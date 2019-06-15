package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import java.io.File;
import java.util.ArrayList;


public class WeaponsDeck extends Deck<String> {


    /**
     * WeaponDeck's constructor automatically reads all weapon files' names and saves them in an ArrayList. Cards will be drawn
     * fallowing a deck logic (implemented in the super class) and the strings will be fed to the static method jsonParser()
     * in WeaponCard class to build the actual card.
     */
    public WeaponsDeck() {
        cards = new ArrayList<>();
        File weaponsFolder = new File("src/main/resources/jsonFiles/weaponCardJsons");
        File[] listOfCardFiles = weaponsFolder.listFiles();
        for (int i = 0; i < listOfCardFiles.length; i++)
            cards.add(listOfCardFiles[i].getName());
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
