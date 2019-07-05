package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Payment executed event.
 */
public class PaymentExecutedEvent extends AbstractModelEvent {

    private List<PowerUpCardClient> powerUpsSpent;
    private List<Ammo> ammosSpent;

    /**
     * Instantiates a new Payment executed event.
     *
     * @param player the player
     * @param powerUpsSpent the power ups spent
     * @param ammosSpent the ammos spent
     */
    public PaymentExecutedEvent(Player player, List<PowerUpCard> powerUpsSpent, List<Ammo> ammosSpent) {
        super(player);
        this.powerUpsSpent = new ArrayList<>();
        for (PowerUpCard powerUpCard: powerUpsSpent)
            this.powerUpsSpent.add(new PowerUpCardClient(powerUpCard));
        this.ammosSpent = ammosSpent;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets power ups spent.
     *
     * @return the power ups spent
     */
    public List<PowerUpCardClient> getPowerUpsSpent() {
        return powerUpsSpent;
    }

    /**
     * Gets ammos spent.
     *
     * @return the ammos spent
     */
    public List<Ammo> getAmmosSpent() {
        return ammosSpent;
    }
}
