package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Cardinal direction selector effect state.
 */
public class CardinalDirectionSelectorEffectState extends SelectorEffectState {

    private String directionSource;
    private int directionSelectionOrder;

    /**
     * Instantiates a new Cardinal direction selector effect state.
     *
     * @param referenceSource the reference source
     * @param sourceSelectionOrder the source selection order
     * @param referenceType the reference type
     * @param selectionType the selection type
     * @param directionSource the direction source
     * @param directionSelectionOrder the direction selection order
     */
    public CardinalDirectionSelectorEffectState(String referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType, String directionSource, int directionSelectionOrder) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.directionSource = directionSource;
        this.directionSelectionOrder = directionSelectionOrder;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        BoardSquare reference;
        CardinalDirection direction = invoker.getEffectByName(directionSource).getSelectedDirection(directionSelectionOrder);
        try {
            reference = getReference(invoker, gameBoard, currentPlayer);
        } catch (Exception e) {
            reference = null;
        }
        if (reference != null) {
            switch (selectionType) {
                case PLAYER:
                    List<Player> playersToSelect = null;
                    try {
                        playersToSelect = new ArrayList<>(gameBoard.getPlayersByCardinalDirection(reference, direction));
                        playersToSelect.remove(currentPlayer);
                    } catch (Exception e) {

                    }
                    invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                    break;
                case BOARDSQUARE:
                    List<BoardSquare> boardSquaresToSelect = null;
                    try {
                        boardSquaresToSelect = new ArrayList<>(gameBoard.getBoardSquaresByCardinalDirection(reference, direction));
                    } catch (Exception e) {

                    }
                    invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                    break;
                default:
                    //TODO a solution to this useless default is to insert constrains in the constructor
                    break;
            }
        }
        return new ArrayList<>();
    }
}
