package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class VisibilitySelectionEffectState extends SelectorEffectState{

    final private boolean notVisible;

    public VisibilitySelectionEffectState(boolean notVisible, WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.notVisible = notVisible;
    }

    @Override
    public void execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) { //TODO define exception type (invalid reference)
        BoardSquare reference = null;
        try {
            reference = getReference(gameBoard, currentPlayer);
        }
        catch (Exception e) {
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
    }





    public SelectableType getReferenceType() {
        return referenceType;
    }
}
