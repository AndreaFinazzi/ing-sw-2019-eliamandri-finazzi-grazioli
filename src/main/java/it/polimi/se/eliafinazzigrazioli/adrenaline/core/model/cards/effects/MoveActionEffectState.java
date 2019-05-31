package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerMovedByWeaponEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

public class MoveActionEffectState extends ActionEffectState {


    private String movementDestinationSource;
    private int destinationSelectionOrder;

    public MoveActionEffectState(String movementDestinationSource, int destinationSelectionOrder, String playerToAffectSource, int toAffectPlayerSelectionOrder) {
        super(playerToAffectSource, toAffectPlayerSelectionOrder);
        this.movementDestinationSource = movementDestinationSource;
        this.destinationSelectionOrder = destinationSelectionOrder;
    }


    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        List<AbstractModelEvent> events = new ArrayList<>();
        BoardSquare destination = invoker.getEffectByName(movementDestinationSource).getSelectedBoardSquare(destinationSelectionOrder);
        Player toMove;
        if (movementDestinationSource != null) {
            try {
                toMove = invoker.getEffectByName(playerToAffectSource).getSelectedPlayer(destinationSelectionOrder);
            } catch (IndexOutOfBoundsException e) {
                toMove = null;
            }
        } else
            toMove = currentPlayer;
        if (toMove != null) {
            gameBoard.movePlayer(toMove, destination);
            events.add(new PlayerMovedByWeaponEvent(currentPlayer.getPlayerNickname(), invoker.getWeaponName(), toMove.getPlayerNickname()));
            return events;
        }
        return null;
    }

}
