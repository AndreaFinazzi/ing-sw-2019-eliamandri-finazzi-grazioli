package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

public abstract class SelectorEffectState extends EffectState {


    final protected WeaponEffect referenceSource;
    final protected int sourceSelectionOrder;
    final protected SelectableType referenceType;
    final protected SelectableType selectionType;

    protected SelectorEffectState(WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType) {
        this.referenceSource = referenceSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.referenceType = referenceType;
        this.selectionType = selectionType;
    }

    protected BoardSquare getReference(GameBoard gameBoard, Player currentPlayer) throws Exception{ //TODO define exception type
        BoardSquare reference;
        if (referenceSource == null)
            reference = gameBoard.getPlayerPosition(currentPlayer);
        else if (referenceType == SelectableType.PLAYER)
            reference = gameBoard.getPlayerPosition(referenceSource.getSelectedPlayer(sourceSelectionOrder));
        else if (referenceType == SelectableType.BOARDSQUARE)
            reference = referenceSource.getSelectedBoardSquare(sourceSelectionOrder);
        else        //The exception can be thrown only when sourceType is Room, which is not acceptable
            throw new Exception();
        return reference;
    }

}
