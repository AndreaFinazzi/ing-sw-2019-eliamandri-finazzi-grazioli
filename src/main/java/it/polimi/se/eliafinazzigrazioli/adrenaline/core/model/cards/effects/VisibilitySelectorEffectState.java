package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Visibility selector effect state.
 */
public class VisibilitySelectorEffectState extends SelectorEffectState {

    final private boolean notVisible;

    /**
     * Instantiates a new Visibility selector effect state.
     *
     * @param notVisible the not visible
     * @param referenceSource the reference source
     * @param sourceSelectionOrder the source selection order
     * @param selectionType the selection type
     * @param referenceType the reference type
     */
    public VisibilitySelectorEffectState(boolean notVisible, String referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.notVisible = notVisible;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) { //TODO define exception type (invalid reference)
        BoardSquare reference;
        try {
            reference = getReference(invoker, gameBoard, currentPlayer);
        } catch (Exception e) {
            reference  = null;
        }
        if (reference != null) {
            switch (selectionType) {
                case BOARDSQUARE:
                    List<BoardSquare> boardSquaresToSelect = new ArrayList<>(gameBoard.getVisibleSquares(reference, notVisible));
                    invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                    break;
                case PLAYER:
                    List<Player> playersToSelect = new ArrayList<>(gameBoard.getVisiblePlayers(reference, notVisible));
                    playersToSelect.remove(currentPlayer);
                    invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                    break;
                case ROOM:
                    List<Room> roomsToSelect = new ArrayList<>(gameBoard.getVisibleRooms(reference, notVisible));
                    invoker.getActiveEffect().updateToSelectRooms(roomsToSelect);
                    break;
            }
        } else {
            invoker.getActiveEffect().updateToSelectPlayers(new ArrayList<>());
        }
        return new ArrayList<>();
    }


    /**
     * Gets reference type.
     *
     * @return the reference type
     */
    public SelectableType getReferenceType() {
        return referenceType;
    }
}
