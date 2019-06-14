package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class SpawnBoardSquare extends BoardSquare {

    private ArrayList<WeaponCard> weaponSlots;

    public SpawnBoardSquare() {
    }

    public SpawnBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
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
    public WeaponCard collectWeapon(WeaponCard weapon) throws Exception {
        int index = weaponSlots.indexOf(weapon);
        if (index == -1)
            throw new Exception();
        WeaponCard tmpWeapon = weaponSlots.get(index);
        weaponSlots.remove(index);
        return tmpWeapon;
    }

    //TODO define type excpetion
    public void addWeapon(WeaponCard weapon) throws Exception {
        if (weaponSlots.size() == 3)
            throw new Exception();
        if (weaponSlots.contains(weapon))
            throw new Exception();
        weaponSlots.add(weapon);
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


