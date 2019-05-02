package it.polimi.se.eliafinazzigrazioli.adrenaline.model;


import java.util.List;

public interface Selectable {

    List<Selectable> getVisible(SelectableType selType, boolean notVisible);

    List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance);

    List<Selectable> getByRoom(SelectableType selType);

    List<Selectable> getOnCardinal(SelectableType selType);

}
