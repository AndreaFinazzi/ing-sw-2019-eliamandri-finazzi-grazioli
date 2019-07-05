package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Preselection based selector effect state.
 */
public class PreselectionBasedSelectorEffectState extends SelectorEffectState {

    private Map<String, List<Integer>> previousSelected;
    private boolean alreadySelected;

    /**
     * Instantiates a new Preselection based selector effect state.
     *
     * @param previousSelectionSource the previous selection source
     * @param sourceSelectionOrder the source selection order
     * @param previousSelected the previous selected
     * @param alreadySelected the already selected
     */
    public PreselectionBasedSelectorEffectState(String previousSelectionSource, int sourceSelectionOrder, Map<String, List<Integer>> previousSelected, boolean alreadySelected) {
        super(previousSelectionSource, sourceSelectionOrder);
        //this.previousSelected = previousSelected;
        //this.alreadySelected = alreadySelected;
    }

    @Override
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {
        List<Player> selectedPlayers = new ArrayList<>();
        List<Player> players = gameBoard.getPlayersOnGameBoard();
        for (Map.Entry<String, List<Integer>> effectIndexesEntry : previousSelected.entrySet()) {
            for (Integer selectionOrder : effectIndexesEntry.getValue()) {
                try {
                    selectedPlayers.add(invoker.getEffectByName(effectIndexesEntry.getKey()).getSelectedPlayer(selectionOrder));
                    selectedPlayers.remove(currentPlayer);
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
        if (alreadySelected)
            invoker.getActiveEffect().updateToSelectPlayers(selectedPlayers);
        else {
            players.removeAll(selectedPlayers);
            invoker.getActiveEffect().updateToSelectPlayers(players);
        }
        return new ArrayList<>();
    }
}
