package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class CardinalSelectorEffectState extends SelectorEffectState {

    public CardinalSelectorEffectState(WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
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
                List<BoardSquare> boardSquaresToSelect = new ArrayList<>(gameBoard.getSquaresOnCardinalDirections(reference));
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            case PLAYER:
                List<Player> playersToSelect = new ArrayList<>(gameBoard.getPlayersOnCardinalDirections(reference));
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            default:
                //TODO define and throw exception
                break;
        }
    }

}
