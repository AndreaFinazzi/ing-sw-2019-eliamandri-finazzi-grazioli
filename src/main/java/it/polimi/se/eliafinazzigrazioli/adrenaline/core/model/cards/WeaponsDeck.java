package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WeaponsDeck extends Deck<String> {

    static final Logger LOGGER = Logger.getLogger(WeaponsDeck.class.getName());

    /**
     * WeaponDeck's constructor automatically reads all weapon files' names and saves them in an ArrayList. Cards will be drawn
     * fallowing a deck logic (implemented in the super class) and the strings will be fed to the static method jsonParser()
     * in WeaponCard class to build the actual card.
     */
    public WeaponsDeck() {
        cards = new ArrayList<>();
        BufferedReader weaponsListReader = null;
        try {
            weaponsListReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/jsonFiles/weaponCardJsons/weaponsList.txt")));
            String weaponIndex;
            while ((weaponIndex = weaponsListReader.readLine()) != null) {
                cards.add(weaponIndex + ".json");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (weaponsListReader != null) {
                try {
                    weaponsListReader.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
