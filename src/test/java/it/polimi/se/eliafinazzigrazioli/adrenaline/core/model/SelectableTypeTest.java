package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;

/**
 * The type Selectable type test.
 */
public class SelectableTypeTest {

    /**
     * Selectable type serialization test.
     */
    @Test
    public void selectableTypeSerializationTest() {
        List<SelectableType> selectableTypes = new ArrayList<>(Arrays.asList(
                SelectableType.BOARDSQUARE,
                SelectableType.BOARDSQUARE,
                SelectableType.PLAYER,
                SelectableType.DIRECTION,
                SelectableType.ROOM));
        Gson gson = new Gson();
        String json = gson.toJson(selectableTypes);
        System.out.println(json);
    }

    /**
     * Selectable type deserialization test.
     */
    @Test
    public void selectableTypeDeserializationTest() {
        String selectableTypesJson = "['BOARDSQUARE', 'PLAYER', 'ROOM', null]";
        Gson gson = new Gson();
        Type selectableTypeListType = new TypeToken<List<SelectableType>>() {
        }.getType();
        List<SelectableType> selectableTypes = gson.fromJson(selectableTypesJson, selectableTypeListType);
        System.out.println(selectableTypes.get(2).getClass());
    }

    /**
     * Map serialization test.
     */
    @Test
    public void mapSerializationTest() {
        Map<Ammo, String> ammoStringMap = new HashMap<>();
        ammoStringMap.put(Ammo.RED, "red");
        ammoStringMap.put(Ammo.YELLOW, "yellow");
        ammoStringMap.put(Ammo.BLUE, "blue");
        Gson gson = new Gson();
        String json = gson.toJson(ammoStringMap);
        System.out.println(json);
    }
}
