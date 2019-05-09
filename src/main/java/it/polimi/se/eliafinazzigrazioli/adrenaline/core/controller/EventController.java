package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.ListenerNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EventController implements Observer {
    private HashMap<Class<? extends AbstractViewEvent>, ArrayList<ViewEventsListenerInterface>> viewEventsListenerMap;
    private ArrayList<ModelEventsListenerInterface> modelEventsListenerMap;

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
        if (modelEventsListenerMap == null) return;

        modelEventsListenerMap.forEach(listener -> {
                    try {
                        event.handle(listener);
                    } catch (HandlerNotImplementedException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
        );
    }

    public void addViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);

        if (listeners != null) {
            listeners.add(value);
        } else {
            listeners = new ArrayList<ViewEventsListenerInterface>();
            listeners.add(value);
            viewEventsListenerMap.put(key, listeners);
        }
    }

    public void addModelEventsListener(ModelEventsListenerInterface listener) {
        if (modelEventsListenerMap != null) {
            modelEventsListenerMap.add(listener);
        } else {
            modelEventsListenerMap = new ArrayList<>();
            modelEventsListenerMap.add(listener);
        }
    }

    public void removeViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) throws ListenerNotFoundException {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);
        if (!listeners.remove(value)) throw new ListenerNotFoundException();
    }

    public void removeModelEventsListener(EventListenerInterface listener) {
        modelEventsListenerMap.remove(listener);
    }
}
