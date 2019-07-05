package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type In room selector effect state.
 */
public class InRoomSelectorEffectState extends SelectorEffectState {

    /**
     * Instantiates a new In room selector effect state.
     *
     * @param referenceSource the reference source
     * @param sourceSelectionOrder the source selection order
     * @param referenceType the reference type
     * @param selectionType the selection type
     */
    public InRoomSelectorEffectState(String referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType) {
        super(referenceSource, sourceSelectionOrder, selectionType, referenceType);
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        Room reference = invoker.getEffectByName(referenceSource).getSelectedRoom(sourceSelectionOrder);
        switch (selectionType) {
            case BOARDSQUARE:
                List<BoardSquare> boardSquaresToSelect = new ArrayList<>(gameBoard.getRoomSquares(reference));
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            case PLAYER:
                List<Player> playersToSelect = new ArrayList<>(gameBoard.getRoomPlayers(reference));
                playersToSelect.remove(currentPlayer);
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            default:
                //TODO define and throw exception
                break;
        }
        return new ArrayList<>();
    }

}
