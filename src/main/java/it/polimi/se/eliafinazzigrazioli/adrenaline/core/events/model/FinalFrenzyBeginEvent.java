package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Final frenzy begin event.
 */
public class FinalFrenzyBeginEvent extends AbstractModelEvent {

    private List<String> playerBoardsToSwitch;

    /**
     * Instantiates a new Final frenzy begin event.
     *
     * @param playerBoardsToSwitch the player boards to switch
     */
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

    /**
     * Gets player boards to switch.
     *
     * @return the player boards to switch
     */
    public List<String> getPlayerBoardsToSwitch() {
        return playerBoardsToSwitch;
    }
}
