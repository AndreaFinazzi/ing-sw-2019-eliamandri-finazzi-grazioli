package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

import java.util.ArrayList;

public class SpawnBoardSquare extends BoardSquare {

    private ArrayList<WeaponCard> weaponSlots;

    public SpawnBoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, north, south, east, west);
        this.weaponSlots = new ArrayList<WeaponCard>();
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
    public BoardSquare getSquare() {
        return this;
    }
}
