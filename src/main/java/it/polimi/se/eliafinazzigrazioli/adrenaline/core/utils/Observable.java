package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.List;

/**
 * The interface Observable.
 */
public interface Observable {

    /**
     * Gets observers.
     *
     * @return the observers
     */
    List<Observer> getObservers();

    /**
     * Add observer.
     *
     * @param observer the observer
     */
    default void addObserver(Observer observer) {
        getObservers().add(observer);
    }

    /**
     * Remove observer.
     *
     * @param observer the observer
     */
    default void removeObserver(Observer observer) {
        getObservers().remove(observer);
    }

    /**
     * Notify observers.
     *
     * @param event the event
     */
    default void notifyObservers(AbstractViewEvent event) {
        synchronized (getObservers()) {
            for (Observer observer : getObservers()) {
                observer.update(event);
            }
        }
    }

    /**
     * Notify observers.
     *
     * @param event the event
     */
    default void notifyObservers(AbstractModelEvent event) {
        synchronized (getObservers()) {
            for (Observer observer : getObservers()) {
                observer.update(event);
            }
        }
    }

    /**
     * Notify observers.
     *
     * @param eventsList the events list
     */
    default void notifyObservers(List<AbstractModelEvent> eventsList) {
        synchronized (getObservers()) {
            for (AbstractModelEvent event : eventsList)
                for (Observer observer : getObservers()) {
                    observer.update(event);
                }
        }
    }

}
