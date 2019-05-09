package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects.VisibilitySelectionEffectState;
import org.junit.Before;

public class VisibilitySelectorEffectStateTest {

    private GameBoard gameBoard;
    private EffectState effectState;
    private WeaponEffect weaponEffect;
    private WeaponCard weaponCard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard(MapType.ONE);
        effectState = new VisibilitySelectionEffectState(true, null,0, SelectableType.PLAYER, SelectableType.BOARDSQUARE);
        weaponEffect = new WeaponEffect(effectState, null);


    }

    public void executeTest(){

    }

}
