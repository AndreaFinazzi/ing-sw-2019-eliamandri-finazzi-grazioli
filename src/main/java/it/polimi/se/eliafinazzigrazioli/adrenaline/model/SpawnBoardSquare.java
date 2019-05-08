package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.ArrayList;

public class SpawnBoardSquare extends BoardSquare {

    private ArrayList<WeaponCard> weaponSlots;

    public SpawnBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
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
            if (spawnBS.getRoom ().equals (this.getRoom ()) &&
                    spawnBS.getCoordinates ().equals (this.getCoordinates ()) &&
                    spawnBS.getNorth ().equals (this.getNorth ()) &&
                    spawnBS.getSouth ().equals (this.getSouth ()) &&
                    spawnBS.getEast ().equals (this.getEast ()) &&
                    spawnBS.getWest ().equals (this.getWest ()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SpawnBoardSquare {" +
                super.toString () +"\n";

    }
}


