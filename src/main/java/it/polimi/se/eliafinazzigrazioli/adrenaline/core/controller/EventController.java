package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.ListenerNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Event controller.
 */
public class EventController implements Observer {
    private HashMap<Class<? extends AbstractViewEvent>, ArrayList<ViewEventsListenerInterface>> viewEventsListenerMap;

    private ArrayList<AbstractClientHandler> virtualViews;

    private static final Logger LOGGER = Logger.getLogger(EventController.class.getName());

    private Timer turnTimer = new Timer();
    private int timerClientID;
    //Bidirectional Event dispatcher

    /**
     * Instantiates a new Event controller.
     *
     * @param matchController the match controller
     */
//TODO is MatchController reference useful?
    public EventController(MatchController matchController) {
        viewEventsListenerMap = new HashMap<>();
    }


    @Override
    public synchronized void update(AbstractViewEvent event) {
        LOGGER.info("Updating eventController");
        // Stop turn timer
        if (event.getClientID() == timerClientID) stopTimer();

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

    private void stopTimer() {
        turnTimer.cancel();
        turnTimer = new Timer();
    }

    @Override
    public synchronized void update(AbstractModelEvent event) {
        if (virtualViews == null) return;

        if (event.isPrivateEvent()) {
            for (AbstractClientHandler virtualView : virtualViews) {
                if (virtualView.getClientID() == event.getClientID()) {
                    virtualView.send(event);
                    if (event.isRequest()) {
                        startTimer(virtualView);
                    }
                }
            }
        } else {
            for (AbstractClientHandler virtualView : virtualViews) {
                virtualView.send(event);
            }
        }
    }

    private void startTimer(AbstractClientHandler virtualView) {
        timerClientID = virtualView.getClientID();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                virtualView.unregister();
            }
        }, (long) Config.CONFIG_MATCH_TURN_TIMEOUT);
    }

    /**
     * Add view events listener.
     *
     * @param key the key
     * @param value the value
     */
    public synchronized void addViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);

        if (listeners != null) {
            listeners.add(value);
        } else {
            listeners = new ArrayList<>();
            listeners.add(value);
            viewEventsListenerMap.put(key, listeners);
        }
    }

    /**
     * Add virtual view.
     *
     * @param virtualView the virtual view
     */
    public synchronized void addVirtualView(AbstractClientHandler virtualView) {
        if (virtualViews == null) {
            virtualViews = new ArrayList<>();
        }
        if (!virtualViews.contains(virtualView))
            virtualViews.add(virtualView);
    }

    /**
     * Remove view events listener.
     *
     * @param key the key
     * @param value the value
     * @throws ListenerNotFoundException the listener not found exception
     */
    public synchronized void removeViewEventsListener(Class<? extends AbstractViewEvent> key, ViewEventsListenerInterface value) throws ListenerNotFoundException {
        ArrayList<ViewEventsListenerInterface> listeners = viewEventsListenerMap.get(key);
        if (!listeners.remove(value)) throw new ListenerNotFoundException();
    }

    /**
     * Pop virtual view abstract client handler.
     *
     * @param clientID the client id
     * @return the abstract client handler
     */
    public synchronized AbstractClientHandler popVirtualView(int clientID) {
        for (AbstractClientHandler virtualView : virtualViews) {
            if (virtualView.getClientID() == clientID) {
                virtualViews.remove(virtualView);
                return virtualView;
            }
        }

        return null;
    }
}
