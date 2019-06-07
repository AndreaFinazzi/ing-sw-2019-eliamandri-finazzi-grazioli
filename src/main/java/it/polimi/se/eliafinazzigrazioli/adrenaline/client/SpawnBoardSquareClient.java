package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

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
        if (weaponCards.size() < Rules.PLAYER_CARDS_MAX_WEAPONS && weaponCards.contains(weaponCard)) {
            weaponCards.add(weaponCard);
        }
    }

    public WeaponCardClient remove(WeaponCardClient weaponCard) {
        int index = weaponCards.indexOf(weaponCard);
        WeaponCardClient tempWeaponcard = weaponCards.get(index);
        weaponCards.remove(weaponCard);
        return tempWeaponcard;
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
        if(weaponCards.size() < Rules.PLAYER_CARDS_MAX_WEAPONS && !weaponCards.contains(weapon)){
            weaponCards.add(weapon);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean addAmmoCard(AmmoCardClient ammo) {
        return false;
    }

    public boolean isAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(boolean ammoCard) {
        this.ammoCard = ammoCard;
    }
}
