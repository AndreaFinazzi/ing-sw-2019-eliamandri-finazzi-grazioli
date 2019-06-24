package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

public class VisibilitySelectorEffectState extends SelectorEffectState {

    final private boolean notVisible;

    public VisibilitySelectorEffectState(boolean notVisible, String referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.notVisible = notVisible;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) { //TODO define exception type (invalid reference)
        BoardSquare reference = null;
        try {
            reference = getReference(invoker, gameBoard, currentPlayer);
        } catch (Exception e) {
            //TODO held exception
        }
        switch (selectionType) {
            case BOARDSQUARE:
                List<BoardSquare> boardSquaresToSelect = new ArrayList<>(gameBoard.getVisibleSquares(reference, notVisible));
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            case PLAYER:
                List<Player> playersToSelect = new ArrayList<>(gameBoard.getVisiblePlayers(reference, notVisible));
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            case ROOM:
                List<Room> roomsToSelect = new ArrayList<>(gameBoard.getVisibleRooms(reference, notVisible));
                invoker.getActiveEffect().updateToSelectRooms(roomsToSelect);
                break;
        }
        return new ArrayList<>();
    }


    public SelectableType getReferenceType() {
        return referenceType;
    }
}
