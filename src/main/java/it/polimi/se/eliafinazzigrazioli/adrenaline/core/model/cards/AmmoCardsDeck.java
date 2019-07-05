package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmmoCardsDeck {

    static final Logger LOGGER = Logger.getLogger(AmmoCardsDeck.class.getName());

    private List<AmmoCard> discardedCards;

    private List<AmmoCard> deck;

    public AmmoCardsDeck() {

        List<String> cardCodes = new ArrayList<>();
        BufferedReader ammoCardsListReader = null;
        try {
            ammoCardsListReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/jsonFiles/ammoCardsDeck/ammoCardsList.txt")));
            String ammoCardIndex;
            while ((ammoCardIndex = ammoCardsListReader.readLine()) != null) {
                cardCodes.add(ammoCardIndex + ".json");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (ammoCardsListReader != null) {
                try {
                    ammoCardsListReader.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        deck = new ArrayList<>();
        for (String code: cardCodes)
            for (int i = 0; i < Rules.GAME_AMMO_CARDS_DUPLICATES; i++) {
                InputStreamReader fileInputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/jsonFiles/ammoCardsDeck/" + code));
                Gson gson = new Gson();
                Type ammoCardType = new TypeToken<AmmoCard>(){}.getType();
                AmmoCard ammoCard = gson.fromJson(fileInputStreamReader, ammoCardType);
                deck.add(ammoCard);
            }
        discardedCards = new ArrayList<>();

    }

    public AmmoCard drawCard() {
        AmmoCard drawedCard;
        drawedCard = deck.get(new Random().nextInt(deck.size()));
        deck.remove(drawedCard);
        discard(drawedCard);
        if (deck.isEmpty()) {
            deck = discardedCards;
            discardedCards = new ArrayList<>();
        }
        return drawedCard;
    }

    private void discard(AmmoCard card) {
        discardedCards.add(card);
    }
}
