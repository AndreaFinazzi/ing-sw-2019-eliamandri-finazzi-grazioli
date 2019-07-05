package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.RuntimeTypeAdapterFactory;

import java.util.List;

/**
 * The type Effect state.
 */
public abstract class EffectState {

    private boolean isFinal = false;


    /**
     * Class hierarchy definition used to parse the json file.
     */
    public static final RuntimeTypeAdapterFactory<EffectState> effectStateRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(EffectState.class, "type")
            .registerSubtype(SelectorEffectState.class, "SelectorEffectState")
            .registerSubtype(ActionEffectState.class, "ActionEffectState")
            .registerSubtype(VisibilitySelectorEffectState.class, "VisibilitySelectorEffectState")
            .registerSubtype(SelectionRequestEffectState.class, "SelectionRequestEffectState")
            .registerSubtype(CardinalDirectionSelectorEffectState.class, "CardinalDirectionSelectorEffectState")
            .registerSubtype(DistanceSelectorEffectState.class, "DistanceSelectorEffectState")
            .registerSubtype(InRoomSelectorEffectState.class, "InRoomSelectorEffectState")
            .registerSubtype(PreselectionBasedSelectorEffectState.class, "PreselectionBasedSelectorEffectState")
            .registerSubtype(MoveActionEffectState.class, "MoveActionEffectState")
            .registerSubtype(DamageActionEffectState.class, "DamageActionEffectState")
            .registerSubtype(SelectionResetEffectState.class, "SelectionResetEffectState");


    /**
     * Execute list.
     *
     * @param invoker the invoker
     * @param gameBoard the game board
     * @param currentPlayer the current player
     * @return the list
     */
    public abstract List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer);

    /**
     * Is final boolean.
     *
     * @return the boolean
     */
    public boolean isFinal() {
        return isFinal;
    }


}
