package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PlayerBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class MoveActionEffectState extends ActionEffectState {


    private WeaponEffect movementDestionationSource;
    private int destinationSelectionOrder;

    public MoveActionEffectState(WeaponEffect movementDestionationSource, int destinationSelectionOrder, WeaponEffect playerToAffectSource, int toAffectPlayerSelectionOrder) {
        super(playerToAffectSource, toAffectPlayerSelectionOrder);
        this.movementDestionationSource = movementDestionationSource;
        this.destinationSelectionOrder = destinationSelectionOrder;
    }


    public void execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        BoardSquare destination = movementDestionationSource.getSelectedBoardSquare(destinationSelectionOrder);
        Player toMove;
        try {
            toMove = playerToAffectSource.getSelectedPlayer(destinationSelectionOrder);
        } catch (IndexOutOfBoundsException e) {
            toMove = null;
        }
        if (toMove != null){
            gameBoard.movePlayer(toMove, destination);
        }

        //May be necessary to add moved players to a list in the card

        if (false) {
            BoardSquare d = movementDestionationSource.getSelectedBoardSquare(destinationSelectionOrder);
            List<Player> m = new ArrayList<>();
            for (Integer selectionOrder : new ArrayList<Integer>())
                for (Player moved : m) {
                    moved.setPosition(d);
                }
            new WeaponEffect().addMovedPlayers(m);
        }

        //All the stuff in the last if is to be deleted

    }

}
