package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class SpawnBoardSquareClient extends BoardSquareClient {

    private List<String> weaponCards;
    private boolean ammoCard;

    public SpawnBoardSquareClient(Room room, Coordinates coordinates, InterSquareLink north,
                                  InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
        weaponCards = new ArrayList<>();
    }

    public List<String> getWeaponCards() {
        return weaponCards;
    }

    public void addWeapon(String weaponCard) {
        if(weaponCards.size() < Rules.PLAYER_CARDS_MAX_WEAPONS && weaponCards.contains(weaponCard)){
            weaponCards.add(weaponCard);
        }
    }

    public String remove(String weaponCard) {
        int index = weaponCards.indexOf(weaponCard);
        String tempWeaponcard = weaponCards.get(index);
        weaponCards.remove(weaponCard);
        return tempWeaponcard;
    }

    public boolean isAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(boolean ammoCard) {
        this.ammoCard = ammoCard;
    }
}
