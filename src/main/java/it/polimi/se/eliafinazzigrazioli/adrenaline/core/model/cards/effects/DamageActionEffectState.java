package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerShotEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DamageActionEffectState extends ActionEffectState {

    private static final Logger LOGGER = Logger.getLogger(DamageActionEffectState.class.getName());

    private int damageAmount;
    private int markAmount;

    public DamageActionEffectState(int damageAmount, int markAmount, String playerToAffectSource, int toAffectPlayerSelectionOrder) {
        super(playerToAffectSource, toAffectPlayerSelectionOrder);
        this.damageAmount = damageAmount;
        this.markAmount = markAmount;
    }


    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        List<AbstractModelEvent> events = new ArrayList<>();
        Player toDamage;
        try {
            toDamage = invoker.getEffectByName(playerToAffectSource).getSelectedPlayer(toAffectPlayerSelectionOrder);
        }catch (IndexOutOfBoundsException e){
            toDamage = null;
        }
        if (toDamage != null){
            PlayerBoard playerBoard = toDamage.getPlayerBoard();
            for(int i=0; i<damageAmount; i++){
                try {
                    playerBoard.addDamage(currentPlayer.getDamageMarkDelivered());
                    //TODO change addDamage method's return for events like suddenDeath and 'infierire' and addition to events
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
            events.add(new PlayerShotEvent(currentPlayer.getPlayerNickname(), toDamage.getPlayerNickname(), invoker.getWeaponName()));
            return events;
        }
        return null;
    }


}
