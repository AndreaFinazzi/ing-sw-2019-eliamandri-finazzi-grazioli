package it.polimi.se.eliafinazzigrazioli.adrenaline;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSupportClasses {

    public static Match match = new Match();
    private static final List<String> defaultNames = Arrays.asList(
            "SGrez",
            "ToniIlBello",
            "FinazIlDuro-X(",
            "NataliaLaGenerosa",
            "QuelloConCuiNataliaFaLaGenerosa"
    );
    private static List<Avatar> avatarList = new ArrayList<>(Arrays.asList(Avatar.values()));

    public static void instanceMatch(MapType mapType,int numOfPlayers) {
        match.setGameBoard(mapType);
        for (int i = 0; i < numOfPlayers; i++) {

            try {
                match.addPlayer(defaultNames.get(i), avatarList.remove(0));
            } catch (MaxPlayerException e) {
                e.printStackTrace();
            } catch (PlayerAlreadyPresentException e) {
                e.printStackTrace();
            } catch (AvatarNotAvailableException e) {
                e.printStackTrace();
            }



        }
    }
}
