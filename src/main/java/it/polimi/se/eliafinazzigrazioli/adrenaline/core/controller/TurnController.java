package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.SkippedTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.AmmoCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PointsAssignmentEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO DEFINE AND INSERT MESSAGES, IN PARTICULAR FOR NOT ALLOWED PLAY EVENT BECAUSE IT IS THROWN IN A VARIETY OF SITUATIONS

public class TurnController implements ViewEventsListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;

    private int actionsPerformed;


    private PowerUpCard powerUpForRespawn;
    private List<PowerUpCard> powerUpsForSpawn;

    private Player spawningPlayer;


    public TurnController(EventController eventController, Match match) {
        this.match = match;
        this.actionsPerformed = 0;
        eventController.addViewEventsListener(MovePlayEvent.class, this);
        eventController.addViewEventsListener(CollectPlayEvent.class, this);
        eventController.addViewEventsListener(SpawnPowerUpSelectedEvent.class, this);
        eventController.addViewEventsListener(ReloadWeaponEvent.class, this);
        eventController.addViewEventsListener(WeaponCollectionEvent.class, this);
        eventController.addViewEventsListener(EffectSelectedEvent.class, this);
    }

    /**
     * Handles the event generated when the player has to select a power up to spawn when the game begins. It spawns
     * the player on the map and adds the other power up to the player, contextually generating:
     * -{@code PlayerSpawnedEvent}
     * -{@code PowerUpCollectedEvent}
     * -{@code BeginTurnEvent}
     * and notifying them to the views.
     * @param event
     * @throws HandlerNotImplementedException
     */
    @Override
    public void handleEvent(SpawnPowerUpSelectedEvent event) throws HandlerNotImplementedException {
        List<AbstractModelEvent> events = new ArrayList<>();
        GameBoard gameBoard = match.getGameBoard();
        if (event.isFirstSpawn()) {
            Player currentPlayer = match.getCurrentPlayer();
            events.add(gameBoard.spawnPlayer(currentPlayer, event.getSpawnCard(), true));
            events.add(currentPlayer.addPowerUp(event.getToKeep(), match.getPowerUpsDeck()));
            match.getPowerUpsDeck().discardPowerUp(event.getSpawnCard());
            events.add(new ActionRequestEvent(
                    currentPlayer,
                    Rules.MAX_ACTIONS_AVAILABLE,
                    currentPlayer.getPlayerBoard().simpleMovementMaxMoves(),
                    currentPlayer.getPlayerBoard().preCollectionMaxMoves(),
                    currentPlayer.getPlayerBoard().preShootingMaxMoves()));
            spawnCompleted();
        }
        else {
            Player deadPlayer = match.getPlayer(event.getPlayer());
            events.add(gameBoard.spawnPlayer(deadPlayer, event.getSpawnCard(), false));
            PowerUpCard powerUpToDiscard = null;
            for (PowerUpCard powerUpCard: deadPlayer.getPowerUps()) {
                if (powerUpCard.getId().equals(event.getSpawnCard().getId()))
                    powerUpToDiscard = powerUpCard;
            }
            if (powerUpToDiscard != null) {
                deadPlayer.removePowerUp(powerUpToDiscard);
                match.getPowerUpsDeck().discardPowerUp(powerUpToDiscard);
                events.add(deadPlayer.addPowerUp(powerUpForRespawn, match.getPowerUpsDeck()));
            }
            else {
                match.getPowerUpsDeck().discardPowerUp(powerUpForRespawn);
                spawnCompleted();
            }
            deadPlayer.resuscitate();
            events.add(new CleanPlayerBoardEvent(deadPlayer));


            if (!match.getDeadPlayers().isEmpty()) {
                Player nextDeadPlayer = match.getDeadPlayers().get(0);
                if (nextDeadPlayer.isConnected()) {
                    powerUpForRespawn  = match.getPowerUpsDeck().drawCard();
                    spawningPlayer = match.getDeadPlayers().get(0);
                    events.add(new SpawnSelectionRequestEvent(nextDeadPlayer, Arrays.asList(powerUpForRespawn), false));
                }
                else
                    events.addAll(deadPlayersRespawnLoop(nextDeadPlayer));
            }
            else {
                events.addAll(nextTurn());
            }
        }
        match.notifyObservers(events);
    }

    /**
     * Handles a request of movement by the player. The handler guaranties that the movement is performed only if the
     * total number of moves (counting also possible moves performed before) doesn't exceed the total number of moves allowed and
     * that the total number of actions allowed is not exceeded.
     * If any of these controls fail it notifies via match a {@code NotAllowedPlayEvent}. If the controls succeed the movement is
     * performed and a {@code PlayerMovementEvent} is notified. Collaterally the {@code movementsPerformed} ins increased by
     * the number of moves performed.
     * @param event
     */
    @Override
    public void handleEvent(MovePlayEvent event) throws HandlerNotImplementedException {
        Player currentPlayer = match.getCurrentPlayer();
        if (!currentPlayer.getPlayerNickname().equals(event.getPlayer())) {
            LOGGER.info("Player " + event.getPlayer() + " tried to move out of his turn!");
            return;
        }
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        List<AbstractModelEvent> events = new ArrayList<>();
        // If this condition is verified it means that something isn't correct in the execution of the client
        // or alternatively this control can be used regularly to inhibit further actions
        if (actionsPerformed >= Rules.MAX_ACTIONS_AVAILABLE)
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));

        else if (!gameBoard.pathIsValid(currentPlayer, path) || path.size() > currentPlayer.getPlayerBoard().simpleMovementMaxMoves())
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));

        else{
            events.add(gameBoard.playerMovement(currentPlayer, path));
            events.add(concludeAction(currentPlayer));
            match.notifyObservers(events);
        }

    }

    /**
     * Handles a request of movement by the player. It controls that the total number of actions or moves allowed is not exceeded.
     * If the control fail it notifies via match a {@code NotAllowedPlayEvent}. If it succeeds the method {@code BoardSquare.collect()}.
     * The return of null value by this method means that collection can't be performed on that square because it doesn't contain
     * any item to collect. If a non null value is returned, {@code actionsPerformed} is incremented, {@code startingPosition} is updated
     * to the current position of the player and {@code movementsPerformed} is reset to 0. Then a {@code AmmoCardCollectedEvent} event
     * is notified.
     * @param event
     */
    @Override
    public void handleEvent(CollectPlayEvent event) {
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        Player currentPlayer = match.getCurrentPlayer();
        boolean movementActuated = false;
        List<AbstractModelEvent> events = new ArrayList<>();
        BoardSquare finalPosition;
        if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE && path.size() <= currentPlayer.getPlayerBoard().preCollectionMaxMoves()) {
            if (path.size() > 0) {
                finalPosition = gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1));
                movementActuated = true;
            }
            else
                finalPosition = gameBoard.getPlayerPosition(currentPlayer);
            if (movementActuated)
            {
                gameBoard.movePlayer(currentPlayer, finalPosition);
                events.add(new PlayerMovementEvent(currentPlayer, path));
            }
            if (finalPosition.ammoCollectionIsValid()) {
                AmmoCard ammoCard = ((GenericBoardSquare) finalPosition).gatherCollectables();
                events.add(new AmmoCardCollectedEvent(currentPlayer, ammoCard, finalPosition.getCoordinates()));

                for (Ammo ammo : ammoCard.getAmmos()) {
                    if (ammoCard.getAmmos().indexOf(ammo) == ammoCard.getAmmos().size() - 1)
                        events.add(new AmmoCollectedEvent(currentPlayer, ammo, currentPlayer.addAmmo(ammo), true));
                    else
                        events.add(new AmmoCollectedEvent(currentPlayer, ammo, currentPlayer.addAmmo(ammo)));
                }

                if (ammoCard.containsPowerUpCard()) {
                    PowerUpsDeck deck = match.getPowerUpsDeck();
                    events.add(currentPlayer.addPowerUp(deck.drawCard(), deck));
                }
                events.add(concludeAction(currentPlayer));
                match.notifyObservers(events);
            }
            else
                match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }
        else {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }
    }

    @Override
    public void handleEvent(WeaponCollectionEvent event) throws HandlerNotImplementedException {
        List<Coordinates> path = event.getPath() == null ? new ArrayList<>() : event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        Player currentPlayer = match.getCurrentPlayer();
        boolean movementActuated = false;
        List<AbstractModelEvent> events = new ArrayList<>();
        BoardSquare finalPosition;
        if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE && path.size() <= currentPlayer.getPlayerBoard().preCollectionMaxMoves()) {
            if (path.size() > 0) {
                finalPosition = gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1));
                movementActuated = true;
            }
            else
                finalPosition = gameBoard.getPlayerPosition(currentPlayer);

            if (movementActuated)
            {
                gameBoard.movePlayer(currentPlayer, finalPosition);
                events.add(new PlayerMovementEvent(currentPlayer, path));
            }
            if (finalPosition.isSpawnPoint()) {
                WeaponCard weaponCard = ((SpawnBoardSquare) finalPosition).collectWeapon(event.getWeaponCollected());

                List<PowerUpCard> powerUpsToSpend = currentPlayer.getRealModelReferences(event.getPowerUpsToPay());

                //weapon replacement
                WeaponCard weaponDropped = null;
                if (event.getWeaponDropped() != null) {
                    try {
                        weaponDropped = currentPlayer.removeWeapon(event.getWeaponDropped());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //weapon collection
                if (currentPlayer.canSpend(weaponCard.getLoader(), powerUpsToSpend)) {
                    try {
                        currentPlayer.addWeapon(weaponCard);
                        if (weaponDropped != null)
                            ((SpawnBoardSquare) finalPosition).addWeapon(weaponDropped);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //payment
                List<Ammo> ammosSpent = currentPlayer.spendPrice(weaponCard.getLoader(), powerUpsToSpend, match.getPowerUpsDeck());
                weaponCard.setLoaded(true);
                events.add(new WeaponCollectedEvent(currentPlayer, weaponCard, weaponDropped, finalPosition.getCoordinates(), powerUpsToSpend, ammosSpent, currentPlayer.weaponHandIsFull()));

                events.add(concludeAction(currentPlayer));
                match.notifyObservers(events);
            }
            else
                match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }
        else {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }

    }

    @Override
    public void handleEvent(EffectSelectedEvent event) {
        if (!event.getPlayer().equals(match.getCurrentPlayer().getPlayerNickname())) {
            LOGGER.log(Level.SEVERE, "Player tried use a card out of his turn.", new Exception());
            return;
        }
        if (event.getEffect() == null)
            match.notifyObservers(concludeAction(match.getCurrentPlayer()));
    }

    @Override
    public void handleEvent(ReloadWeaponEvent event) throws HandlerNotImplementedException {
        if (event.getWeapon() == null) {
            match.notifyObservers(concludeTurn());
        }
        else {
            Player currentPlayer = match.getCurrentPlayer();
            WeaponCard weaponReloaded = match.getCurrentPlayer().getWeaponByName(event.getWeapon());
            List<Ammo> price = new ArrayList<>(weaponReloaded.getLoader());
            List<PowerUpCard> actualPowerUps = currentPlayer.getRealModelReferences(event.getPowerUpsToPay());
            price.add(weaponReloaded.getCardColor());

            if (currentPlayer.canSpend(price, actualPowerUps)) {
                List<Ammo> ammosSpent = currentPlayer.spendPrice(price, actualPowerUps, match.getPowerUpsDeck());
                match.notifyObservers(new PaymentExecutedEvent(currentPlayer, actualPowerUps, ammosSpent));
                weaponReloaded.setLoaded(true);
                match.notifyObservers(new WeaponReloadedEvent(currentPlayer, weaponReloaded.getWeaponName()));
                match.notifyObservers(new ReloadWeaponsRequestEvent(currentPlayer));
            }
            else
                match.notifyObservers(concludeTurn());
        }
    }

    @Override
    public void handleEvent(EndTurnRequestEvent event) throws HandlerNotImplementedException {
        match.notifyObservers(concludeTurn());
    }




    //SUPPORT METHODS

    public void disconnectionDefault(Player disconnectedPlayer) {
        if (match.getCurrentPlayer() == disconnectedPlayer) {
            if (spawningPlayer == disconnectedPlayer && powerUpsForSpawn != null) {
                match.notifyObservers(match.getGameBoard().spawnPlayer(disconnectedPlayer, powerUpsForSpawn.get(0), true));
                match.notifyObservers(disconnectedPlayer.addPowerUp(powerUpsForSpawn.get(1), match.getPowerUpsDeck()));
                match.getPowerUpsDeck().discardPowerUp(powerUpsForSpawn.get(0));
                spawnCompleted();
                match.notifyObservers(nextTurn());
            }
            else
                match.notifyObservers(nextTurn());
        }
        else if (spawningPlayer == disconnectedPlayer && powerUpForRespawn != null) {
            match.notifyObservers(match.getGameBoard().spawnPlayer(disconnectedPlayer, powerUpForRespawn, false));
            match.getPowerUpsDeck().discardPowerUp(powerUpForRespawn);
            spawnCompleted();
        }

    }

    //doesn't count the double kill bonus point
    private Map<String,Integer> singleDeathPointsAssignment(Player deadPlayer) {
        PlayerBoard deadPlayerBoard = deadPlayer.getPlayerBoard();
        Map<Player,Integer> playerPointsMap = new HashMap<>();
        Map<String,Integer> playerPointsMapStringVersion = new HashMap<>();

        for (Player player: match.getPlayers()) {
            if (player != deadPlayer)
                playerPointsMap.put(player, 0);
        }

        //First Blood
        Player firstBloodShooter = deadPlayerBoard.getFirstBloodShooter(new ArrayList<>(playerPointsMap.keySet()));
        playerPointsMap.put(firstBloodShooter, playerPointsMap.get(firstBloodShooter) + Rules.GAME_FIRST_BLOOD_POINTS);

        //Damages points
        List<Player> playersRanking = new ArrayList<>();
        for (DamageMark damageMark: deadPlayerBoard.damageAmountRanking()) {
            playersRanking.add(match.getPlayerByMark(damageMark));
            playersRanking.remove(null);
        }

        for (int rank = 0; rank < playersRanking.size(); rank++) {
            Player player = playersRanking.get(rank);
            playerPointsMap.put(player, playerPointsMap.get(player) + deadPlayerBoard.getPointsByDamageRank(rank));
        }

        for (Map.Entry<Player, Integer> playerPoints: playerPointsMap.entrySet())
            playerPointsMapStringVersion.put(playerPoints.getKey().getPlayerNickname(), playerPoints.getValue());
        return playerPointsMapStringVersion;
    }

    private Map<String,Integer> pointsAssignment(List<Player> deadPlayers) {
        Map<String, Integer> playersPoints = new HashMap<>();
        for (Player player: match.getPlayers())
            playersPoints.put(player.getPlayerNickname(), 0);

        for (Player player: deadPlayers) {
            for (Map.Entry<String, Integer> playerPointsEntry: singleDeathPointsAssignment(player).entrySet()) {
                String pointsAssignee = playerPointsEntry.getKey();
                playersPoints.put(pointsAssignee, playersPoints.get(pointsAssignee) + playerPointsEntry.getValue());
            }
        }
        if (deadPlayers.size() > 1) {
            String currentPlayer = match.getCurrentPlayer().getPlayerNickname();
            playersPoints.put(currentPlayer, playersPoints.get(currentPlayer) + Rules.GAME_DOUBLE_KILL_POINTS);
        }

        return playersPoints;
    }

    private List<AbstractModelEvent> deadPlayersRemoval() {
        List<AbstractModelEvent> events = new ArrayList<>();
        for (Player player: match.getDeadPlayers()) {
            match.getGameBoard().removePlayer(player);
            events.add(new PlayerDeathEvent(match.getCurrentPlayer(), player));
        }
        return events;
    }

    private List<AbstractModelEvent> nextTurn() {
        List<AbstractModelEvent> events = new ArrayList<>();

        while (!match.getNextPlayer().isConnected()) {
            match.nextCurrentPlayer();
            match.increaseTurn();
            if (match.getTurn() == 0) {
                powerUpsForSpawn = new ArrayList<>(Arrays.asList(match.getPowerUpsDeck().drawCard(), match.getPowerUpsDeck().drawCard()));
                events.add(match.getGameBoard().spawnPlayer(match.getCurrentPlayer(), powerUpsForSpawn.get(0), true));
                match.getPowerUpsDeck().discardPowerUp(powerUpsForSpawn.get(0));
                events.add(match.getCurrentPlayer().addPowerUp(powerUpsForSpawn.get(1), match.getPowerUpsDeck()));
                spawnCompleted();
            }
            else
                events.add(new SkippedTurnEvent(match.getCurrentPlayer()));
        }

        Map<Coordinates, AmmoCardClient> ammoCardsReplaced = match.getGameBoard().ammoCardsSetup(match.getAmmoCardsDeck());

        Map<Coordinates, List<WeaponCardClient>> weaponCardsReplaced = match.getGameBoard().weaponCardsSetup(match.getWeaponsDeck());

        beginTurn();

        events.add(new EndTurnEvent(match.getCurrentPlayer(), ammoCardsReplaced, weaponCardsReplaced));

        match.nextCurrentPlayer();

        match.increaseTurn(); //increases turn if nextPlayer is the first player

        events.add(new BeginTurnEvent(match.getCurrentPlayer()));
        if (match.getTurn() == 0) {
            powerUpsForSpawn = Arrays.asList(match.getPowerUpsDeck().drawCard(), match.getPowerUpsDeck().drawCard());
            spawningPlayer = match.getCurrentPlayer();
            events.add(new SpawnSelectionRequestEvent(match.getCurrentPlayer(), powerUpsForSpawn, true));
        }
        else {
            PlayerBoard playerBoard = match.getCurrentPlayer().getPlayerBoard();
            events.add(new ActionRequestEvent(
                    match.getCurrentPlayer(),
                    Rules.MAX_ACTIONS_AVAILABLE,
                    playerBoard.simpleMovementMaxMoves(),
                    playerBoard.preCollectionMaxMoves(),
                    playerBoard.preShootingMaxMoves()));
        }

        return events;
    }

    private void beginTurn() {
        actionsPerformed = 0;
    }

    private AbstractModelEvent concludeAction(Player currentPlayer) {
        actionsPerformed++;
        if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE)
            return new ActionRequestEvent(
                    currentPlayer,
                    Rules.MAX_ACTIONS_AVAILABLE - actionsPerformed,
                    currentPlayer.getPlayerBoard().simpleMovementMaxMoves(),
                    currentPlayer.getPlayerBoard().preCollectionMaxMoves(),
                    currentPlayer.getPlayerBoard().preShootingMaxMoves()
            );
        else
            return new ReloadWeaponsRequestEvent(currentPlayer);
    }

    private List<AbstractModelEvent> concludeTurn() {
        if (match.getDeadPlayers().isEmpty())
            return nextTurn();
        else {
            List<AbstractModelEvent> events = new ArrayList<>();

            //Points
            Map<String, Integer> playerPointsMap = pointsAssignment(match.getDeadPlayers());
            match.updatePoints(playerPointsMap);
            events.add(new PointsAssignmentEvent(playerPointsMap));

            //Dead players removal from map
            events.addAll(deadPlayersRemoval());

            //Skulls assignment and dead path
            events.addAll(match.skullsAssignment());

            //Trigger respawn
            Player firstDeadPlayer = match.getDeadPlayers().get(0);
            if (firstDeadPlayer.isConnected()) {
                powerUpForRespawn  = match.getPowerUpsDeck().drawCard();
                spawningPlayer = firstDeadPlayer;
                events.add(new SpawnSelectionRequestEvent(firstDeadPlayer, new ArrayList<>(Arrays.asList(powerUpForRespawn)), false));
            }
            else {
                Player nextDeadPlayer = firstDeadPlayer;
                events.addAll(deadPlayersRespawnLoop(nextDeadPlayer));
            }

            return events;
        }
    }

    private List<AbstractModelEvent> deadPlayersRespawnLoop(Player nextDeadPlayer) {
        List<AbstractModelEvent> events = new ArrayList<>();
        while (!match.getDeadPlayers().isEmpty() && !nextDeadPlayer.isConnected()) {
            events.addAll(disconnectedPlayerSpawnHandling(nextDeadPlayer, false));
            if (!match.getDeadPlayers().isEmpty())
                nextDeadPlayer = match.getDeadPlayers().get(0);
        }
        if (match.getDeadPlayers().isEmpty())
            events.addAll(nextTurn());
        else {
            powerUpForRespawn  = match.getPowerUpsDeck().drawCard();
            spawningPlayer = nextDeadPlayer;
            events.add(new SpawnSelectionRequestEvent(nextDeadPlayer, Arrays.asList(powerUpForRespawn), false));
        }
        return events;
    }

    private void spawnCompleted() {
        spawningPlayer = null;
        powerUpsForSpawn = null;
        powerUpForRespawn = null;
    }


    private List<AbstractModelEvent> disconnectedPlayerSpawnHandling(Player deadPlayer, boolean firstSpawn) {
        List<AbstractModelEvent> events = new ArrayList<>();
        powerUpForRespawn = match.getPowerUpsDeck().drawCard();
        match.getPowerUpsDeck().discardPowerUp(powerUpForRespawn);
        events.add(match.getGameBoard().spawnPlayer(deadPlayer, powerUpForRespawn, false));
        events.add(new CleanPlayerBoardEvent(deadPlayer));
        spawnCompleted();
        deadPlayer.resuscitate();
        return events;
    }

    public void setPowerUpsForSpawn(List<PowerUpCard> powerUpsForSpawn) {
        this.powerUpsForSpawn = powerUpsForSpawn;
    }

    public void setSpawningPlayer(Player spawningPlayer) {
        this.spawningPlayer = spawningPlayer;
    }
}
