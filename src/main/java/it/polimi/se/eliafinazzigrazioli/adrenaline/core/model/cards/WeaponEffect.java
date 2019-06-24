package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.SelectableEffectsEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WeaponEffect {


    /*
     * This block of attributes characterizes the effect and it's the part
     * saved in the json file.
     */
    private String effectName;
    private String effectDescription;
    private List<Ammo> price;
    private ArrayList<EffectState> effectStates;
    private List<String> nextCallableEffects;
    private EffectState currentState;


    /*
     * This block of attributes is used to store items selected by the user.
     */
    private List<Player> selectedPlayers = new ArrayList<>();
    private List<BoardSquare> selectedBoardSquares = new ArrayList<>();
    private List<Room> selectedRooms = new ArrayList<>();
    private List<CardinalDirection> selectedDirections = new ArrayList<>();


    /*
     * This flag is used to in the update lists methods to avoid filtering
     * and simply add selected items when no visibility class has been run.
     */
    private boolean beginningSelectionBuilding = true;


    /*
     * Lists used to build the list of selectable items
     */
    private List<Player> toSelectPlayers;
    private List<BoardSquare> toSelectBoardSquares;
    private List<Room> toSelectRooms;

    /*
     * Other support attributes.
     */
    private Iterator<EffectState> stateIterator = effectStates.iterator();
    private boolean alreadyUsed = false;

    private boolean needsSelection = false;


    public WeaponEffect(EffectState currentState, List<Player> players) {
        this.currentState = currentState;
        this.toSelectPlayers = players;

        selectedRooms = new ArrayList<>(Arrays.asList(Room.PURPLE));

        beginningSelectionBuilding = true;
        toSelectPlayers = new ArrayList<>();
        toSelectBoardSquares = new ArrayList<>();
        toSelectRooms = new ArrayList<>();

    }


    public String getEffectName() {
        return effectName;
    }

    /*
         Methods to retrieve a selected item based on the selection order.
         */
    public Player getSelectedPlayer(int selectionOrder) throws IndexOutOfBoundsException {
        return selectedPlayers.get(selectionOrder);
    }

    public BoardSquare getSelectedBoardSquare(int selectionOrder) {
        return selectedBoardSquares.get(selectionOrder);
    }

    public Room getSelectedRoom(int selectionOrder) {
        return selectedRooms.get(selectionOrder);
    }

    public CardinalDirection getSelectedDirection(int selectionOrder) {
        return selectedDirections.get(selectionOrder);
    }


    /*
    Method used to convert selection building lists to already selected items in case where items to be affected
    don't require a selection by the user but are uniquely determined by the effect itself.
     */
    public void automaticSelection() {
        selectedPlayers.addAll(toSelectPlayers);
        selectedBoardSquares.addAll(toSelectBoardSquares);
        selectedRooms.addAll(toSelectRooms);
    }


    /*
    Methods used by the controller to send new selected items.
     */
    public void addSelectedPlayers(List<Player> newSelectedPlayers) {
        selectedPlayers.addAll(newSelectedPlayers);
    }

    public void addSelectedBoardSquares(List<BoardSquare> newSelectedBoardSquares) {
        selectedBoardSquares.addAll(newSelectedBoardSquares);
    }

    public void addSelectedRooms(List<Room> newSelectedRooms) {
        selectedRooms.addAll(newSelectedRooms);
    }

    public void addSelectedDirections(List<CardinalDirection> newSelectedDirections) {
        selectedDirections.addAll(newSelectedDirections);
    }


    public List<Player> getToSelectPlayers() {
        return new ArrayList<>(toSelectPlayers);
    }

    public List<BoardSquare> getToSelectBoardSquares() {
        return new ArrayList<>(toSelectBoardSquares);
    }

    public List<Room> getToSelectRooms() {
        return new ArrayList<>(toSelectRooms);
    }


    /*
    This method is to be used by the controller when the effect is controlled.
     */
    public void setCurrentState(EffectState currentState) {
        this.currentState = currentState;
    }


    /*
    Unique method to be called by the controller to run the effect.
     */
    public List<AbstractModelEvent> execute(WeaponCard invoker, GameBoard gameBoard, Player currentPlayer) {

        return currentState.execute(invoker, gameBoard, currentPlayer);

        /*List<AbstractModelEvent> events = null;
        while ((events == null || events.isEmpty()) && !currentState.isFinal()) {
            events = currentState.execute(invoker, gameBoard, currentPlayer); //A selection event is to be generated by the effect state
            if (stateIterator.hasNext()) {
                currentState = stateIterator.next();
            } else {
                events.add(new SelectableEffectsEvent(currentPlayer, invoker.getWeaponName(), getNextCallableEffects(invoker)));
            }
        }*/
    }


    /*
     * Method to initialize support attributes at the beginning of the execution.
     */
    public void initialize() {
        selectedPlayers = new ArrayList<>();
        selectedBoardSquares = new ArrayList<>();
        selectedRooms = new ArrayList<>();
        selectedDirections = new ArrayList<>();

        beginningSelectionBuilding = true;

        toSelectPlayers = new ArrayList<>();
        toSelectBoardSquares = new ArrayList<>();
        toSelectRooms = new ArrayList<>();

        stateIterator = effectStates.iterator();
        alreadyUsed = false;
    }



    /*
    Methods to build step by step the selectable set when it has to respect
    multiple requirements (like visible + at least one movement from you).
     */

    public void updateToSelectPlayers(List<Player> newSelectedPlayers) { //List of players built by last visibility step to be intersected with the previously computed
        if (beginningSelectionBuilding) {
            toSelectPlayers = newSelectedPlayers;
            beginningSelectionBuilding = false;
        } else {
            List<Player> newToSelectPlayers = new ArrayList<>();
            for (Player player : toSelectPlayers) {
                if (newSelectedPlayers.contains(player))
                    newToSelectPlayers.add(player);
            }
            toSelectPlayers = newToSelectPlayers;
        }
    }
    public void updateToSelectBoardSquares(List<BoardSquare> newSelectedBoardSquares) {
        if (beginningSelectionBuilding) {
            toSelectBoardSquares = newSelectedBoardSquares;
            beginningSelectionBuilding = false;
        } else {
            List<BoardSquare> newToSelectBoardSquares = new ArrayList<>();
            for (BoardSquare boardSquare : toSelectBoardSquares) {
                if (newSelectedBoardSquares.contains(boardSquare))
                    newToSelectBoardSquares.add(boardSquare);
            }
            toSelectBoardSquares = newToSelectBoardSquares;
        }
    }

    public void updateToSelectRooms(List<Room> newSelectedRooms) {
        if (beginningSelectionBuilding) {
            toSelectRooms = newSelectedRooms;
            beginningSelectionBuilding = false;
        } else {
            List<Room> newToSelectRooms = new ArrayList<>();
            for (Room roomColor : toSelectRooms) {
                if (newSelectedRooms.contains(roomColor))
                    newToSelectRooms.add(roomColor);
            }
            toSelectRooms = newToSelectRooms;
        }
    }


    /*
    Method used to filter suggested callable effects.
     */

    public List<String> getNextCallableEffects(WeaponCard invoker) {
        List<String> notUsedYetEffects = new ArrayList<>();
        for (String callableEffectName : nextCallableEffects) {
            WeaponEffect callableEffect = invoker.getEffectByName(callableEffectName);
            if (!callableEffect.isAlreadyUsed())
                notUsedYetEffects.add(callableEffectName);
        }
        return notUsedYetEffects;
    }

    /*
    See comment above the involved boolean attribute.
     */

    public void selectionReset() {
        beginningSelectionBuilding = true;
    }
    public String getEffectDescription() {
        return effectDescription;
    }

    public List<Ammo> getPrice() {
        return price;
    }

    public boolean needsSelection() {
        return needsSelection;
    }

    public void setNeedsSelection(boolean needsSelection) {
        this.needsSelection = needsSelection;
    }

    public boolean hasNextState() {
        return stateIterator.hasNext();
    }

    public void nextState() {
        currentState = stateIterator.next();
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    /*
     * Method to know if the selected effect is still callable.
     */
    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
}
