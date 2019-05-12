package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class CardinalDirectionSelectorEffectState extends SelectorEffectState {

    private String directionSource;
    private int directionSelectionOrder;

    public CardinalDirectionSelectorEffectState(String referenceSource, int sourceSelectionOrder, SelectableType referenceType, SelectableType selectionType, String directionSource, int directionSelectionOrder) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.directionSource = directionSource;
        this.directionSelectionOrder = directionSelectionOrder;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        BoardSquare reference = null;
        CardinalDirection direction = invoker.getEffectByName(directionSource).getSelectedDirection(directionSelectionOrder);
        try {
            reference = getReference(invoker, gameBoard, currentPlayer);
        }
        catch (Exception e) {
            //TODO held exception
        }
        switch (selectionType) {
            case PLAYER:
                List<Player> playersToSelect = null;
                try {
                    playersToSelect = new ArrayList<>(gameBoard.getPlayersByCardinalDirection(reference, direction));
                } catch (Exception e){

                }
                invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                break;
            case BOARDSQUARE:
                List<BoardSquare> boardSquaresToSelect = null;
                try {
                    boardSquaresToSelect = new ArrayList<>(gameBoard.getBoardSquaresByCardinalDirection(reference, direction));
                }catch (Exception e){

                }
                invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                break;
            default:
                //TODO a solution to this useless default is to insert constrains in the constructor
                break;
        }
        return null;
    }
}
