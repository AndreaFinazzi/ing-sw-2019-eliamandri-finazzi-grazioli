package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.RuntimeTypeAdapterFactory;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponCardTest {


    @Test
    public void weaponCardSerializationTest() {
        WeaponCard weaponCard = new WeaponCard();
        /*Gson gson = new Gson();
        String json = gson.toJson(weaponCard);
        Type weaponType = new TypeToken<WeaponCard>(){}.getType();
        weaponCard = gson.fromJson(json, weaponType);*/
        //System.out.println(weaponCard);

        List<EffectState> states = new ArrayList<>(Arrays.asList(
                new VisibilitySelectorEffectState(true, "ehi", 1, SelectableType.PLAYER, SelectableType.PLAYER),
                new PreselectionBasedSelectorEffectState("bla", 3, null, true),
                new SelectionRequestEffectState(false, SelectableType.PLAYER, 0)
        ));
        RuntimeTypeAdapterFactory<EffectState> effectStateRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(EffectState.class, "type")
                .registerSubtype(SelectorEffectState.class, "SelectorEffectState")
                .registerSubtype(ActionEffectState.class, "ActionEffectState")
                .registerSubtype(VisibilitySelectorEffectState.class, "VisibilitySelectionEffectState")
                .registerSubtype(SelectionRequestEffectState.class, "SelectionRequestEffectState")
                .registerSubtype(CardinalDirectionSelectorEffectState.class, "CardinalDirectionSelectorEffectState")
                .registerSubtype(DistanceSelectorEffectState.class, "DistanceSelectorEffectState")
                .registerSubtype(InRoomSelectorEffectState.class, "InRoomSelectorEffectState")
                .registerSubtype(PreselectionBasedSelectorEffectState.class, "PreselectionBasedSelectorEffectState")
                .registerSubtype(MoveActionEffectState.class, "MoveActionEffectState")
                .registerSubtype(DamageActionEffectState.class, "DamageActionEffectState");
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(effectStateRuntimeTypeAdapterFactory)
                .create();
        Type effectStateListType = new TypeToken<List<EffectState>>() {
        }.getType();
        String json = gson.toJson(states, effectStateListType);
        System.out.println(json);
        states = gson.fromJson(json, effectStateListType);
        System.out.println(states);
    }

    @Test
    public void jsonTest() {
        String json = "{\n" +
                "    \"weaponName\" : \"Lock Refile\",\n" +
                "    \"cardColor\" : \"BLUE\",\n" +
                "    \"loader\" : [\"BLUE\"],\n" +
                "    \"effects\" : [\n" +
                "        {\n" +
                "            \"effectName\" : \"basic effect\",\n" +
                "            \"effectDescription\" : \"Deal 2 damage and 1 mark to 1 target you can see.\",\n" +
                "            \"price\" : [],\n" +
                "            \"effectStates\" : [\n" +
                "                {\n" +
                "                    \"type\" : \"VisibilitySelectorEffectState\",\n" +
                "                    \"notVisible\" : false,\n" +
                "                    \"referenceSource\" : null,\n" +
                "                    \"sourceSelectionOrder\" : 0,\n" +
                "                    \"selectionType\" : \"PLAYER\",\n" +
                "                    \"referenceType\" : \"PLAYER\"\n" +
                "                }, {\n" +
                "                    \"type\" : \"SelectionRequestEffectState\",\n" +
                "                    \"userSelectionRequired\" : true,\n" +
                "                    \"selectionType\" : \"PLAYER\",\n" +
                "                    \"maxSelectableItems\" : 1\n" +
                "                }, {\n" +
                "                    \"type\" : \"DamageActionEffectState\",\n" +
                "                    \"damageAmount\" : 2,\n" +
                "                    \"markAmount\" : 1, \n" +
                "                    \"playerToAffectSource\" : \"basic effect\",\n" +
                "                    \"toAffectPlayerSelectionOrder\" : 0\n" +
                "                }\n" +
                "            ],\n" +
                "            \"nextCallableEffects\" : [\n" +
                "                \"with second lock\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"effectName\" : \"with second lock\",\n" +
                "            \"effectDescription\" : \"Deal 1 mark to a different target you can see.\",\n" +
                "            \"price\" : [\"RED\"],\n" +
                "            \"effectStates\" : [\n" +
                "                {\n" +
                "                    \"type\" : \"VisibilitySelectorEffectState\",\n" +
                "                    \"notVisible\" : false,\n" +
                "                    \"referenceSource\" : null,\n" +
                "                    \"sourceSelectionOrder\" : 0,\n" +
                "                    \"selectionType\" : \"PLAYER\",\n" +
                "                    \"referenceType\" : \"PLAYER\"\n" +
                "                }, {\n" +
                "                    \"type\" : \"PreselectionBasedSelectorEffectState\",\n" +
                "                    \"previousSelected\" : {\n" +
                "                        \"basic effect\" : [0]\n" +
                "                    },\n" +
                "                    \"alreadySelected\" : false\n" +
                "                }, {\n" +
                "                    \"type\" : \"SelectionRequestEffectState\",\n" +
                "                    \"userSelectionRequired\" : true,\n" +
                "                    \"selectionType\" : \"PLAYER\",\n" +
                "                    \"maxSelectableItems\" : 1\n" +
                "                }, {\n" +
                "                    \"type\" : \"DamageActionEffectState\",\n" +
                "                    \"damageAmount\" : 0,\n" +
                "                    \"markAmount\" : 1, \n" +
                "                    \"playerToAffectSource\" : \"with second lock\",\n" +
                "                    \"toAffectPlayerSelectionOrder\" : 0\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"callableEffects\" : [\"basic effect\"]\n" +
                "}";
        RuntimeTypeAdapterFactory<EffectState> effectStateRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(EffectState.class, "type")
                .registerSubtype(SelectorEffectState.class, "SelectorEffectState")
                .registerSubtype(ActionEffectState.class, "ActionEffectState")
                .registerSubtype(VisibilitySelectorEffectState.class, "VisibilitySelectorEffectState")
                .registerSubtype(SelectionRequestEffectState.class, "SelectionRequestEffectState")
                .registerSubtype(CardinalDirectionSelectorEffectState.class, "CardinalDirectionSelectorEffectState")
                .registerSubtype(DistanceSelectorEffectState.class, "DistanceSelectorEffectState")
                .registerSubtype(InRoomSelectorEffectState.class, "InRoomSelectorEffectState")
                .registerSubtype(PreselectionBasedSelectorEffectState.class, "PreselectionBasedSelectorEffectState")
                .registerSubtype(MoveActionEffectState.class, "MoveActionEffectState")
                .registerSubtype(DamageActionEffectState.class, "DamageActionEffectState");
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(effectStateRuntimeTypeAdapterFactory)
                .create();
        Type weaponCardType = new TypeToken<WeaponCard>() {
        }.getType();
        WeaponCard card = gson.fromJson(json, weaponCardType);
        System.out.println(card);
    }

    @Test
    public void jsonParserTest() {
        try {
            System.out.println(WeaponCard.jsonParser("Electroscythe"));
        } catch (WeaponFileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void executeStepTest() {
        WeaponCard card;
        try {
            card = WeaponCard.jsonParser("LockRefile");
        } catch (WeaponFileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
