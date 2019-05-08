package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class InRoomSelectorEffectState extends SelectorEffectState{

    public InRoomSelectorEffectState(WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, selectionType, referenceType);
    }

    @Override
    public void execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        Room reference = referenceSource.getSelectedRoom(sourceSelectionOrder);
        switch (selectionType) {
            case BOARDSQUARE:
                List<BoardSquare> boardSquaresToSelect = new ArrayList<>(gameBoard.getRoomSquares(reference));
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            case PLAYER:
                List<Player> playersToSelect = new ArrayList<>(gameBoard.getRoomPlayers(reference));
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            default:
                //TODO define and throw exception
                break;
        }
    }

}
