package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

import java.util.ArrayList;

public class Observable {
    ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public synchronized void notifyObservers(AbstractEvent event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }
}
