package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.CardinalSelectorEffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CardinalSelectorEffectStateTest {

    private GameBoard gameBoard;
    private EffectState effectState;
    private WeaponEffect weaponEffect;
    private WeaponCard weaponCard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard(MapType.ONE);
        effectState = new CardinalSelectorEffectState(null,0, SelectableType.PLAYER, SelectableType.BOARDSQUARE);
        weaponEffect = new WeaponEffect(effectState, null);
        weaponCard = new WeaponCard(weaponEffect);
    }

    @Test
    public void executeTest() {
        List<Player> visiblePlayers = new ArrayList<>();
        Player playerOne = new Player("Player1");
        visiblePlayers.add(playerOne);
        Player playerTwo = new Player("Player2");
        visiblePlayers.add(playerTwo);
        Player playerThree = new Player("Player3");
        //visiblePlayers.add(playerThree);
        Player playerFour = new Player("Player4");
        //visiblePlayers.add(playerFour);

        gameBoard.movePlayer(playerOne, gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 1)));
        gameBoard.movePlayer(playerTwo, gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 1)));
        gameBoard.movePlayer(playerThree, gameBoard.getBoardSquareByCoordinates(new Coordinates(3, 0)));
        gameBoard.movePlayer(playerFour, gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 0)));

        effectState.execute(weaponCard, gameBoard, playerOne);

        for (Player player:weaponEffect.getToSelectPlayers()){
            assert (visiblePlayers.contains(player));
        }
        System.out.println();
        for (Player player:visiblePlayers){
            assert (weaponEffect.getToSelectPlayers().contains(player));
        }


    }
}
