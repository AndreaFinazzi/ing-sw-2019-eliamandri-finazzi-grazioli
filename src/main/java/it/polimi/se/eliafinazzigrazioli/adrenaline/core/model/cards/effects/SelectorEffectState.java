package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

public abstract class SelectorEffectState extends EffectState {


    final protected String referenceSource;
    final protected int sourceSelectionOrder;
    final protected SelectableType referenceType;
    final protected SelectableType selectionType;

    protected SelectorEffectState(String referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType) {
        this.referenceSource = referenceSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.referenceType = referenceType;
        this.selectionType = selectionType;
    }

    public SelectorEffectState(String previousSelectionSource, int sourceSelectionOrder) {
        this.referenceSource = previousSelectionSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.selectionType = null;
        this.referenceType = null;
    }

    protected BoardSquare getReference(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) throws Exception{ //TODO define exception type
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
