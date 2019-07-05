package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Spawn board square client.
 */
public class SpawnBoardSquareClient extends BoardSquareClient {
    private static final long serialVersionUID = 9007L;


    private List<WeaponCardClient> weaponCards;
    private boolean ammoCard;

    /**
     * Instantiates a new Spawn board square client.
     *
     * @param room the room
     * @param coordinates the coordinates
     * @param north the north
     * @param south the south
     * @param east the east
     * @param west the west
     */
    public SpawnBoardSquareClient(Room room, Coordinates coordinates, InterSquareLink north,
                                  InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
        weaponCards = new ArrayList<>();
    }

    /**
     * Add weapon.
     *
     * @param weaponCard the weapon card
     */
    public void addWeapon(WeaponCardClient weaponCard) {
        List<Integer> weaponPositions = new ArrayList<>();
        int index = 0;
        for (WeaponCardClient weaponCardClient: weaponCards)
            weaponPositions.add(weaponCardClient.getSlotPosition());
        while (weaponPositions.contains(index))
            index++;
        weaponCard.setSlotPosition(room, index);
        if (!weaponCards.contains(weaponCard)) {
            weaponCard.setLoaded(true);
            weaponCards.add(weaponCard);
        }
    }

    /**
     * Remove weapon card client.
     *
     * @param weaponCard the weapon card
     * @return the weapon card client
     */
    public WeaponCardClient remove(String weaponCard) {
        WeaponCardClient toRemove = null;
        for (WeaponCardClient weaponCardClient: weaponCards)
            if (weaponCard.equals(weaponCardClient.getWeaponName()))
                toRemove = weaponCardClient;
        toRemove.setSlotPosition(null, -1);
        weaponCards.remove(toRemove);
        return toRemove;
    }

    @Override
    public boolean isSpawnBoard() {
        return true;
    }

    @Override
    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
    }

    /**
     * Gets weapon cards by slot position.
     *
     * @param slotPosition the slot position
     * @return the weapon cards by slot position
     */
    public WeaponCardClient getWeaponCardsBySlotPosition(int slotPosition) {
        for (WeaponCardClient weaponCard : weaponCards) {
            if (weaponCard.getSlotPosition() == slotPosition) return weaponCard;
        }

        return null;
    }

    @Override
    public boolean addWeaponCard(WeaponCardClient weapon) {
        if(!weaponCards.contains(weapon)){
            weaponCards.add(weapon);
            return true;
        } else
            return false;
    }

    /**
     * Is ammo card boolean.
     *
     * @return the boolean
     */
    public boolean isAmmoCard() {
        return ammoCard;
    }

    /**
     * Sets ammo card.
     *
     * @param ammoCard the ammo card
     */
    public void setAmmoCard(boolean ammoCard) {
        this.ammoCard = ammoCard;
    }

    @Override
    public String toString() {
        return "Spawn board square at " + coordinates + "\n" + "Room: " + room;
    }
}
