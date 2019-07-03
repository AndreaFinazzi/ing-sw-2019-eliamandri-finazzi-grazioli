package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpsDeck extends Deck<PowerUpCard> {

    private List<PowerUpCard> discardedPowerUps = new ArrayList<>();

    public PowerUpsDeck(List<PowerUpCard> cards) {
        this.cards = cards;
        discardedPowerUps = new ArrayList<>();
    }

    public PowerUpsDeck() {

        cards = new ArrayList<>();

        List<String> cardCodes = new ArrayList<>();
        File weaponsFolder = new File("src/main/resources/jsonFiles/powerUpsDeck");
        File[] listOfCardFiles = weaponsFolder.listFiles();
        for (int i = 0; i < listOfCardFiles.length; i++)
            cardCodes.add(listOfCardFiles[i].getName());


        for (String code: cardCodes)
            for (int i = 0; i < Rules.GAME_AMMO_CARDS_DUPLICATES; i++) {
                String filePath = "src/main/resources/jsonFiles/powerUpsDeck/" + code;
                String jsonString = null;
                try {
                    jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                Type powerUpType = new TypeToken<PowerUpCard>(){}.getType();
                PowerUpCard powerUpCard = gson.fromJson(jsonString, powerUpType);
                cards.add(powerUpCard);
            }
        discardedPowerUps = new ArrayList<>();
    }

    public PowerUpCard drawCard() {
        PowerUpCard powerUpCard = cards.remove(new Random().nextInt(cards.size()));
        if (cards.size() == 0)
            resetDeck();
        return powerUpCard;
    }


    private void resetDeck() {
        cards = discardedPowerUps;
        discardedPowerUps = new ArrayList<>();
    }

    public void discardPowerUp(PowerUpCard powerUpCard){
        if (powerUpCard != null)
            discardedPowerUps.add(powerUpCard);
    }

}
