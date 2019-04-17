package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.List;

public class MoveAction extends EffectState {


    public void execute(WeaponCard invoker){
        WeaponEffect executingEffect = invoker.getActiveEffect();
        List<Player> toMove = executingEffect.getToAffect();
        BoardSquare destination = executingEffect.getMovementDestination();
        for(Player moved:toMove){
            moved.setPosition(destination);
        }
        executingEffect.addMovedPlayers(toMove);
    }

}
