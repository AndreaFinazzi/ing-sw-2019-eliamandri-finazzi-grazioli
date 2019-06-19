package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;

public class SpawnBoardSquare extends BoardSquare {

    private ArrayList<WeaponCard> weaponSlots;

    public SpawnBoardSquare() {
    }

    public SpawnBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
        weaponSlots = new ArrayList<>();
    }


    @Override
    public boolean isSpawnPoint() {
        return true;
    }

    /**
     * For a spawn square the collect action is not planned so the methods can only return a null event. The null return value
     * is used by the TurnController to know whether the collect action has been performed or not.
     * @param player
     * @param deck
     * @return
     */
    @Override
    public AmmoCardCollectedEvent collect(Player player, PowerUpsDeck deck) {
        return null;
    }

    @Override
    public boolean ammoCollectionIsValid() {
        return false;
    }

    public ArrayList<WeaponCard> getWeapons() {
        return weaponSlots;
    }


    //TODO define type excpetion
    public WeaponCard collectWeapon(String weapon) {
        WeaponCard tmpWeapon = null;
        for (WeaponCard weaponCard: weaponSlots)
            if (weapon.equals(weaponCard.getWeaponName()))
                tmpWeapon = weaponCard;

        if (tmpWeapon != null)
            weaponSlots.remove(tmpWeapon);

        return tmpWeapon;
    }

    //TODO define type excpetion
    public void addWeapon(WeaponCard weapon) throws Exception {
        if (weaponSlots.size() == Rules.GAME_BOARD_MAX_WEAPONS_ON_SPAWN)
            throw new Exception();
        if (weaponSlots.contains(weapon))
            throw new Exception();
        weaponSlots.add(weapon);
    }

    public boolean weaponsSlotIsFull() {
        return weaponSlots.size() >= Rules.GAME_BOARD_MAX_WEAPONS_ON_SPAWN;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpawnBoardSquare) {
            SpawnBoardSquare spawnBS = (SpawnBoardSquare) obj;
            return spawnBS.getRoom().equals(this.getRoom()) &&
                    spawnBS.getCoordinates().equals(this.getCoordinates()) &&
                    spawnBS.getNorth().equals(this.getNorth()) &&
                    spawnBS.getSouth().equals(this.getSouth()) &&
                    spawnBS.getEast().equals(this.getEast()) &&
                    spawnBS.getWest().equals(this.getWest());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SpawnBoardSquare {" +
                super.toString() + "\n";

    }
}


