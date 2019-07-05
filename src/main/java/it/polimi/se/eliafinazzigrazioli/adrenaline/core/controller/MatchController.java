package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LoginResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.MatchStartedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.BeginMatchEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerDisconnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerReconnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MapVoteEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerReconnectionException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;

import java.util.*;
import java.util.logging.Logger;

public class MatchController implements ViewEventsListenerInterface, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());
    private static final String NOT_LOGGED_CLIENT_NICKNAME = "$_$";

    private MatchBuilder matchBuilder;

    private boolean started;

    private Match match;

    private CardController cardController;
    private TurnController turnController;
    private EventController eventController;
    private Map<Integer, String> clientIDToPlayerMap = new HashMap<>();

    private Map<MapType, Integer> mapVotesCounter;

    private int mapVotes = 0;

    public MatchController(MatchBuilder matchBuilder) {
        this.matchBuilder = matchBuilder;

        match = new Match();
        match.setPhase(MatchPhase.RECRUITING);

        eventController = new EventController(this);
        cardController = new CardController(eventController, match);
        turnController = new TurnController(eventController, match);

        eventController.addViewEventsListener(LoginRequestEvent.class, this);
        eventController.addViewEventsListener(MapVoteEvent.class, this);
        eventController.addViewEventsListener(ClientDisconnectionEvent.class, this);

        match.addObserver(eventController);
    }

    public void setMatchID(int matchID) {
        match.setMatchID(matchID);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void initMatch(MapType chosenMap) {
        //TODO: to implement
        match.setPhase(MatchPhase.PLAYING);
        match.beginMatch(chosenMap); //Initializes map

        Map<Coordinates, AmmoCardClient> ammoCardsSetup = match.getGameBoard().ammoCardsSetup(match.getAmmoCardsDeck()); //places ammoCards and keeps track in a map
        Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup = match.getGameBoard().weaponCardsSetup(match.getWeaponsDeck());

        match.notifyObservers(new BeginMatchEvent(chosenMap, ammoCardsSetup, weaponCardsSetup, match.getAvatarMap())); //event contains info about match initialization
        match.notifyObservers(new BeginTurnEvent(match.getCurrentPlayer())); //notifies to all players who is beginning the turn
        List<PowerUpCard> powerUpCardsForSpawn = Arrays.asList(match.getPowerUpsDeck().drawCard(), match.getPowerUpsDeck().drawCard());
        turnController.setPowerUpsForSpawn(powerUpCardsForSpawn);
        turnController.setSpawningPlayer(match.getCurrentPlayer());
        match.notifyObservers(new SpawnSelectionRequestEvent(match.getCurrentPlayer(), powerUpCardsForSpawn, true));
    }

    public EventController getEventController() {
        return eventController;
    }

    public void addPlayer(String player, Avatar avatar) throws MaxPlayerException, PlayerAlreadyPresentException, AvatarNotAvailableException {
        match.addPlayer(player, avatar);
    }

    public void signNewClient(AbstractClientHandler clientHandler) {
        eventController.addVirtualView(clientHandler);
        clientIDToPlayerMap.put(clientHandler.getClientID(), NOT_LOGGED_CLIENT_NICKNAME);
        eventController.update(new ConnectionResponseEvent(clientHandler.getClientID(), "Username and Avatar required.", match.getAvailableAvatars()));
    }

    public void signClient(AbstractClientHandler clientHandler) {
        eventController.addVirtualView(clientHandler);
        clientIDToPlayerMap.put(clientHandler.getClientID(), NOT_LOGGED_CLIENT_NICKNAME);
    }

    public void removePlayer(String nickname) {
        match.removePlayer(nickname);
    }

    public boolean isReady() {
        return match.getPlayers().size() >= Rules.GAME_MIN_PLAYERS;
    }

    public boolean isFull() {
        return match.getPlayers().size() == Rules.GAME_MAX_PLAYERS;
    }

    //Getter
    public Player.AbstractPlayerList getPlayers() {
        return match.getPlayers();
    }

    public Match getMatch() {
        return match;
    }

    public AbstractClientHandler popClient(int clientID) {
        clientIDToPlayerMap.remove(clientID);
        return eventController.popVirtualView(clientID);
    }

    public List<AbstractClientHandler> popNotLoggedClients() {
        ArrayList<AbstractClientHandler> notLoggedClients = new ArrayList<>();
        Iterator<Integer> clientIDs = clientIDToPlayerMap.keySet().iterator();

        while (clientIDs.hasNext()) {
            int clientID = clientIDs.next();

            if (clientIDToPlayerMap.get(clientID).equals(NOT_LOGGED_CLIENT_NICKNAME)) {
                notLoggedClients.add(eventController.popVirtualView(clientID));
                clientIDs.remove();
            }
        }

        return notLoggedClients;
    }

    private synchronized void voteMap(MapType mapType) {
        mapVotes++;
        int votesForMap = mapVotesCounter.getOrDefault(mapType, 0);
        mapVotesCounter.put(mapType, ++votesForMap);
    }

    private MapType getWinningMap() {
        ArrayList<MapType> winningMaps = new ArrayList<>();
        int higherVotesCount = 0;

        for (MapType mapType : mapVotesCounter.keySet()) {
            if (mapVotesCounter.get(mapType) > higherVotesCount) {
                higherVotesCount = mapVotesCounter.get(mapType);
                winningMaps.clear();
                winningMaps.add(mapType);
            } else if (mapVotesCounter.get(mapType) == higherVotesCount) {
                winningMaps.add(mapType);
            }
        }

        return winningMaps.get(new Random().nextInt(winningMaps.size()));
    }

    @Override
    public void handleEvent(LoginRequestEvent event) throws HandlerNotImplementedException {
        LoginResponseEvent responseEvent = new LoginResponseEvent(event.getClientID());
        try {
            if (matchBuilder.validateLoginRequestEvent(event, this)) {
                Player player = match.addPlayer(event.getClientID(), event.getPlayer(), event.getChosenAvatar());

                responseEvent.setSuccess(true);
                responseEvent.setPlayer(player.getPlayerNickname());
                responseEvent.setAssignedAvatar(player.getAvatar());
                responseEvent.setMatchID(match.getMatchID());
                responseEvent.setMessage("Welcome to Adrenaline, " + event.getPlayer() + "\nYou're logged to match " + match.getMatchID());

                clientIDToPlayerMap.put(player.getClientID(), player.getPlayerNickname());
                matchBuilder.playerLogged(player.getPlayerNickname(), this);
            }

        } catch (MaxPlayerException e) {
            responseEvent.setSuccess(false);
            responseEvent.setAvailableAvatars(match.getAvailableAvatars());
            responseEvent.setMessage("MaxPlayerException thrown");

        } catch (PlayerAlreadyPresentException e) {
            responseEvent.setSuccess(false);
            responseEvent.setAvailableAvatars(match.getAvailableAvatars());
            responseEvent.setMessage("Username already in game, try with a different nick!");

        } catch (PlayerReconnectionException e) {
            Player player = match.getPlayers().get(event.getPlayer());
            player.setClientID(event.getClientID());
            clientIDToPlayerMap.put(player.getClientID(), player.getPlayerNickname());

            responseEvent.setSuccess(true);
            responseEvent.setReconnection(true);
            responseEvent.setClientModel(match.generateClientModel(player));
            responseEvent.setPlayer(player.getPlayerNickname());
            responseEvent.setAssignedAvatar(player.getAvatar());

            matchBuilder.reconnectClient(player.getPlayerNickname(), this);
        }
        eventController.update(responseEvent);
        if (responseEvent.isReconnection())
            eventController.update(new PlayerReconnectedEvent(responseEvent.getPlayer()));
    }

    @Override
    public void handleEvent(MapVoteEvent event) throws HandlerNotImplementedException {
        if (event.getVotedMap() != null)
            voteMap(event.getVotedMap());
        else
            voteMap(MapType.values()[new Random().nextInt(MapType.values().length - 1)]);

        if (mapVotes == getPlayers().size()) {
            initMatch(getWinningMap());
        }
    }


    @Override
    public void handleEvent(ClientDisconnectionEvent event) throws HandlerNotImplementedException {
        Player disconnectingPlayer = match.getPlayers().get(clientIDToPlayerMap.get(event.getClientID()));
        clientIDToPlayerMap.remove(event.getClientID());
        eventController.popVirtualView(event.getClientID());
        if (disconnectingPlayer != null) {
            disconnectingPlayer.setConnected(false);
            matchBuilder.disconnectClient(disconnectingPlayer.getPlayerNickname(), this);
            eventController.update(new PlayerDisconnectedEvent(disconnectingPlayer.getPlayerNickname()));
            turnController.disconnectionDefault(disconnectingPlayer);
        }
    }

    @Override
    public void run() {
        HashMap<String, Avatar> playerToAvatarMap = new HashMap<>();
        for (Player player : match.getPlayers()) {
            playerToAvatarMap.put(player.getPlayerNickname(), player.getAvatar());
        }

        mapVotesCounter = new EnumMap<>(MapType.class);

        match.notifyObservers(new MatchStartedEvent(playerToAvatarMap, new ArrayList<>(Arrays.asList(MapType.values()))));
    }

    public void closeMatch() {
        matchBuilder.matchEnded(this);
    }
}