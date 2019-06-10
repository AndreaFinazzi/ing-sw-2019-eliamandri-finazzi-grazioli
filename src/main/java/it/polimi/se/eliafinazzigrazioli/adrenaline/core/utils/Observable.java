package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.List;

public interface Observable {

    List<Observer> getObservers();

    default void addObserver(Observer observer) {
        getObservers().add(observer);
    }

    default void removeObserver(Observer observer) {
        getObservers().remove(observer);
    }

    default void notifyObservers(AbstractViewEvent event) {
        synchronized (getObservers()) {
            for (Observer observer : getObservers()) {
                observer.update(event);
            }
        }
    }

    default void notifyObservers(AbstractModelEvent event) {
        synchronized (getObservers()) {
            for (Observer observer : getObservers()) {
                observer.update(event);
            }
        }
    }

    default void notifyObservers(List<AbstractModelEvent> eventsList) {
        synchronized (getObservers()) {
            for (AbstractModelEvent event : eventsList)
                for (Observer observer : getObservers()) {
                    observer.update(event);
                }
        }
    }

}
