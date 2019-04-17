package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PlayerBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.List;

public class DamageAction extends EffectState {

    int damageAmount;
    int markAmount;

    public DamageAction(int damageAmount, int markAmount) {
        this.damageAmount = damageAmount;
        this.markAmount = markAmount;
    }

    public void execute(WeaponCard invoker){
        WeaponEffect executingEffect = invoker.getActiveEffect();
        List<Player> toDamage = executingEffect.getToAffect();
        for (Player damaged:toDamage){
            PlayerBoard board = damaged.getPlayerBoard();
            for(int i=0; i<damageAmount; i++){
                try {
                    board.addDamage(invoker.getDeliveredMark());
                }
                catch (Exception e){
                    //TODO a handling procedure is to be defined
                    break;
                }
            }
            for(int i=0; i<markAmount; i++) {
                try {
                    board.addMark(invoker.getDeliveredMark());
                } catch (Exception e) {
                    //TODO a handling procedure is to be defined
                    break;
                }
            }
            executingEffect.addShotPlayers(toDamage);
        }
    }


}
