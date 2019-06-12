package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class ActionRequestEvent extends AbstractModelEvent {

    private int simpleMovesMax;
    private int collectingMovesMax;
    private int shootingMovesMax;

    private int actionsRemained;

    public ActionRequestEvent(String player, int actionsRemained) {
        super(player, true);
        this.actionsRemained = actionsRemained;
    }

    public ActionRequestEvent(Player player, int actionsRemained) {
        super(true, player.getPlayerNickname(), player.getClientID());
        this.actionsRemained = actionsRemained;
    }

    public ActionRequestEvent(Player player, int actionsRemained, int simpleMovesMax, int collectingMovesMax, int shootingMovesMax) {
        super(true, player);
        this.actionsRemained = actionsRemained;
        this.simpleMovesMax = simpleMovesMax;
        this.collectingMovesMax = collectingMovesMax;
        this.shootingMovesMax = shootingMovesMax;
    }



    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public int getActionsRemained() {
        return actionsRemained;
    }

    public int getSimpleMovesMax() {
        return simpleMovesMax;
    }

    public int getCollectingMovesMax() {
        return collectingMovesMax;
    }

    public int getShootingMovesMax() {
        return shootingMovesMax;
    }
}
