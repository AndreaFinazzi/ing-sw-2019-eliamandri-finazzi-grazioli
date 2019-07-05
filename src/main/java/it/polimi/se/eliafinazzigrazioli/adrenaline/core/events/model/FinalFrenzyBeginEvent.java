package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;

import java.util.ArrayList;
import java.util.List;

public class FinalFrenzyBeginEvent extends AbstractModelEvent {

    private List<String> playerBoardsToSwitch;

    public FinalFrenzyBeginEvent(List<Player> playerBoardsToSwitch) {
        super();
        this.playerBoardsToSwitch = new ArrayList<>();
        for (Player player: playerBoardsToSwitch)
            this.playerBoardsToSwitch.add(player.getPlayerNickname());
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<String> getPlayerBoardsToSwitch() {
        return playerBoardsToSwitch;
    }
}
