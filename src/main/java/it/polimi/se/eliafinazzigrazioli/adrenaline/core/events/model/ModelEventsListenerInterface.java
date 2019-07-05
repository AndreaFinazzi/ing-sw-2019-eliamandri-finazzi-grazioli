package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The interface Model events listener interface.
 */
public interface ModelEventsListenerInterface extends EventListenerInterface {


    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(AbstractModelEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(ReloadWeaponsRequestEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(ActionRequestEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(AmmoCardCollectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(BeginTurnEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(BeginMatchEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(WeaponCardDrawedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(CardReloadedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(ConnectionResponseEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(ConnectionTimeoutEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(FinalFrenzyBeginEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(MatchStartedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(NotAllowedPlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerDamagedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerDisconnectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerReconnectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerShotEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerUpdateEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectableBoardSquaresEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectablePlayersEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectableRoomsEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerDeathEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectedMapEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SpawnSelectionRequestEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerSpawnedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PowerUpCollectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(AmmoCollectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(WeaponCollectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectableEffectsEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PaymentExecutedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PlayerMovedByWeaponEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(WeaponReloadedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(PointsAssignmentEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SkullRemovalEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(CleanPlayerBoardEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(FinalFrenzyActionRequestEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(UsablePowerUpsEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SelectDirectionEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(MatchEndedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

}
