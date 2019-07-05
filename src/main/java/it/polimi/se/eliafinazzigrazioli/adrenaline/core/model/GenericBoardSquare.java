package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.io.Serializable;

/**
 * The type Generic board square.
 */
public class GenericBoardSquare extends BoardSquare implements Serializable {

    private AmmoCard collectable;

    /**
     * Instantiates a new Generic board square.
     */
    public GenericBoardSquare() {
    }

    /**
     * Instantiates a new Generic board square.
     *
     * @param room the room
     * @param coordinates the coordinates
     * @param north the north
     * @param south the south
     * @param east the east
     * @param west the west
     */
    public GenericBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
        collectable = null;
    }


    /**
     * Gets collectable.
     *
     * @return the collectable
     */
    public AmmoCard getCollectable() {
        return collectable;
    }

    @Override
    public boolean isSpawnPoint() {
        return false;
    }

    @Override
    public boolean ammoCollectionIsValid() {
        if (collectable != null)
            return true;
        else
            return false;
    }

    @Override
    public AmmoCardCollectedEvent collect(Player player, PowerUpsDeck deck) {
        AmmoCard collected = collectable;
        collectable = null;
        PowerUpCard collectedPowerUp = null;
        if (collected == null)
            return null;
        player.addAmmos(collected.getAmmos());
        if (collected.containsPowerUpCard()) {
            collectedPowerUp = deck.drawCard();
            player.addPowerUp(collectedPowerUp, deck);
        }
        return null;
    }

    /**
     * Gather collectables ammo card.
     *
     * @return the ammo card
     */
//TODO define type exception
    public AmmoCard gatherCollectables() {
        AmmoCard tempCollectables = collectable;
        collectable = null;
        return tempCollectables;
    }

    /**
     * Drop collectables.
     *
     * @param toDrop the to drop
     */
    public void dropCollectables(AmmoCard toDrop) {
        collectable = toDrop;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenericBoardSquare) {
            GenericBoardSquare genericBS = (GenericBoardSquare) obj;
            return genericBS.getRoom().equals(this.getRoom()) &&
                    genericBS.getCoordinates().equals(this.getCoordinates()) &&
                    genericBS.getNorth().equals(this.getNorth()) &&
                    genericBS.getSouth().equals(this.getSouth()) &&
                    genericBS.getEast().equals(this.getEast()) &&
                    genericBS.getWest().equals(this.getWest());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "GenericBoardSquare { " +
                super.toString() + "\n";
    }
}
