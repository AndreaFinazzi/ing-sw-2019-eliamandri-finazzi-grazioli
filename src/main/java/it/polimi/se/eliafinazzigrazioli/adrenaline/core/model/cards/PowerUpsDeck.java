package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PowerUpsDeck {

    static final Logger LOGGER = Logger.getLogger(PowerUpsDeck.class.getName());

    List<PowerUpCard> cards;

    private List<PowerUpCard> discardedPowerUps = new ArrayList<>();

    public PowerUpsDeck(List<PowerUpCard> cards) {
        this.cards = cards;
        discardedPowerUps = new ArrayList<>();
    }

    public PowerUpsDeck() throws URISyntaxException {

        cards = new ArrayList<>();

        List<String> cardCodes = new ArrayList<>();
        BufferedReader powerUpsListReader = null;
        try {
            powerUpsListReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/jsonFiles/powerUpsDeck/powerUpsList.txt")));
            String powerUpIndex;
            while ((powerUpIndex = powerUpsListReader.readLine()) != null) {
                cardCodes.add(powerUpIndex + ".json");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (powerUpsListReader != null) {
                try {
                    powerUpsListReader.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        for (String code : cardCodes)
            for (int i = 0; i < Rules.GAME_AMMO_CARDS_DUPLICATES; i++) {
                InputStreamReader fileInputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/jsonFiles/powerUpsDeck/" + code));

                Gson gson = new Gson();
                Type powerUpType = new TypeToken<PowerUpCard>() {
                }.getType();
                PowerUpCard powerUpCard = null;
                powerUpCard = gson.fromJson(fileInputStreamReader, powerUpType);

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

    public void discardPowerUp(PowerUpCard powerUpCard) {
        if (powerUpCard != null)
            discardedPowerUps.add(powerUpCard);
    }

}
