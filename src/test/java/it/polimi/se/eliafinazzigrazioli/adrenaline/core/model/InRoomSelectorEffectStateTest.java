package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The type In room selector effect state test.
 */
public class InRoomSelectorEffectStateTest {

    private GameBoard gameBoard;
    private EffectState effectState;
    private WeaponEffect weaponEffect;
    private WeaponCard weaponCard;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard(MapType.ONE);
        /*weaponEffect = new WeaponEffect(null, null);
        effectState = new InRoomSelectorEffectState(null,0, SelectableType.PLAYER, SelectableType.BOARDSQUARE);
        weaponEffect.setCurrentState(effectState);
        weaponCard = new WeaponCard(weaponEffect);*/
    }

    /**
     * Execute test.
     */
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
        gameBoard.movePlayer(playerTwo, gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 1)));
        gameBoard.movePlayer(playerThree, gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 1)));
        gameBoard.movePlayer(playerFour, gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 2)));


        //effectState.execute(weaponCard, gameBoard, playerOne);


    }
}
