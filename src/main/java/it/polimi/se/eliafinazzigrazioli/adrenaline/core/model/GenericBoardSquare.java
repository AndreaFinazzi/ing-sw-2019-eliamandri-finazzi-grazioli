package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.io.Serializable;

public class GenericBoardSquare extends BoardSquare implements Serializable {

    private AmmoCard collectable;

    public GenericBoardSquare() {
    }

    public GenericBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, coordinates, north, south, east, west);
        collectable = null;
    }


    @Override
    public AbstractModelEvent collect(Player player, PowerUpsDeck deck) {
        PowerUpCard collectedCard = deck.drawCard();
        AmmoCard collected = collectable;
        collectable = null;
        player.addPowerUp(collectedCard);
        player.addAmmos(collected.getAmmos());
        return new AmmoCardCollectedEvent(player.getPlayerNickname(), collectedCard, collected.getAmmos());
    }

    @Override
    public boolean collectActionIsValid(Player player) {
        if (collectable == null)
            return false;
        return true;
    }

    //TODO define type exception
    public AmmoCard removeCollectables() throws Exception {
        if (collectable == null)
            throw new Exception();
        AmmoCard tempCollectables = collectable;
        collectable = null;
        return tempCollectables;
    }

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
