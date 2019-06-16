package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class SpawnPowerUpSelected extends AbstractViewEvent {

    private PowerUpCard toKeep;
    private PowerUpCard spawnCard;

    public SpawnPowerUpSelected(int clientID, String player, PowerUpCard toKeep, PowerUpCard spawnCard) {
        super(clientID, player);
        this.toKeep = toKeep;
        this.spawnCard = spawnCard;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public PowerUpCard getToKeep() {
        return toKeep;
    }

    public PowerUpCard getSpawnCard() {
        return spawnCard;
    }
}
