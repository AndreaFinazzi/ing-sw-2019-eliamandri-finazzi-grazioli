package it.polimi.se.eliafinazzigrazioli.adrenaline.model;


import java.util.List;

public interface Selectable {

    List<Selectable> getVisible(SelectableType selType, boolean notVisible, GameBoard gameBoard);

    List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance, GameBoard gameBoard);

    List<Selectable> getByRoom(SelectableType selType, GameBoard gameBoard, List<Player> players);

    List<Selectable> getOnCardinal(SelectableType selType, GameBoard gameBoard);

}
