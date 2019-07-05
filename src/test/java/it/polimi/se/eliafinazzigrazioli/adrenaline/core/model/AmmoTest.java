package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmmoTest {


    @Test
    public void ammoSerializationTest() {
        List<Ammo> ammos = new ArrayList<>(Arrays.asList(Ammo.YELLOW, Ammo.BLUE, Ammo.RED, Ammo.BLUE));
        Gson gson = new Gson();
        String ammoJson = gson.toJson(ammos);
        System.out.println(ammoJson);
    }

    @Test
    public void ammoTest() {
        List<Ammo> ammoList = new ArrayList<>();
        ammoList.add(Ammo.BLUE);
        ammoList.add(Ammo.YELLOW);
        AmmoCard ammoCard = new AmmoCard(ammoList, false, "01");
        for(Ammo value : Ammo.values()) {
            assertNotNull(value.toStringToMap());
        }

    }

    @Test
    public void ammoDeserializationTest() {
        String jsonAmmos = "['RED', 'RED', 'YELLOW', 'BLUE']";
        Gson gson = new Gson();
        Type ammoListType = new TypeToken<List<Ammo>>() {
        }.getType();
        List<Ammo> ammos = gson.fromJson(jsonAmmos, ammoListType);
        System.out.println(ammos);
    }
}
