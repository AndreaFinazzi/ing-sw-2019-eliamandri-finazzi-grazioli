package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    ArrayList<Observer> observers = new ArrayList<>();

    default void addObserver(Observer observer) {
        observers.add(observer);
    }

    default void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    default void notifyObservers(AbstractViewEvent event) {
        synchronized (observers) {
            for (Observer observer : observers) {
                observer.update(event);
            }
        }
    }

    default void notifyObservers(AbstractModelEvent event) {
        synchronized (observers) {
            for (Observer observer : observers) {
                observer.update(event);
            }
        }
    }

    default void notifyObservers(List<AbstractModelEvent> eventsList) {
        for (AbstractModelEvent event: eventsList)
            for (Observer observer : observers) {
                observer.update(event);
            }
    }

}
