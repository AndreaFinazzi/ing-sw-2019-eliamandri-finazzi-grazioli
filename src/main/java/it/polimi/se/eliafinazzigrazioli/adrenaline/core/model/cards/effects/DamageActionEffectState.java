package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerShotEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
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
        DamageMark deliveredMark = currentPlayer.getDamageMarkDelivered();
        List<DamageMark> damages = new ArrayList<>();
        List<DamageMark> marks = new ArrayList<>();
        List<DamageMark> marksRemoved = new ArrayList<>();
        Player toDamage;
        try {
            toDamage = invoker.getEffectByName(playerToAffectSource).getSelectedPlayer(toAffectPlayerSelectionOrder);
        } catch (IndexOutOfBoundsException e) {
            toDamage = null;
        }
        if (toDamage != null) {
            PlayerBoard playerBoard = toDamage.getPlayerBoard();

            for (int i = 0; i < damageAmount; i++) {
                damages.add(playerBoard.addDamage(deliveredMark));
                damages.remove(null);
            }

            while (toDamage.getPlayerBoard().getMarks().contains(deliveredMark)) {
                marksRemoved.add(playerBoard.removeMark(deliveredMark));
                marksRemoved.remove(null);
                damages.add(playerBoard.addDamage(deliveredMark));
                damages.remove(null);
            }


            for (int i = 0; i < markAmount && currentPlayer.getPlayerBoard().canUseMark(); i++) {
                marks.add(playerBoard.addMark(currentPlayer.getDamageMarkDelivered()));
                marks.remove(null);
            }

            events.add(new PlayerShotEvent(currentPlayer, toDamage.getPlayerNickname(), damages, marks, marksRemoved));
            return events;
        }
        return new ArrayList<>();
    }


}
