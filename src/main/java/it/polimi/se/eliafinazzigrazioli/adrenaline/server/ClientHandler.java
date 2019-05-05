package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public interface ClientHandler extends Runnable{

    public void send(AbstractEvent event);

    public AbstractEvent receved();
}
