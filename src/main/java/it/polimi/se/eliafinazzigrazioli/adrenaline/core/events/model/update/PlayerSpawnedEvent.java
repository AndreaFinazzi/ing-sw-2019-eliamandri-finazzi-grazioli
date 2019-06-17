package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerSpawnedEvent extends AbstractModelEvent {

    private Coordinates spawnPoint;
    private PowerUpCardClient discardedPowerUp;

    public PlayerSpawnedEvent(String player, Coordinates spawnPoint, PowerUpCardClient discardedPowerUp) {
        super(player);
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = discardedPowerUp;
    }

    public PlayerSpawnedEvent(Player player, Coordinates spawnPoint, PowerUpCard discardedPowerUp) {
        super(player.getPlayerNickname(), player.getClientID());
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = new PowerUpCardClient(discardedPowerUp);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Coordinates getSpawnPoint() {
        return spawnPoint;
    }

    public PowerUpCardClient getDiscardedPowerUp() {
        return discardedPowerUp;
    }
}
