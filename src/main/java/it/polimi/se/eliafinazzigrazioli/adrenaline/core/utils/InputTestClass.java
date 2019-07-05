package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InputTestClass implements RemoteView, Runnable {

    private LocalModel localModel;

    private Client client;

    private List<Observer> observers = new ArrayList<>();

    public InputTestClass(Client client) {
        this.client = client;
        localModel = new LocalModel();
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void run() {

    }

    @Override
    public void showLogin(List<Avatar> availableAvatars) {

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String username = generatedString;

        notifyLoginRequestEvent(username, availableAvatars.get(new Random().nextInt(availableAvatars.size())));
    }

    @Override
    public void showMapVote(List<MapType> availableMaps) {
        notifyMapVoteEvent(availableMaps.get(new Random().nextInt(availableMaps.size())));
    }

    @Override
    public void showMessage(Object message) {}
    @Override
    public void showMap() {}
    @Override
    public void showLoginSuccessful() {}
    @Override
    public void showBeginMatch() {}
    @Override
    public void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {}
    @Override
    public void showWeaponCollectionUpdate(String player, WeaponCardClient collectedCard, WeaponCardClient droppedCard, Room roomColor) {}
    @Override
    public void showSuddenDeadUpdate(String deadPlayer) {}
    @Override
    public void showPlayerMovedByWeaponUpdate(String attackingPlayer, String playerMoved, String weaponUsed, Coordinates finalPosition) {}
    @Override
    public void showShotPlayerUpdate(String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {}
    @Override
    public void showPlayerMovementUpdate(String player, List<Coordinates> path) {}
    @Override
    public void showSpawnUpdate(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {}
    @Override
    public void showPowerUpCollectionUpdate(String player, PowerUpCardClient cardCollected, boolean isOpponent) {}
    @Override
    public void showBeginTurnUpdate(String player) {}
    @Override
    public void showEndTurnUpdate(String player) {}
    @Override
    public void showAmmoCollectedUpdate(String player, Ammo ammo, boolean actuallyCollected, boolean lastOfCard) {}
    @Override
    public void showAmmoCardCollectedUpdate(String player, AmmoCardClient ammoCard, Coordinates coordinates) {}
    @Override
    public void showAmmoCardResettingUpdate(Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap) {}
    @Override
    public void showWeaponCardResettingUpdate(Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap) {}
    @Override
    public void showWeaponReloadedUpdate(String player, WeaponCardClient weaponCard) {}


    @Override
    public MoveDirection selectDirection(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves) {
        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }

    @Override
    public PlayerAction selectAction() {
        return PlayerAction.values()[new Random().nextInt(4)];
    }

    @Override
    public PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards) {
        return cards.get(new Random().nextInt(cards.size()));
    }

    @Override
    public PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards) {
        return cards.get(new Random().nextInt(cards.size()));
    }

    @Override
    public Coordinates selectCoordinates(List<Coordinates> coordinates) {
        if (coordinates.size() > 0)
            return coordinates.get(new Random().nextInt(coordinates.size()));
        else
            return null;
    }

    @Override
    public String selectPlayer(List<String> players) {
        return players.get(new Random().nextInt(players.size()));
    }

    @Override
    public Room selectRoom(List<Room> rooms) {
        if (rooms.size() > 0)
            return rooms.get(new Random().nextInt(rooms.size()));
        else
            return null;
    }

    @Override
    public WeaponCardClient selectWeaponCardFromSpawnSquare(Coordinates coordinates, List<WeaponCardClient> selectableWeapons) {
        if (selectableWeapons.size() > 0)
            return selectableWeapons.get(new Random().nextInt(selectableWeapons.size()));
        else
            return null;
    }

    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        return reloadableWeapons.get(new Random().nextInt(reloadableWeapons.size()));
    }

    @Override
    public WeaponCardClient selectWeaponCardFromHand(List<WeaponCardClient> selectableWeapons) {
        if (selectableWeapons.size() > 0)
            return selectableWeapons.get(new Random().nextInt(selectableWeapons.size()));
        else
            return null;
    }

    @Override
    public WeaponEffectClient selectWeaponEffect(WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) {
        return callableEffects.get(new Random().nextInt(callableEffects.size()));
    }

    @Override
    public boolean selectYesOrNot() {
        return false;
    }

    @Override
    public void setDisconnected() {

    }


    @Override
    public void setLocalModel(LocalModel clientModel) {

    }

    @Override
    public void showPlayerDisconnection(String player) {

    }

    @Override
    public void showPlayerReconnection(String player) {

    }
}
