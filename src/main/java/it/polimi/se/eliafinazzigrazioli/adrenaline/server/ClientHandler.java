package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public interface ClientHandler extends Runnable {

    void send(AbstractEvent event);

    AbstractEvent received();
}
