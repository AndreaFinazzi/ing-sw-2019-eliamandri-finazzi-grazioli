package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**Laura<3*/

public class AmmoCardsDeck {

    List<AmmoCard> discardedCards;

    List<AmmoCard> deck;

    public AmmoCardsDeck() throws WeaponFileNotFoundException {

        List<String> cardCodes = new ArrayList<>();
        File weaponsFolder = new File("src/main/resources/jsonFiles/ammoCardsDeck");
        File[] listOfCardFiles = weaponsFolder.listFiles();
        for (int i = 0; i < listOfCardFiles.length; i++)
            cardCodes.add(listOfCardFiles[i].getName());


        deck = new ArrayList<>();
        for (String code: cardCodes)
            for (int i = 0; i < Rules.GAME_AMMO_CARDS_DUPLICATES; i++) {
                String filePath = "src/main/resources/jsonFiles/ammoCardsDeck/" + code;
                String jsonString;
                try {
                    jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
                }
                catch (IOException e){
                    throw new WeaponFileNotFoundException();
                }
                Gson gson = new Gson();
                Type ammoCardType = new TypeToken<AmmoCard>(){}.getType();
                AmmoCard ammoCard = gson.fromJson(jsonString, ammoCardType);
                deck.add(ammoCard);
            }
        discardedCards = new ArrayList<>();

    }

    public AmmoCard drawCard() {
        AmmoCard drawedCard;
        drawedCard = deck.get(new Random().nextInt(deck.size()));
        deck.remove(drawedCard);
        return drawedCard;
    }

    public void discard(AmmoCard card) {
        discardedCards.add(card);
    }
}
