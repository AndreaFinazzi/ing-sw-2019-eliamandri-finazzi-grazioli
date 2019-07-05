package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerShotEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.UsablePowerUpsEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.Arrays;
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
                currentPlayer.getPlayerBoard().increaseDeliverableMarks();
            }


            for (int i = 0; i < markAmount && currentPlayer.getPlayerBoard().canUseMark(); i++) {
                marks.add(playerBoard.addMark(currentPlayer.getDamageMarkDelivered()));
                marks.remove(null);
            }

            events.add(new PlayerShotEvent(currentPlayer, toDamage.getPlayerNickname(), damages, marks, marksRemoved));
            List<PowerUpCard> usablePowerUpsTargeting = new ArrayList<>();
            for (PowerUpCard powerUpCard: currentPlayer.getPowerUps()) {
                if (powerUpCard.getType().equals("Targeting Scope"))
                    usablePowerUpsTargeting.add(powerUpCard);
            }
            if (!usablePowerUpsTargeting.isEmpty() && damageAmount > 0)
                events.add(new UsablePowerUpsEvent(currentPlayer, new ArrayList<>(Arrays.asList("Targeting Scope")), toDamage.getPlayerNickname()));

            List<PowerUpCard> usablePowerUpsTrackBack = new ArrayList<>();
            for (PowerUpCard powerUpCard: toDamage.getPowerUps()) {
                if (powerUpCard.getType().equals("Tagback Grenade"))
                    usablePowerUpsTrackBack.add(powerUpCard);
            }
            if (!usablePowerUpsTrackBack.isEmpty() && damageAmount > 0)
                events.add(new UsablePowerUpsEvent(toDamage, new ArrayList<>(Arrays.asList("Tagback Grenade")), currentPlayer.getPlayerNickname()));
            return events;
        }
        return new ArrayList<>();
    }


}
