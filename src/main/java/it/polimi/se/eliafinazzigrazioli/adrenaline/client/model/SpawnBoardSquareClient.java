package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class SpawnBoardSquareClient extends BoardSquareClient {


    private List<WeaponCardClient> weaponCards;
    private boolean ammoCard;

    public SpawnBoardSquareClient(Room room, Coordinates coordinates, InterSquareLink north,
                                  InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
        weaponCards = new ArrayList<>();
    }

    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
    }

    public void addWeapon(WeaponCardClient weaponCard) {
        List<Integer> weaponPositions = new ArrayList<>();
        int index = 0;
        for (WeaponCardClient weaponCardClient: weaponCards)
            weaponPositions.add(weaponCardClient.getSlotPosition());
        while (weaponPositions.contains(index))
            index++;
        weaponCard.setSlotPosition(index);
        if (!weaponCards.contains(weaponCard)) {
            weaponCard.setLoaded(true);
            weaponCards.add(weaponCard);
        }
    }

    public WeaponCardClient remove(String weaponCard) {
        WeaponCardClient toRemove = null;
        for (WeaponCardClient weaponCardClient: weaponCards)
            if (weaponCard.equals(weaponCardClient.getWeaponName()))
                toRemove = weaponCardClient;
        toRemove.setSlotPosition(-1);
        weaponCards.remove(toRemove);
        return toRemove;
    }

    @Override
    public boolean isSpawnBoard() {
        return true;
    }

    @Override
    public List<WeaponCardClient> getWeapons() {
        return weaponCards;
    }

    @Override
    public boolean addWeaponCard(WeaponCardClient weapon) {
        if(!weaponCards.contains(weapon)){
            weaponCards.add(weapon);
            return true;
        }
        else
            return false;
    }

    public boolean isAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(boolean ammoCard) {
        this.ammoCard = ammoCard;
    }

    @Override
    public String toString() {
        return "Spawn board square at " + coordinates + "\n" + "Room: " + room;
    }
}
