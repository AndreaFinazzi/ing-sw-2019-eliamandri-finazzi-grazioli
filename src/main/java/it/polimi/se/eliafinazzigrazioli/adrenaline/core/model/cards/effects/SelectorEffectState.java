package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

/**
 * The type Selector effect state.
 */
public abstract class SelectorEffectState extends EffectState {


    /**
     * The Reference source.
     */
    final protected String referenceSource;
    /**
     * The Source selection order.
     */
    final protected int sourceSelectionOrder;
    /**
     * The Reference type.
     */
    final protected SelectableType referenceType;
    /**
     * The Selection type.
     */
    final protected SelectableType selectionType;

    /**
     * Instantiates a new Selector effect state.
     *
     * @param referenceSource the reference source
     * @param sourceSelectionOrder the source selection order
     * @param referenceType the reference type
     * @param selectionType the selection type
     */
    protected SelectorEffectState(String referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType) {
        this.referenceSource = referenceSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.referenceType = referenceType;
        this.selectionType = selectionType;
    }

    /**
     * Instantiates a new Selector effect state.
     *
     * @param previousSelectionSource the previous selection source
     * @param sourceSelectionOrder the source selection order
     */
    public SelectorEffectState(String previousSelectionSource, int sourceSelectionOrder) {
        this.referenceSource = previousSelectionSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.selectionType = null;
        this.referenceType = null;
    }

    /**
     * Gets reference.
     *
     * @param invoker the invoker
     * @param gameBoard the game board
     * @param currentPlayer the current player
     * @return the reference
     * @throws Exception                 the exception
     * @throws IndexOutOfBoundsException the index out of bounds exception
     */
    protected BoardSquare getReference(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) throws Exception ,IndexOutOfBoundsException { //TODO define exception type
        BoardSquare reference;
        if (referenceSource == null)
            reference = gameBoard.getPlayerPosition(currentPlayer);
        else if (referenceType == SelectableType.PLAYER)
            reference = gameBoard.getPlayerPosition(invoker.getEffectByName(referenceSource).getSelectedPlayer(sourceSelectionOrder));
        else if (referenceType == SelectableType.BOARDSQUARE)
            reference = invoker.getEffectByName(referenceSource).getSelectedBoardSquare(sourceSelectionOrder);
        else        //The exception can be thrown only when sourceType is Room or CardinalDirection, which is not acceptable
            throw new Exception();
        return reference;
    }

}
