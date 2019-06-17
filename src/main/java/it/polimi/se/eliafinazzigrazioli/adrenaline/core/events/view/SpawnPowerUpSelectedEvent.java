package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class SpawnPowerUpSelectedEvent extends AbstractViewEvent {

    private PowerUpCardClient toKeep;
    private PowerUpCardClient spawnCard;

    public SpawnPowerUpSelectedEvent(int clientID, String player, PowerUpCardClient toKeep, PowerUpCardClient spawnCard) {
        super(clientID, player);
        this.toKeep = toKeep;
        this.spawnCard = spawnCard;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public PowerUpCard getToKeep() {
        return PowerUpCard.clientCopyToServer(toKeep);
    }

    public PowerUpCard getSpawnCard() {
        return PowerUpCard.clientCopyToServer(spawnCard);
    }
}
