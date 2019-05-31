package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.WeaponCollectSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;

public class SpawnBoardSquare extends BoardSquare {

    private ArrayList<WeaponCard> weaponSlots;

    public SpawnBoardSquare() {
    }

    public SpawnBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
    }


    /**
     * For the spawn square the item collected depends on the choice of the user so the execution of the method consists
     * of the generation of an event containing the names of the selectable cards.
     * @param player
     * @param deck
     * @return
     */
    @Override
    public AbstractModelEvent collect(Player player, PowerUpsDeck deck) {
        return new WeaponCollectSelectionRequestEvent(player.getPlayerNickname());
    }

    /**
     * Returns true the player owns enough ammos to buy at least one of the weaponCards in the slots.
     * @param player
     * @return
     */
    @Override
    public boolean collectActionIsValid(Player player) {
        for (WeaponCard weaponCard: weaponSlots){
            if (player.canSpend(weaponCard.getLoader())) {
                return true;
            }
        }
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


