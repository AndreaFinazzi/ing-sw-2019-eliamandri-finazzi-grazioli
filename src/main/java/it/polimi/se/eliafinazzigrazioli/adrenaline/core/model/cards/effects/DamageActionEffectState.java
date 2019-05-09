package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DamageActionEffectState extends ActionEffectState {

    private static final Logger LOGGER = Logger.getLogger(DamageActionEffectState.class.getName());

    private int damageAmount;
    private int markAmount;

    public DamageActionEffectState(int damageAmount, int markAmount, WeaponEffect playerToAffectSource, int toAffectPlayerSelectionOrder) {
        super(playerToAffectSource, toAffectPlayerSelectionOrder);
        this.damageAmount = damageAmount;
        this.markAmount = markAmount;
    }


    @Override
    public void execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        Player toDamage;
        try {
            toDamage = playerToAffectSource.getSelectedPlayer(toAffectPlayerSelectionOrder);
        }catch (IndexOutOfBoundsException e){
            toDamage = null;
        }
        if (toDamage != null){
            PlayerBoard playerBoard = toDamage.getPlayerBoard();
            for(int i=0; i<damageAmount; i++){
                try {
                    playerBoard.addDamage(currentPlayer.getDamageMarkDelivered());
                } catch (OutOfBoundException e) {
                    //TODO held exception
                }
            }
            for(int i=0; i<markAmount; i++){
                try {
                    playerBoard.addMark(currentPlayer.getDamageMarkDelivered());
                }
                catch (OutOfBoundException e) {
                    //TODO held exception
                }
            }
        }
    }


}
