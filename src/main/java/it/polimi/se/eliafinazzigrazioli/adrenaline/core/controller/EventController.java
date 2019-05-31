package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.ListenerNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EventController implements Observer {
    private HashMap<Class<? extends AbstractViewEvent>, ArrayList<ViewEventsListenerInterface>> viewEventsListenerMap;
    //private ArrayList<ModelEventsListenerInterface> modelEventsListenerMap;

    private ArrayList<AbstractClientHandler> virtualViews;

    private static final Logger LOGGER = Logger.getLogger(EventController.class.getName());

    //Bidirectional Event dispatcher

    //TODO is MatchController reference useful?
    public EventController(MatchController matchController) {
        viewEventsListenerMap = new HashMap<>();
    }


    @Override
    public void update(AbstractViewEvent event) {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(event.getClass());

        if (listeners == null) return;

        listeners.forEach(listener -> {
                    try {
                        event.handle(listener);
                    } catch (HandlerNotImplementedException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
        );
    }

    @Override
    public void update(AbstractModelEvent event) {
        if (virtualViews == null) return;

        virtualViews.forEach(virtualView -> {
            virtualView.send(event);
                }
        );
    }

    public void addViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);

        if (listeners != null) {
            listeners.add(value);
        } else {
            listeners = new ArrayList<>();
            listeners.add(value);
            viewEventsListenerMap.put(key, listeners);
        }
    }

    public void addVirtualView(AbstractClientHandler virtualView) {
        if (virtualViews == null) {
            virtualViews = new ArrayList<>();
        }
        if (!virtualViews.contains(virtualView))
            virtualViews.add(virtualView);
    }

    public void removeViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) throws ListenerNotFoundException {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);
        if (!listeners.remove(value)) throw new ListenerNotFoundException();
    }

    public void removeVirtualView(AbstractClientHandler virtualView) {
        virtualViews.remove(virtualView);
    }
}
