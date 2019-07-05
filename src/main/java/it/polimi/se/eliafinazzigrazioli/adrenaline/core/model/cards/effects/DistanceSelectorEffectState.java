package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Distance selector effect state.
 */
public class DistanceSelectorEffectState extends SelectorEffectState {

    final private int maxDistance;
    final private int minDistance;

    /**
     * Instantiates a new Distance selector effect state.
     *
     * @param maxDistance the max distance
     * @param minDistance the min distance
     * @param referenceSource the reference source
     * @param sourceSelectionOrder the source selection order
     * @param selectionType the selection type
     * @param referenceType the reference type
     */
    public DistanceSelectorEffectState(int maxDistance, int minDistance, String referenceSource, int sourceSelectionOrder, SelectableType selectionType, SelectableType referenceType) {
        super(referenceSource, sourceSelectionOrder, referenceType, selectionType);
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) { //TODO define exception type (invalid reference)
        BoardSquare reference;
        try {
            reference = getReference(invoker, gameBoard, currentPlayer);
        } catch (Exception e) {
            reference = null;
        }
        if (reference != null) {
            switch (selectionType) {
                case BOARDSQUARE:
                    List<BoardSquare> boardSquaresToSelect = null;
                    try {
                        boardSquaresToSelect = new ArrayList<>(gameBoard.getSquaresByDistance(reference, maxDistance, minDistance));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    invoker.getActiveEffect().updateToSelectBoardSquares(boardSquaresToSelect);
                    break;
                case PLAYER:
                    List<Player> playersToSelect = null;
                    try {
                        playersToSelect = new ArrayList<>(gameBoard.getPlayersByDistance(reference, maxDistance, minDistance));
                        playersToSelect.remove(currentPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    invoker.getActiveEffect().updateToSelectPlayers(playersToSelect);
                    break;
                case ROOM:
                    //TODO define and throw exception
                    break;
            }
        } else {
            invoker.getActiveEffect().updateToSelectPlayers(new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
