package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PowerUpCard;

public class PowerUpPlayEvent implements ViewEventInterface {
    private String player;
    private PowerUpCard card;

    @Override
    public String getPlayer() {
        return player;
    }

    public PowerUpCard getCard() {
        return card;
    }
}
