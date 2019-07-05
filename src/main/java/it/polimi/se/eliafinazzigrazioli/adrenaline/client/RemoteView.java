package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.SkippedTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

import java.util.*;

/**
 * The interface Remote view.
 */
public interface RemoteView extends ModelEventsListenerInterface, Observable {

    /**
     * Gets local model.
     *
     * @return the local model
     */
    LocalModel getLocalModel();

    /**
     * Gets client.
     *
     * @return the client
     */
    Client getClient();

    @Override
    default void handleEvent(ConnectionResponseEvent event) throws HandlerNotImplementedException {
        getClient().setClientID(event.getClientID());

        showMessage(event.getMessage());
        showLogin(event.getAvailableAvatars());
    }

    @Override
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        if (!event.isSuccessful()) {
            showLogin(event.getAvailableAvatars());
        } else {
            updatePlayerInfo(event.getPlayer());
            showLoginSuccessful();
            if (event.isReconnection()) {
                setLocalModel(event.getClientModel());

                //Placing ammo cards on map
                getLocalModel().getGameBoard().resetAmmoCards(getLocalModel().getServerAmmoCardsSetup());
                showAmmoCardResettingUpdate(getLocalModel().getGameBoard().getCoordinatesAmmoCardMap());

                //Placing weapon cards on spawn points
                getLocalModel().getGameBoard().resetWeapons(getLocalModel().getServerWeaponCardsSetup());
                showWeaponCardResettingUpdate(getLocalModel().getWeaponCardsSetup());

                //Placing Players on map
                getLocalModel().getPlayersAvatarMap().keySet().forEach(player -> getLocalModel().getGameBoard().setPlayerPosition(player, getLocalModel().getServerPlayerPositions().get(player)));

                showBeginMatch();
            }
        }
    }

    @Override
    default void handleEvent(MatchStartedEvent event) throws HandlerNotImplementedException {
        updateMatchPlayers(event.getPlayerToAvatarMap());
        List<String> players = new ArrayList<>(event.getPlayerToAvatarMap().keySet());
        initPlayersBoard(players);
        showMapVote(event.getAvailableMaps());
    }

    @Override
    default void handleEvent(BeginMatchEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());

        //Placing ammo cards on map
        getLocalModel().getGameBoard().resetAmmoCards(event.getAmmoCardsSetup());
        showAmmoCardResettingUpdate(event.getAmmoCardsSetup());

        //Placing weapon cards on spawn points
        getLocalModel().getGameBoard().resetWeapons(event.getWeaponCardsSetup());
        showWeaponCardResettingUpdate(event.getWeaponCardsSetup());


        //todo to complete with info about the match and relative visualization
        showBeginMatch();
    }

    @Override
    default void handleEvent(BeginTurnEvent event) throws HandlerNotImplementedException {
        showBeginTurnUpdate(event.getPlayer());
    }

    @Override
    default void handleEvent(SpawnSelectionRequestEvent event) throws HandlerNotImplementedException {
        if (event.isFirstSpawn()) {
            List<PowerUpCardClient> cards = new ArrayList<>(event.getSelectableCards());
            showMessage("Select power up to keep. The other will be discarded and used to spawn.");
            PowerUpCardClient toKeep = selectPowerUpToKeep(cards);
            cards.remove(toKeep);
            PowerUpCardClient spawnCard = cards.get(0);
            notifyObservers(new SpawnPowerUpSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), toKeep, spawnCard, true));
        } else {
            showMessage("Select the power up to discard:");
            List<PowerUpCardClient> powerUps = new ArrayList<>();
            powerUps.addAll(getLocalModel().getPowerUpCards());
            powerUps.addAll(event.getSelectableCards());
            PowerUpCardClient spawnCard = selectPowerUpToKeep(powerUps);
            notifyObservers(new SpawnPowerUpSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), null, spawnCard, false));
        }
    }

    @Override
    default void handleEvent(PlayerSpawnedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        String player = event.getPlayer();
        localModel.getGameBoard().setPlayerPosition(player, event.getSpawnPoint());
        if (!event.isFirsSpawn()) {
            PowerUpCardClient powerUpToDiscard;

            if (event.getPlayer().equals(getClient().getPlayerName())) {
                powerUpToDiscard = getLocalModel().getPowerUpCardById(event.getDiscardedPowerUp().getId());
                if (powerUpToDiscard == null)
                    powerUpToDiscard = event.getDiscardedPowerUp();
                else
                    getLocalModel().removePowerUp(powerUpToDiscard);
                showPowerUpDiscardedUpdate(player, powerUpToDiscard);
            } else {
                if (event.ownedDiscardedPowerUp())
                    getLocalModel().getOpponentInfo(event.getPlayer()).removePowerUp();
                showPowerUpDiscardedUpdate(player, event.getDiscardedPowerUp());
            }
        }

        showSpawnUpdate(player, event.getSpawnPoint(), event.getDiscardedPowerUp(), !player.equals(getClient().getPlayerName()));
    }

    @Override
    default void handleEvent(ActionRequestEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(getClient().getPlayerName())) {
            AbstractViewEvent generatedEvent = null;
            while (generatedEvent == null) {
                PlayerAction choice = selectAction();
                List<Coordinates> path;
                switch (choice) {
                    case MOVE:
                        path = getPathFromUser(event.getSimpleMovesMax());
                        generatedEvent = path.isEmpty() ? null : new MovePlayEvent(getClient().getClientID(), getClient().getPlayerName(), path);
                        break;

                    case SHOOT:
                        List<WeaponCardClient> loadedWeapons = new ArrayList<>();
                        path = getPathFromUser(event.getShootingMovesMax());
                        for (WeaponCardClient weaponCardClient : getLocalModel().getWeaponCards()) {
                            if (weaponCardClient.isLoaded())
                                loadedWeapons.add(weaponCardClient);
                        }
                        WeaponCardClient shootingWeapon = selectWeaponCardFromHand(loadedWeapons);
                        if (shootingWeapon != null)
                            generatedEvent = new WeaponToUseSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), shootingWeapon.getWeaponName(), path);

                        break;

                    case COLLECT:
                        path = getPathFromUser(event.getCollectingMovesMax());
                        GameBoardClient gameBoard = getLocalModel().getGameBoard();
                        BoardSquareClient finalPosition = path.isEmpty() ?
                                gameBoard.getPlayerPositionByName(getClient().getPlayerName()) : gameBoard.getBoardSquareByCoordinates(path.get(path.size() - 1));
                        generatedEvent = collectionHandling(generatedEvent, path, finalPosition);
                        break;
                    case USE_POWER_UP:
                        List<PowerUpCardClient> usablePowerUps = new ArrayList<>();
                        PowerUpCardClient powerUpToUse = null;
                        for (PowerUpCardClient powerUpCardClient: getLocalModel().getPowerUpCards()) {
                            if (Arrays.asList("Newton", "Teleporter").contains(powerUpCardClient.getPowerUpType()))
                                usablePowerUps.add(powerUpCardClient);
                        }
                        if (!usablePowerUps.isEmpty()) {
                            powerUpToUse = selectPowerUp(usablePowerUps);
                        }
                        if (powerUpToUse != null)
                            generatedEvent = new UseTurnPowerUpEvent(getClient().getClientID(), getClient().getPlayerName(), powerUpToUse.getId());

                }
                if (generatedEvent == null)
                    showMessage("This action can't be performed... choose again.");
            }
            notifyObservers(generatedEvent);
        }
    }

    /**
     * Collection handling abstract view event.
     *
     * @param generatedEvent the generated event
     * @param path the path
     * @param finalPosition the final position
     * @return the abstract view event
     */
    default AbstractViewEvent collectionHandling(AbstractViewEvent generatedEvent, List<Coordinates> path, BoardSquareClient finalPosition) {
        if (finalPosition.isSpawnBoard()) {
            List<WeaponCardClient> collectibleWeapons = calculatePayableWeapons(finalPosition.getWeaponCards(), getLocalModel(), false);

            WeaponCardClient selectedWeapon = selectWeaponCardFromSpawnSquare(finalPosition.getCoordinates(), collectibleWeapons);  /** SELECTION HERE */

            if (selectedWeapon != null) { //Weapon has been selected
                //Selection of the power ups the user wants to pay with
                List<PowerUpCardClient> powerUpsSelected = getPowerUpsToPay(selectedWeapon.getLoader());

                //Confirmation of the feasibility of the payment, if the payment isn't feasible generated event remains null and the procedure is repeated
                if (getLocalModel().canPay(selectedWeapon.getLoader(), powerUpsSelected)) {
                    if (getLocalModel().isWeaponHandFull()) {
                        showMessage("You have too many weapons, drop one:");
                        WeaponCardClient weaponCardToDiscard = selectWeaponCardFromHand(getLocalModel().getWeaponCards());    /**SELECTION HERE*/
                        if (weaponCardToDiscard != null)
                            generatedEvent = new WeaponCollectionEvent(
                                    getClient().getClientID(),
                                    getClient().getPlayerName(),
                                    path == null ? new ArrayList<>() : path,
                                    selectedWeapon,
                                    weaponCardToDiscard,
                                    powerUpsSelected);
                    } else
                        generatedEvent = new WeaponCollectionEvent(
                                getClient().getClientID(),
                                getClient().getPlayerName(),
                                path == null ? new ArrayList<>() : path,
                                selectedWeapon,
                                null,
                                powerUpsSelected);
                }
            } else
                showMessage("Looks like you changed your mind... or maybe you're just too poor.");
        } else if (finalPosition.hasAmmoCard()) {
            generatedEvent = new CollectPlayEvent(getClient().getClientID(), getClient().getPlayerName(), path == null ? new ArrayList<>() : path);
        } else {
            generatedEvent = null;
        }
        return generatedEvent;
    }

    @Override
    default void handleEvent(FinalFrenzyActionRequestEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(getClient().getPlayerName())) {
            AbstractViewEvent generatedEvent = null;
            while (generatedEvent == null) {
                PlayerAction choice = selectAction();
                List<Coordinates> path;
                switch (choice) {
                    case MOVE:
                        if (!event.isSingleAction()) {
                            path = getPathFromUser(event.getSimpleMovesMax());
                            generatedEvent = path.isEmpty() ? null : new MovePlayEvent(getClient().getClientID(), getClient().getPlayerName(), path);
                        }
                        break;

                    case SHOOT:
                        List<WeaponCardClient> selectableWeapons = new ArrayList<>();
                        path = getPathFromUser(event.getShootingMovesMax());
                        for (WeaponCardClient weaponCardClient : getLocalModel().getWeaponCards()) {
                            if (weaponCardClient.isLoaded() || getLocalModel().canPay(weaponCardClient.getPrice()))
                                selectableWeapons.add(weaponCardClient);
                        }
                        WeaponCardClient shootingWeapon = selectWeaponCardFromHand(selectableWeapons);
                        if (shootingWeapon != null) {
                            if (shootingWeapon.isLoaded())
                                generatedEvent = new WeaponToUseSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), shootingWeapon.getWeaponName(), path);
                            else {
                                //Selection of the power ups the user wants to pay with
                                List<PowerUpCardClient> powerUpsSelected = getPowerUpsToPay(shootingWeapon.getPrice());

                                //Confirmation of the feasibility of the payment, if the payment isn't feasible generated event remains null and the procedure is repeated
                                if (getLocalModel().canPay(shootingWeapon.getPrice(), powerUpsSelected)) {
                                    notifyObservers(new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), shootingWeapon, powerUpsSelected));
                                    generatedEvent = new WeaponToUseSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), shootingWeapon.getWeaponName(), path);
                                } else
                                    showMessage("You didn't select enough power ups. Select another weapon and pay your debts >:(");
                            }

                        }

                        break;

                    case COLLECT:
                        path = getPathFromUser(event.getCollectingMovesMax());
                        GameBoardClient gameBoard = getLocalModel().getGameBoard();
                        BoardSquareClient finalPosition = path.isEmpty() ?
                                gameBoard.getPlayerPositionByName(getClient().getPlayerName()) : gameBoard.getBoardSquareByCoordinates(path.get(path.size() - 1));
                        generatedEvent = collectionHandling(generatedEvent, path, finalPosition);
                        break;
                }
                if (generatedEvent == null)
                    showMessage("This action can't be performed... choose again.");
            }
            notifyObservers(generatedEvent);
        }
    }

    @Override
    default void handleEvent(WeaponCollectedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        SpawnBoardSquareClient collectionSpawnPoint = (SpawnBoardSquareClient) getLocalModel().getGameBoard().getBoardSquareByCoordinates(event.getCollectionSpawnPoint());
        WeaponCardClient weaponCard = collectionSpawnPoint.remove(event.getCollectedWeapon());
        WeaponCardClient droppedWeapon = null;

        if (event.getPlayer().equals(getClient().getPlayerName())) {

            executePayment(event.getPlayer(), event.getPowerUpsSpent(), event.getAmmosSpent());

            if (event.getDropOffWeapon() != null) {
                droppedWeapon =  localModel.removeWeapon(event.getDropOffWeapon());
                collectionSpawnPoint.addWeapon(droppedWeapon);
            }

            localModel.addWeapon(weaponCard);
            if (event.isHandFull())
                localModel.setWeaponHandFull();
        } else {
            String player = event.getPlayer();

            executePayment(player, event.getPowerUpsSpent(), event.getAmmosSpent());

            if (event.getDropOffWeapon() != null) {
                droppedWeapon =  localModel.getOpponentInfo(player).removeWeapon(event.getDropOffWeapon());
                collectionSpawnPoint.addWeapon(droppedWeapon);
            }

            localModel.getOpponentInfo(player).addWeapon(weaponCard);
        }
        weaponCard.setLoaded(true);

        showPaymentUpdate(event.getPlayer(), event.getPowerUpsSpent(), event.getAmmosSpent());
        showWeaponCollectionUpdate(event.getPlayer(), weaponCard, droppedWeapon, collectionSpawnPoint.getRoom());
    }

    @Override
    default void handleEvent(SelectableEffectsEvent event) throws HandlerNotImplementedException {
        if (!event.getCallableEffects().isEmpty()) {
            WeaponCardClient weaponCard = null;
            WeaponEffectClient selectedEffect = null;
            List<PowerUpCardClient> powerUpCardsToPay = new ArrayList<>();
            boolean repeatSelection = true;
            boolean usageConfirmation = true;
            List<WeaponEffectClient> toSelect = new ArrayList<>();
            while (repeatSelection) {
                for (WeaponCardClient weaponCardClient: getLocalModel().getWeaponCards()) {
                    if (event.getWeapon().equals(weaponCardClient.getWeaponName()))
                        weaponCard = weaponCardClient;
                }
                for (WeaponEffectClient weaponEffectClient: weaponCard.getEffects()) {
                    if (event.getCallableEffects().contains(weaponEffectClient.getEffectName()) && getLocalModel().canPay(weaponEffectClient.getPrice()))
                        toSelect.add(weaponEffectClient);
                }

                if (!toSelect.isEmpty()) {
                    selectedEffect = selectWeaponEffect(weaponCard, toSelect);
                    if (selectedEffect == null) {
                        repeatSelection = false;
                        usageConfirmation = false;
                    } else {
                        powerUpCardsToPay = getPowerUpsToPay(selectedEffect.getPrice());
                        if (!getLocalModel().canPay(selectedEffect.getPrice(), powerUpCardsToPay)) {
                            showMessage("The power ups you selected are not enough to pay, are you sure you want to play this effect?[Y=1/n=0]");
                            usageConfirmation = selectYesOrNot();
                            repeatSelection = usageConfirmation;
                        } else
                            repeatSelection = false;
                    }
                } else {
                    repeatSelection = false;
                    usageConfirmation = false;
                    showMessage("No more selectable effects :(");
                }
            }
            if (usageConfirmation) {
                weaponCard.setLoaded(false);
                notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedEffect.getEffectName(), powerUpCardsToPay));
            } else
                notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), null, null));

        } else
            notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), null, null));
    }

    @Override
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        GameBoardClient gameBoard = getLocalModel().getGameBoard();
        List<Coordinates> path = event.getPath();
        Coordinates finalPositionCoordinates = path.get(path.size()-1);
        gameBoard.setPlayerPosition(event.getPlayer(), finalPositionCoordinates);
        showPlayerMovementUpdate(event.getPlayer(), path);
    }

    @Override
    default void handleEvent(PowerUpCollectedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        if (event.isActuallyCollected()) {
            if (event.getPlayer().equals(getClient().getPlayerName())) {
                localModel.addPowerUp(event.getCollectedCard());
                showPowerUpCollectionUpdate(event.getPlayer(), event.getCollectedCard(), false);
            } else {
                localModel.getOpponentInfo(event.getPlayer()).addPowerUp();
                showPowerUpCollectionUpdate(event.getPlayer(), event.getCollectedCard(), true);
            }
        } else
            showMessage("E' ARRIVATO MA NON VIENE COLLECTATO!!!!!!!!!!!"); // TODO we should change this message
    }

    @Override
    default void handleEvent(PaymentExecutedEvent event) throws HandlerNotImplementedException {
        executePayment(event.getPlayer(), event.getPowerUpsSpent(), event.getAmmosSpent());
        showPaymentUpdate(event.getPlayer(), event.getPowerUpsSpent(), event.getAmmosSpent());
    }

    @Override
    default void handleEvent(SkippedTurnEvent event) throws HandlerNotImplementedException {
        showMessage(event.getPlayer() + " skipped his turn!");
    }

    @Override
    default void handleEvent(ReloadWeaponsRequestEvent event) throws HandlerNotImplementedException {
        AbstractViewEvent generatedEvent = null;
        List<WeaponCardClient> weaponsList = getLocalModel().getWeaponCards();
        List<WeaponCardClient> reloadableWeapons;

        reloadableWeapons = calculatePayableWeapons(weaponsList, getLocalModel(), true);
        for (WeaponCardClient weaponCardClient: new ArrayList<>(reloadableWeapons)) {
            if (weaponCardClient.isLoaded())
                reloadableWeapons.remove(weaponCardClient);
        }

        if (!reloadableWeapons.isEmpty()) {

            showMessage("Select a weapon you want to reload!");

            while (generatedEvent == null) {

                WeaponCardClient selectedWeapon = selectWeaponCardFromHand(reloadableWeapons);  /** SELECTION HERE */

                if (selectedWeapon != null) { //Weapon has been selected
                    //Selection of the power ups the user wants to pay with
                    List<PowerUpCardClient> powerUpsSelected = getPowerUpsToPay(selectedWeapon.getPrice());

                    //Confirmation of the feasibility of the payment, if the payment isn't feasible generated event remains null and the procedure is repeated
                    if (getLocalModel().canPay(selectedWeapon.getPrice(), powerUpsSelected))
                        generatedEvent = new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), selectedWeapon, powerUpsSelected);
                    else
                        showMessage("You didn't select enough power ups. Select another weapon and pay your debts >:(");
                } else
                    generatedEvent = new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), null, null);
            }
        } else {
            showMessage("You can't reload any weapon.");
            generatedEvent = new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), null, null);
        }

        notifyObservers(generatedEvent);
    }

    @Override
    default void handleEvent(WeaponReloadedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        WeaponCardClient weaponReloaded;
        if (event.getPlayer().equals(getClient().getPlayerName())) {
            weaponReloaded = localModel.getWeaponByName(event.getWeaponReloaded());
        } else {
            PlayerClient player = localModel.getOpponentInfo(event.getPlayer());
            weaponReloaded = player.getWeaponByName(event.getWeaponReloaded());
        }
        weaponReloaded.setLoaded(true);
        showWeaponReloadedUpdate(event.getPlayer(), weaponReloaded);
    }

    @Override
    default void handleEvent(AmmoCollectedEvent event) throws HandlerNotImplementedException {
        if (event.isActuallyCollected())
            if (event.getPlayer().equals(getClient().getPlayerName()))
                getLocalModel().addAmmo(event.getAmmoCollected());
            else
                getLocalModel().getOpponentInfo(event.getPlayer()).addAmmo(event.getAmmoCollected());
        showAmmoCollectedUpdate(event.getPlayer(), event.getAmmoCollected(), event.isActuallyCollected(), event.isLastOfCard());
    }

    @Override
    default void handleEvent(SelectableBoardSquaresEvent event) throws HandlerNotImplementedException {
        showMessage("Select a square");
        List<Coordinates> selectedCoordinates = getTargetCoordinates(event.getSelectableBoardSquares(), event.getMaxSelectableItems());
        notifyObservers(new SquareSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedCoordinates));
    }

    @Override
    default void handleEvent(SelectablePlayersEvent event) throws HandlerNotImplementedException {
        List<String> selectedPlayers = getTargetPlayers(event.getSelectablePlayers(), event.getMaxSelectableItems());
        notifyObservers(new PlayersSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedPlayers));
    }

    @Override
    default void handleEvent(SelectableRoomsEvent event) throws HandlerNotImplementedException {
        Room selectedRoom = selectRoom(event.getSelectableRooms());
        showMessage("Select room to damage");
        notifyObservers(new RoomSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedRoom));
    }

    @Override
    default void handleEvent(SelectDirectionEvent event) throws HandlerNotImplementedException {
        showMessage("Select a direction:");
        ArrayList<MoveDirection> directions = new ArrayList<>();
        directions.addAll(Arrays.asList(MoveDirection.values()));
        directions.remove(MoveDirection.STOP);
        MoveDirection direction = selectDirection(getLocalModel().getGameBoard().getPlayerPositionByName(getClient().getPlayerName()), directions);
        notifyObservers(new DirectionSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), direction));
    }

    @Override
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
        getLocalModel().getGameBoard().resetAmmoCards(event.getAmmoCardsReplaced());
        showAmmoCardResettingUpdate(event.getAmmoCardsReplaced());

        getLocalModel().getGameBoard().resetWeapons(event.getWeaponCardsReplaced());
        showWeaponCardResettingUpdate(event.getWeaponCardsReplaced());

        showEndTurnUpdate(event.getPlayer());
    }

    @Override
    default void handleEvent(PlayerMovedByWeaponEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        localModel.getGameBoard().setPlayerPosition(event.getMovedPlayer(), event.getFinalPosition());
        showPlayerMovedByWeaponUpdate(event.getPlayer(), event.getMovedPlayer(), event.getMovingWeapon(), event.getFinalPosition());
    }

    @Override
    default void handleEvent(PlayerShotEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        localModel.performDamage(getClient().getPlayerName(), event.getTarget(), event.getDamages(), event.getMarks(), event.getMarksDelivered());
        showShotPlayerUpdate(event.getTarget(), event.getDamages(), event.getMarks(), event.getMarksDelivered());
    }

    @Override
    default void handleEvent(AmmoCardCollectedEvent event) throws HandlerNotImplementedException {
        GameBoardClient gameBoard = getLocalModel().getGameBoard();

        gameBoard.getBoardSquareByCoordinates(event.getBoardSquare()).removeAmmoCard();
        showAmmoCardCollectedUpdate(event.getPlayer(), event.getAmmoCardCollected(), event.getBoardSquare());
    }

    @Override
    default void handleEvent(PlayerDeathEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        localModel.getGameBoard().removePlayer(event.getDeadPlayer());
        showSuddenDeadUpdate(event.getDeadPlayer());
    }

    @Override
    default void handleEvent(PointsAssignmentEvent event) throws HandlerNotImplementedException {
        getLocalModel().updatePoints(event.getPlayerPointsMap());
        showPointsUpdate(event.getPlayerPointsMap());
    }

    @Override
    default void handleEvent(CleanPlayerBoardEvent event) throws HandlerNotImplementedException {
        if (getClient().getPlayerName().equals(event.getPlayer()))
            getLocalModel().clearDamages();
        else
            getLocalModel().getOpponentInfo(event.getPlayer()).cleanPlayerBoard();
        showShotPlayerUpdate(event.getPlayer(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    default void handleEvent(SkullRemovalEvent event) throws HandlerNotImplementedException {
        if (!getLocalModel().getKillTrack().isFull()) {
            if (event.getDeadPlayer().equals(getClient().getPlayerName()))
                getLocalModel().addSkull();
            else
                getLocalModel().getOpponentInfo(event.getDeadPlayer()).addSkull();
        }
        getLocalModel().getKillTrack().removeSkull(event.getDamageMark(), event.isOverkill());
        showSkullRemovalUpdate(event.getDeadPlayer());
    }

    @Override
    default void handleEvent(FinalFrenzyBeginEvent event) throws HandlerNotImplementedException {
        showMessage("LAST SKULL REMOVED! FINAL FRENZY BEGINS!!!!!!!");
        showFinalFrenzyBegin(event.getPlayerBoardsToSwitch());
    }

    @Override
    default void handleEvent(UsablePowerUpsEvent event) throws HandlerNotImplementedException {
        if (event.getUsableTypes().contains("Targeting Scope")) {
            targetingHandle(event);
        } else if (event.getUsableTypes().contains("Tagback Grenade")) {
            showMessage("You can use Tagback Grenade on " + event.getTarget() + ", select the power up you want to use or proceed");
            List<PowerUpCardClient> usablePowerUps = new ArrayList<>();
            for (PowerUpCardClient powerUpCardClient : getLocalModel().getPowerUpCards()) {
                if (event.getUsableTypes().contains(powerUpCardClient.getPowerUpType()))
                    usablePowerUps.add(powerUpCardClient);
            }
            PowerUpCardClient powerUpSelected = null;
            if (!usablePowerUps.isEmpty())
                powerUpSelected = selectPowerUp(usablePowerUps);
            if (powerUpSelected != null) {
                notifyObservers(new PowerUpsToUseEvent(
                        getClient().getClientID(),
                        getClient().getPlayerName(),
                        powerUpSelected.getId(),
                        event.getTarget(),
                        null,
                        null,
                        "Tagback Grenade"
                ));
            } else
                notifyObservers(new PowerUpRefusedEvent(getClient().getClientID(), getClient().getPlayerName()));
        }
    }

    @Override
    default void handleEvent(MatchEndedEvent event) throws HandlerNotImplementedException {
        showMatchEnded(event.getWinner());
    }

    @Override
    default void handleEvent(NotAllowedPlayEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        showMessage("ACTION FAILED!");
    }

    //TODO to implement
    @Override
    default void handleEvent(AbstractModelEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement
    @Override
    default void handleEvent(ConnectionTimeoutEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement
    //TODO to implement

    @Override
    default void handleEvent(SelectedMapEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());
        showMap();
    }

    //TODO to implement

    @Override
    default void handleEvent(PlayerUpdateEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    @Override
    default void handleEvent(PlayerDisconnectedEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(getClient().getPlayerName()))
            getLocalModel().setDisconnected(true);
        else
            getLocalModel().getOpponentInfo(event.getPlayer()).setDisconnected(true);

        showPlayerDisconnection(event.getPlayer());
    }

    @Override
    default void handleEvent(PlayerReconnectedEvent event) throws HandlerNotImplementedException {
        showPlayerReconnection(event.getPlayer());
    }

    /**
     * Error.
     *
     * @param e the e
     */
    void error(Exception e);


    /**
     * Sets local model.
     *
     * @param clientModel the client model
     */
    void setLocalModel(LocalModel clientModel);


    /**
     * OUTGOING communications
     *
     * @param nickname the nickname
     * @param avatar the avatar
     */
    default void notifyLoginRequestEvent(String nickname, Avatar avatar) {
        notifyObservers(new LoginRequestEvent(getClient().getClientID(), nickname, avatar));
    }

    /**
     * Notify map vote event.
     *
     * @param mapType the map type
     */
    default void notifyMapVoteEvent(MapType mapType) {
        notifyObservers(new MapVoteEvent(getClient().getClientID(), getClient().getPlayerName(), mapType));
    }


    /**
     * LOCAL MODEL MODIFIERS--------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     * @param player the player
     */
    default void updatePlayerInfo(String player) {
        getClient().setPlayerName(player);
    }

    /**
     * Update match players.
     *
     * @param playerToAvatarMap the player to avatar map
     */
    default void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap) {
        getLocalModel().setPlayerToAvatarMap(playerToAvatarMap);
    }

    /**
     * Init players board.
     *
     * @param players the players
     */
    default void initPlayersBoard(List<String> players) {
        getLocalModel().setPlayerName(getClient().getPlayerName());
        players.remove(getClient().getPlayerName());

        for (String player : players) {
            getLocalModel().addOpponent(player);
        }
    }

    /**
     * Build local map.
     *
     * @param mapType the map type
     */
    default void buildLocalMap(MapType mapType) {
        getLocalModel().generatesGameBoard(mapType);
    }

    /**
     * Execute payment.
     *
     * @param player the player
     * @param powerUpsToPay the power ups to pay
     * @param ammosToPay the ammos to pay
     */
    default void executePayment(String player, List<PowerUpCardClient> powerUpsToPay, List<Ammo> ammosToPay) {
        if (player.equals(getClient().getPlayerName())) {
            List<PowerUpCardClient> actualPowerUpsToPay = extractActualPowerUps(powerUpsToPay);
            for (PowerUpCardClient powerUpCardClient : actualPowerUpsToPay)
                getLocalModel().removePowerUp(powerUpCardClient);
            for (Ammo ammo : ammosToPay)
                getLocalModel().removeAmmo(ammo);
        } else {
            PlayerClient opponent = getLocalModel().getOpponentInfo(player);
            for (int i = 0; i < powerUpsToPay.size(); i++)
                opponent.removePowerUp();
            for (Ammo ammo : ammosToPay)
                opponent.removeAmmo(ammo);
        }
    }


    /**
     * UPDATES SHOW METHODS--------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     * @param message the message
     */
    void showMessage(Object message);

    /**
     * Show match ended.
     *
     * @param winner the winner
     */
    default void showMatchEnded(String winner) {
        showMessage("AND THE WINNER IS " + winner + "!!!!!!!!!!!!!");
    }

    /**
     * Show map.
     */
    void showMap();

    /**
     * Show login.
     *
     * @param availableAvatars the available avatars
     */
    void showLogin(List<Avatar> availableAvatars);

    /**
     * Show login successful.
     */
    void showLoginSuccessful();

    /**
     * Show map vote.
     *
     * @param availableMaps the available maps
     */
    void showMapVote(List<MapType> availableMaps);

    /**
     * Show begin match.
     */
    void showBeginMatch();

    /**
     * Show payment update.
     *
     * @param player the player
     * @param powerUpCardClients the power up card clients
     * @param ammos the ammos
     */
    void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos);

    /**
     * Show weapon collection update.
     *
     * @param player the player
     * @param collectedCard the collected card
     * @param droppedCard the dropped card
     * @param roomColor the room color
     */
    void showWeaponCollectionUpdate(String player, WeaponCardClient collectedCard, WeaponCardClient droppedCard, Room roomColor);

    /**
     * Show skull removal update.
     *
     * @param deadPlayer the dead player
     */
    default void showSkullRemovalUpdate(String deadPlayer) {}

    /**
     * Show points update.
     *
     * @param pointsMap the points map
     */
    default void showPointsUpdate(Map<String, Integer> pointsMap) {
        for (Map.Entry<String, Integer> playerPoints: pointsMap.entrySet())
            showMessage("-" + playerPoints.getKey() + " received " + playerPoints.getValue() + " points.");
    }

    /**
     * Show sudden dead update.
     *
     * @param deadPlayer the dead player
     */
    default void showSuddenDeadUpdate(String deadPlayer) {
        showMessage(deadPlayer.equals(getClient().getPlayerName()) ? "You " : deadPlayer +
                " died... poor fool.");
    }

    /**
     * Show player moved by weapon update.
     *
     * @param attackingPlayer the attacking player
     * @param playerMoved the player moved
     * @param weaponUsed the weapon used
     * @param finalPosition the final position
     */
    default void showPlayerMovedByWeaponUpdate(String attackingPlayer, String playerMoved, String weaponUsed, Coordinates finalPosition) {
        String message;
        if (attackingPlayer.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = attackingPlayer;
        message = message + " moved ";
        if (playerMoved.equals(getClient().getPlayerName()))
            message = message + "you ";
        else
            message = message + playerMoved;
        message = message + " to square " + getLocalModel().getGameBoard().getBoardSquareByCoordinates(finalPosition) + " using weapon " + weaponUsed + "!";
        showMessage(message);
    }

    /**
     * Show shot player update.
     *
     * @param damagedPlayer the damaged player
     * @param damages the damages
     * @param marks the marks
     * @param removedMarks the removed marks
     */
    default void showShotPlayerUpdate(String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {
        showMessage(damagedPlayer + " received damages " + damages + " and marks " + marks);
    }

    /**
     * Show player movement update.
     *
     * @param player the player
     * @param path the path
     */
    default void showPlayerMovementUpdate(String player, List<Coordinates> path) {
        if (path != null && path.size() > 0) {
            if (player.equals(getClient().getPlayerName()))
                showMessage("You moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size() - 1)) + " through the path: ");
            else
                showMessage("Player " + player + " moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size() - 1)) + " through the path: ");
            for (Coordinates coordinates : path)
                showMessage(getLocalModel().getGameBoard().getBoardSquareByCoordinates(coordinates));
        }
    }

    /**
     * Show power up discarded update.
     *
     * @param player the player
     * @param powerUpCardClient the power up card client
     */
    default void showPowerUpDiscardedUpdate(String player, PowerUpCardClient powerUpCardClient) {
        showPaymentUpdate(player, new ArrayList<>(Arrays.asList(powerUpCardClient)), new ArrayList<>());
    }

    /**
     * Show spawn update.
     *
     * @param player the player
     * @param spawnPoint the spawn point
     * @param spawnCard the spawn card
     * @param isOpponent the is opponent
     */
    default void showSpawnUpdate(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {
        if (isOpponent)
            showMessage("Player " + player + " spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
        else
            showMessage("You spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
    }

    /**
     * Show power up collection update.
     *
     * @param player the player
     * @param cardCollected the card collected
     * @param isOpponent the is opponent
     */
    void showPowerUpCollectionUpdate(String player, PowerUpCardClient cardCollected, boolean isOpponent);

    /**
     * Show begin turn update.
     *
     * @param player the player
     */
    default void showBeginTurnUpdate(String player) {
        if (player.equals(getClient().getPlayerName())) {
            showMessage("Your turn began!!!");
        } else
            showMessage(player + "'s turn began!");
    }

    /**
     * Show end turn update.
     *
     * @param player the player
     */
    default void showEndTurnUpdate(String player) {
        if (player.equals(getClient().getPlayerName()))
            showMessage("YEAH!! You concluded your turn!");
        else
            showMessage(player + " concluded his turn!");
    }

    /**
     * Show ammo collected update.
     *
     * @param player the player
     * @param ammo the ammo
     * @param actuallyCollected the actually collected
     * @param lastOfCard the last of card
     */
    default void showAmmoCollectedUpdate(String player, Ammo ammo, boolean actuallyCollected, boolean lastOfCard) {
        if (player.equals(getClient().getPlayerName()))
            showMessage("You collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but your playerBoard was full!"));
        else
            showMessage(player + " collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but his playerBoard was full!"));
    }

    /**
     * Show ammo card collected update.
     *
     * @param player the player
     * @param ammoCard the ammo card
     * @param coordinates the coordinates
     */
    default void showAmmoCardCollectedUpdate(String player, AmmoCardClient ammoCard, Coordinates coordinates) {
        String message = " gathered the ammo card " + ammoCard.toString() + " from square " + coordinates + "!";
        if (player.equals(getClient().getPlayerName()))
            showMessage("You " + message);
        else
            showMessage(player + message);
    }

    /**
     * Show ammo card resetting update.
     *
     * @param coordinatesAmmoCardMap the coordinates ammo card map
     */
    default void showAmmoCardResettingUpdate(Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap) {
        showMessage("Gathered ammo cards are being replaced...");
    }

    /**
     * Show weapon card resetting update.
     *
     * @param coordinatesWeaponsMap the coordinates weapons map
     */
    default void showWeaponCardResettingUpdate(Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap) {
        showMessage("Weapons are being placed on the spawn points...");
    }

    //Implemented on CLI

    /**
     * Show weapon reloaded update.
     *
     * @param player the player
     * @param weaponCard the weapon card
     */
    default void showWeaponReloadedUpdate(String player, WeaponCardClient weaponCard) {
        showMessage(player + " reloaded " + weaponCard.getWeaponName() + "!");
    }

    /**
     * Show player disconnection.
     *
     * @param player the player
     */
    void showPlayerDisconnection(String player);

    /**
     * Show player reconnection.
     *
     * @param player the player
     */
    void showPlayerReconnection(String player);

    /**
     * Show final frenzy begin.
     *
     * @param playerBoardsToSwitch the player boards to switch
     */
    default void showFinalFrenzyBegin(List<String> playerBoardsToSwitch) {
    }

    ;

    /**
     * INTERACTION (INPUT) METHODS   NB: THEY MUST NOT BE VOID FUNCTIONS --------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     * @param currentPose the current pose
     * @param availableMoves the available moves
     * @return the move direction
     */
    MoveDirection selectDirection(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves);

    /**
     * Select action player action.
     *
     * @return the player action
     */
    PlayerAction selectAction();

    /**
     * Select power up to keep power up card client.
     *
     * @param cards the cards
     * @return the power up card client
     */
    PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards);

    /**
     * Select power up power up card client.
     *
     * @param cards the cards
     * @return the power up card client
     */
    PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards);

    /**
     * Select coordinates coordinates.
     *
     * @param coordinates the coordinates
     * @return the coordinates
     */
    Coordinates selectCoordinates(List<Coordinates> coordinates);

    /**
     * Select player string.
     *
     * @param players the players
     * @return the string
     */
    default String selectPlayer(List<String> players) {
        if (players.isEmpty())
            return null;
        int count = 0;
        showMessage("Select a player: ");
        for (String player : players) {
            count++;
            showMessage(count + ") " + player);
        }
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            showMessage("enter:");
            choice = scanner.nextInt();
        } while (choice < 1 || count > players.size());
        return players.get(choice - 1);
    }

    /**
     * Select room room.
     *
     * @param rooms the rooms
     * @return the room
     */
//Implemented on CLI
    default Room selectRoom(List<Room> rooms) {
        if (rooms.isEmpty())
            return null;
        int count = 0;
        showMessage("Select a player: ");
        for (Room room : rooms) {
            count++;
            showMessage(count + ") " + room);
        }
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            showMessage("enter:");
            choice = scanner.nextInt();
        } while (choice < 1 || count > rooms.size());
        return rooms.get(choice - 1);
    }

    /**
     * Select weapon card from spawn square weapon card client.
     *
     * @param coordinates the coordinates
     * @param selectableWeapons the selectable weapons
     * @return the weapon card client
     */
    default WeaponCardClient selectWeaponCardFromSpawnSquare(Coordinates coordinates, List<WeaponCardClient> selectableWeapons) {
        if (selectableWeapons.isEmpty()) {
            showMessage("No weapons to select.");
            return null;
        } else {
            showMessage("Select a weapon from the map:");
            int count = 0;
            for (WeaponCardClient weaponCardClient : selectableWeapons) {
                count++;
                showMessage(count + ") " + weaponCardClient);
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                showMessage("enter:");
                choice = scanner.nextInt();
            } while (choice < 1 || choice > selectableWeapons.size());
            return selectableWeapons.get(choice - 1);
        }
    }

    /**
     * Select ammo type ammo.
     *
     * @param selectableAmmos the selectable ammos
     * @return the ammo
     */
    default Ammo selectAmmoType(List<Ammo> selectableAmmos) {
        return selectableAmmos.get(new Random().nextInt(selectableAmmos.size()));
    }

    /**
     * Select weapon card from hand weapon card client.
     *
     * @param selectableWeapons the selectable weapons
     * @return the weapon card client
     */
    WeaponCardClient selectWeaponCardFromHand(List<WeaponCardClient> selectableWeapons);

    /**
     * Select weapon effect weapon effect client.
     *
     * @param weapon the weapon
     * @param callableEffects the callable effects
     * @return the weapon effect client
     */
    default WeaponEffectClient selectWeaponEffect(WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) {
        int count = 0;
        showMessage("Select an effect to activate: ");
        for (WeaponEffectClient weaponEffectClient : callableEffects) {
            count++;
            showMessage(count + ") " + weaponEffectClient.getEffectName());
        }
        count++;
        showMessage(count + ") Stop using weapon.");

        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            showMessage("enter:");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > callableEffects.size() + 1);
        if (choice == callableEffects.size() + 1)
            return null;
        else
            return callableEffects.get(choice - 1);
    }

    /**
     * Select yes or not boolean.
     *
     * @return the boolean
     */
    default boolean selectYesOrNot() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        return choice == 1;
    }

    /**
     * COMPOSITE SELECTION METHODS--------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     * @param maxSteps the max steps
     * @return the path from user
     */
    default List<Coordinates> getPathFromUser(int maxSteps) {
        if (maxSteps == 0)
            return new ArrayList<>();

        BoardSquareClient currentPose = getLocalModel().getGameBoard().getPlayerPositionByName(getClient().getPlayerName());
        List<Coordinates> path = new ArrayList<>();
        ArrayList<MoveDirection> availableMoves = new ArrayList<>();

        MoveDirection choice;

        do {
            availableMoves.clear();
            if (currentPose == null) {
                return null;
            }

            if (currentPose.getNorth().equals(InterSquareLink.SAMEROOM) || currentPose.getNorth().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.UP);
            }
            if (currentPose.getEast().equals(InterSquareLink.SAMEROOM) || currentPose.getEast().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.RIGHT);
            }
            if (currentPose.getSouth().equals(InterSquareLink.SAMEROOM) || currentPose.getSouth().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.DOWN);
            }
            if (currentPose.getWest().equals(InterSquareLink.SAMEROOM) || currentPose.getWest().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.LEFT);
            }
            availableMoves.add(MoveDirection.STOP);

            choice = selectDirection(currentPose, availableMoves);

            currentPose = getLocalModel().getGameBoard().getBoardSquareByCoordinates(calculateCoordinates(currentPose.getCoordinates(), choice));

            if (choice != MoveDirection.STOP)
                path.add(currentPose.getCoordinates());

        } while (path.size() < maxSteps && choice != MoveDirection.STOP);

        showMessage("Path successfully generated.");

        return path;
    }

    /**
     * Gets power ups to pay.
     *
     * @param price the price
     * @return the power ups to pay
     */
    default List<PowerUpCardClient> getPowerUpsToPay(List<Ammo> price) {
        List<PowerUpCardClient> powerUpsSelected = new ArrayList<>();
        PowerUpCardClient powerUpCardUsedToPay;
        List<PowerUpCardClient> spendablePowerUps;
        List<PowerUpCardClient> playerHand = getLocalModel().getPowerUpCards();


        spendablePowerUps = calculateSpendablePowerUps(price, playerHand);

        do {
            if (!spendablePowerUps.isEmpty()) {
                showMessage("Select a power up you want to use to pay ");
                powerUpCardUsedToPay = selectPowerUp(spendablePowerUps);   /** SELECTION HERE */
            } else
                powerUpCardUsedToPay = null;

            if (powerUpCardUsedToPay != null) {
                powerUpsSelected.add(powerUpCardUsedToPay);
                playerHand.remove(powerUpCardUsedToPay);
                price.remove(powerUpCardUsedToPay.getEquivalentAmmo());
                spendablePowerUps = calculateSpendablePowerUps(price, playerHand);
            }
        } while (powerUpCardUsedToPay != null);
        return powerUpsSelected;
    }

    /**
     * Gets target coordinates.
     *
     * @param coordinates the coordinates
     * @param maxSelections the max selections
     * @return the target coordinates
     */
    default List<Coordinates> getTargetCoordinates(List<Coordinates> coordinates, int maxSelections) {
        showMessage("select a target square!");
        List<Coordinates> selectedCoordinates = new ArrayList<>();
        Coordinates selection;
        do {
            selection = selectCoordinates(coordinates);
            if (selection != null) {
                selectedCoordinates.add(selection);
                coordinates.remove(selection);
            }
        } while (selectedCoordinates.size() < maxSelections && selection != null);

        return selectedCoordinates;
    }

    /**
     * Gets target players.
     *
     * @param players the players
     * @param maxSelections the max selections
     * @return the target players
     */
    default List<String> getTargetPlayers(List<String> players, int maxSelections) {
        if (!players.isEmpty()) {
            showMessage("select a target player!");
            List<String> selectedPlayers = new ArrayList<>();
            String selection;
            // TODO fix target selection
            do {
                selection = selectPlayer(players);
                if (selection != null) {
                    selectedPlayers.add(selection);
                    players.remove(selection);
                }
            } while (selectedPlayers.size() < maxSelections && selection != null && !players.isEmpty());
            return selectedPlayers;
        } else {
            showMessage("There are no players to select :(");
            return new ArrayList<>();
        }
    }

    /**
     * SUPPORT EVALUATION AND DATA EXTRACTION METHODS--------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     * @param initialCoordinates the initial coordinates
     * @param moveDirection the move direction
     * @return the coordinates
     */
    static Coordinates calculateCoordinates(Coordinates initialCoordinates, MoveDirection moveDirection) {
        return new Coordinates(initialCoordinates.getXCoordinate() + moveDirection.getDeltaX(), initialCoordinates.getYCoordinate() + moveDirection.getDeltaY());
    }

    /**
     * Calculate spendable power ups list.
     *
     * @param price the price
     * @param powerUpCards the power up cards
     * @return the list
     */
    static List<PowerUpCardClient> calculateSpendablePowerUps(List<Ammo> price, List<PowerUpCardClient> powerUpCards) {
        List<PowerUpCardClient> spendablePowerUps = new ArrayList<>();
        for (PowerUpCardClient powerUpCardClient : powerUpCards) {
            if (price.contains(powerUpCardClient.getEquivalentAmmo()))
                spendablePowerUps.add(powerUpCardClient);
        }
        return spendablePowerUps;
    }

    /**
     * Calculate payable weapons list.
     *
     * @param weaponsList the weapons list
     * @param localModel the local model
     * @param toReload the to reload
     * @return the list
     */
    static List<WeaponCardClient> calculatePayableWeapons(List<WeaponCardClient> weaponsList, LocalModel localModel, boolean toReload) {
        List<WeaponCardClient> payableWeapons = new ArrayList<>();
        Iterator<WeaponCardClient> iterator = weaponsList.iterator();
        while (iterator.hasNext()) {
            WeaponCardClient weaponCard = iterator.next();
            List<Ammo> totalPrice = new ArrayList<>(toReload ? weaponCard.getPrice() : weaponCard.getLoader());
            if (localModel.canPay(totalPrice)) {
                payableWeapons.add(weaponCard);
            }
        }
        return payableWeapons;
    }

    /**
     * Extract actual power ups list.
     *
     * @param powerUpCopies the power up copies
     * @return the list
     */
    default List<PowerUpCardClient> extractActualPowerUps(List<PowerUpCardClient> powerUpCopies) {
        List<String> powerUpIds = new ArrayList<>();
        List<PowerUpCardClient> actualPowerUps = new ArrayList<>();
        for (PowerUpCardClient powerUpCardClient : powerUpCopies)
            powerUpIds.add(powerUpCardClient.getId());
        for (PowerUpCardClient powerUpCardClient : getLocalModel().getPowerUpCards()) {
            if (powerUpIds.contains(powerUpCardClient.getId())) {
                actualPowerUps.add(powerUpCardClient);
                powerUpIds.remove(powerUpCardClient.getId());
            }
        }
        return actualPowerUps;
    }

    /**
     * Sets disconnected.
     */
    void setDisconnected();

    /**
     * Targeting handle.
     *
     * @param event the event
     */
    default void targetingHandle(UsablePowerUpsEvent event) {
        showMessage("You can use Targeting Scope on " + event.getTarget() + ", select the power up you want to use or proceed");
        List<PowerUpCardClient> usablePowerUps = new ArrayList<>();
        for (PowerUpCardClient powerUpCardClient: getLocalModel().getPowerUpCards()) {
            if (event.getUsableTypes().contains(powerUpCardClient.getPowerUpType()))
                usablePowerUps.add(powerUpCardClient);
        }
        PowerUpCardClient powerUpSelected = null;
        if (!usablePowerUps.isEmpty())
            powerUpSelected = selectPowerUp(usablePowerUps);
        if (powerUpSelected != null) {
            List<Ammo> selectableAmmos = new ArrayList<>();
            for (PowerUpCardClient powerUpCardClient: getLocalModel().getPowerUpCards()) {
                if (!selectableAmmos.contains(powerUpCardClient.getEquivalentAmmo()) && powerUpCardClient != powerUpSelected)
                    selectableAmmos.add(powerUpCardClient.getEquivalentAmmo());
            }
            for (Ammo ammo: getLocalModel().getAmmos()) {
                if (!selectableAmmos.contains(ammo))
                    selectableAmmos.add(ammo);
            }
            Ammo ammoToUse = null;
            if (powerUpSelected != null) {
                ammoToUse = selectAmmoType(selectableAmmos);
            }
            if (ammoToUse != null) {
                List<PowerUpCardClient> spendablePowerUps = new ArrayList<>();
                for (PowerUpCardClient powerUpCardClient: getLocalModel().getPowerUpCards()) {
                    if (powerUpCardClient.getEquivalentAmmo().equals(ammoToUse))
                        spendablePowerUps.add(powerUpCardClient);
                }
                spendablePowerUps.remove(powerUpSelected);
                PowerUpCardClient powerUpToSpend = null;
                if (!spendablePowerUps.isEmpty())
                    powerUpToSpend = selectPowerUp(spendablePowerUps);
                if (getLocalModel().canPay(new ArrayList<>(Arrays.asList(ammoToUse)), new ArrayList<>()))
                    notifyObservers(new PowerUpsToUseEvent(
                            getClient().getClientID(),
                            getClient().getPlayerName(),
                            powerUpSelected.getId(),
                            event.getTarget(),
                            ammoToUse,
                            powerUpToSpend == null ? null : powerUpToSpend.getId(),
                            "Targeting Scope"
                    ));
                else notifyObservers(new PowerUpRefusedEvent(getClient().getClientID(), getClient().getPlayerName()));
            } else notifyObservers(new PowerUpRefusedEvent(getClient().getClientID(), getClient().getPlayerName()));
        } else notifyObservers(new PowerUpRefusedEvent(getClient().getClientID(), getClient().getPlayerName()));
    }
}
