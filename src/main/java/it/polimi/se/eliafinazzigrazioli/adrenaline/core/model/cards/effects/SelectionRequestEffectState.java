package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class SelectionRequestEffectState extends EffectState {

    private boolean userSelectionRequired;
    private SelectableType selectionType;
    private int maxSelectableItems;

    public SelectionRequestEffectState(boolean userSelectionRequired, SelectableType selectionType, int maxSelectableItems) {
        this.userSelectionRequired = userSelectionRequired;
        this.selectionType = selectionType;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        List<AbstractModelEvent> events = new ArrayList<>();
        if (userSelectionRequired) {
            switch (selectionType) {
                case PLAYER:
                    List<String> selectablePlayers = new ArrayList<>();
                    for (Player player : invoker.getActiveEffect().getToSelectPlayers())
                        selectablePlayers.add(player.getPlayerNickname());
                    events.add(new SelectablePlayersEvent(currentPlayer, selectablePlayers, maxSelectableItems));
                    break;
                case BOARDSQUARE:
                    List<Coordinates> selectableSquares = new ArrayList<>();
                    for (BoardSquare boardSquare : invoker.getActiveEffect().getToSelectBoardSquares())
                        selectableSquares.add(gameBoard.getCoordinates(boardSquare));
                    events.add(new SelectableBoardSquaresEvent(currentPlayer, selectableSquares, maxSelectableItems));
                    break;
                case ROOM:
                    events.add(new SelectableRoomsEvent(currentPlayer.getPlayerNickname(), invoker.getActiveEffect().getToSelectRooms()));
                    break;
                case DIRECTION:
                    events.add(new SelectDirectionEvent(currentPlayer.getPlayerNickname()));
                    break;
            }
            invoker.getActiveEffect().setNeedsSelection(true);
        } else {
            invoker.getActiveEffect().automaticSelection();
        }
        invoker.getActiveEffect().selectionReset();
        return events;
    }
}
