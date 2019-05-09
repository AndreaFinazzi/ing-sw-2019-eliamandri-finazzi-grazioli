package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public abstract class EffectState {

    private boolean selectionRequired;
    private boolean isFinal;

    public abstract void execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer);

    public boolean selectionIsRequired() {
        return selectionRequired;
    }

    public boolean isFinal() {
        return isFinal;
    }


}
