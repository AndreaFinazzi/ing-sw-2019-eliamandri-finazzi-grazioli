package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.ListenerNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;


public class EventController implements Observer {
    private HashMap<Class<? extends AbstractViewEvent>, ArrayList<EventListenerInterface>> listenerMap;

    public EventController() {
        listenerMap = new HashMap<>();
    }

    @Override
    public void update(AbstractEvent event) {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(event.getClass());
        listeners.forEach(listener -> {
                    try {
                        event.handle(listener);
                    } catch (HandlerNotImplementedException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void addEventListener(Class<? extends AbstractViewEvent> key, EventListenerInterface value) {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(key);

        if (listeners != null) {
            listeners.add(value);
        } else {
            listeners = new ArrayList<EventListenerInterface>();
            listeners.add(value);
            listenerMap.put(key, listeners);
        }
    }

    public void removeEventListener(Class<? extends AbstractViewEvent> key, EventListenerInterface value) throws ListenerNotFoundException {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(key);
        listeners.remove(value);
    }
}
