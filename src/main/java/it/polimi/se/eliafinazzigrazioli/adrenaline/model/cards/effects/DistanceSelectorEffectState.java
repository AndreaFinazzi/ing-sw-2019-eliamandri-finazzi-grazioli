package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class DistanceSelectorEffectState extends SelectorEffectState {

    final private int maxDistance;
    final private int minDistance;

    public DistanceSelectorEffectState(int maxDistance, int minDistance, WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
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
                List<BoardSquare> boardSquaresToSelect = null;
                try {
                    boardSquaresToSelect = new ArrayList<>(gameBoard.getSquaresByDistance(reference, maxDistance, minDistance));
                }catch (Exception e){

                }
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            case PLAYER:
                List<Player> playersToSelect = null;
                try {
                    playersToSelect = new ArrayList<>(gameBoard.getPlayersByDistance(reference, maxDistance, minDistance));
                } catch (Exception e){

                }
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            case ROOM:
                //TODO define and throw exception
                break;
        }

    }
}
