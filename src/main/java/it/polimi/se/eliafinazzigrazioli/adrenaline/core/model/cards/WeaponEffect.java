package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The type Weapon effect.
 */
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


    /**
     * Instantiates a new Weapon effect.
     *
     * @param currentState the current state
     */
    public WeaponEffect(EffectState currentState) {
        this.currentState = currentState;

        selectedRooms = new ArrayList<>(Arrays.asList(Room.PURPLE));

        beginningSelectionBuilding = true;
        toSelectPlayers = new ArrayList<>();
        toSelectBoardSquares = new ArrayList<>();
        toSelectRooms = new ArrayList<>();

    }


    /**
     * Gets effect name.
     *
     * @return the effect name
     */
    public String getEffectName() {
        return effectName;
    }

    /**
     * Gets selected player.
     *
     * @param selectionOrder the selection order
     * @return the selected player
     * @throws IndexOutOfBoundsException the index out of bounds exception
     */
/*
         Methods to retrieve a selected item based on the selection order.
         */
    public Player getSelectedPlayer(int selectionOrder) throws IndexOutOfBoundsException {
        return selectedPlayers.get(selectionOrder);
    }

    /**
     * Gets selected board square.
     *
     * @param selectionOrder the selection order
     * @return the selected board square
     */
    public BoardSquare getSelectedBoardSquare(int selectionOrder) {
        return selectedBoardSquares.get(selectionOrder);
    }

    /**
     * Gets selected room.
     *
     * @param selectionOrder the selection order
     * @return the selected room
     */
    public Room getSelectedRoom(int selectionOrder) {
        return selectedRooms.get(selectionOrder);
    }

    /**
     * Gets selected direction.
     *
     * @param selectionOrder the selection order
     * @return the selected direction
     */
    public CardinalDirection getSelectedDirection(int selectionOrder) {
        return selectedDirections.get(selectionOrder);
    }


    /**
     * Automatic selection.
     */
/*
    Method used to convert selection building lists to already selected items in case where items to be affected
    don't require a selection by the user but are uniquely determined by the effect itself.
     */
    public void automaticSelection() {
        selectedPlayers.addAll(toSelectPlayers);
        selectedBoardSquares.addAll(toSelectBoardSquares);
        selectedRooms.addAll(toSelectRooms);
    }


    /**
     * Add selected players.
     *
     * @param newSelectedPlayers the new selected players
     */
/*
    Methods used by the controller to send new selected items.
     */
    public void addSelectedPlayers(List<Player> newSelectedPlayers) {
        selectedPlayers.addAll(newSelectedPlayers);
    }

    /**
     * Add selected board squares.
     *
     * @param newSelectedBoardSquares the new selected board squares
     */
    public void addSelectedBoardSquares(List<BoardSquare> newSelectedBoardSquares) {
        selectedBoardSquares.addAll(newSelectedBoardSquares);
    }

    /**
     * Add selected rooms.
     *
     * @param newSelectedRooms the new selected rooms
     */
    public void addSelectedRooms(List<Room> newSelectedRooms) {
        selectedRooms.addAll(newSelectedRooms);
    }

    /**
     * Add selected directions.
     *
     * @param newSelectedDirections the new selected directions
     */
    public void addSelectedDirections(List<CardinalDirection> newSelectedDirections) {
        selectedDirections.addAll(newSelectedDirections);
    }


    /**
     * Gets to select players.
     *
     * @return the to select players
     */
    public List<Player> getToSelectPlayers() {
        return new ArrayList<>(toSelectPlayers);
    }

    /**
     * Gets to select board squares.
     *
     * @return the to select board squares
     */
    public List<BoardSquare> getToSelectBoardSquares() {
        return new ArrayList<>(toSelectBoardSquares);
    }

    /**
     * Gets to select rooms.
     *
     * @return the to select rooms
     */
    public List<Room> getToSelectRooms() {
        return new ArrayList<>(toSelectRooms);
    }


    /**
     * Sets current state.
     *
     * @param currentState the current state
     */
/*
    This method is to be used by the controller when the effect is controlled.
     */
    public void setCurrentState(EffectState currentState) {
        this.currentState = currentState;
    }


    /**
     * Execute list.
     *
     * @param invoker the invoker
     * @param gameBoard the game board
     * @param currentPlayer the current player
     * @return the list
     */
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


    /**
     * Initialize.
     */
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

    /**
     * Update to select players.
     *
     * @param newSelectedPlayers the new selected players
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

    /**
     * Update to select board squares.
     *
     * @param newSelectedBoardSquares the new selected board squares
     */
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

    /**
     * Update to select rooms.
     *
     * @param newSelectedRooms the new selected rooms
     */
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

    /**
     * Clear player selection.
     */
    public void clearPlayerSelection() {
        selectedPlayers.clear();
    }


    /*
    Method used to filter suggested callable effects.
     */

    /**
     * Gets next callable effects.
     *
     * @param invoker the invoker
     * @return the next callable effects
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

    /**
     * Selection reset.
     */
    public void selectionReset() {
        beginningSelectionBuilding = true;
    }

    /**
     * Gets effect description.
     *
     * @return the effect description
     */
    public String getEffectDescription() {
        return effectDescription;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public List<Ammo> getPrice() {
        return price;
    }

    /**
     * Needs selection boolean.
     *
     * @return the boolean
     */
    public boolean needsSelection() {
        return needsSelection;
    }

    /**
     * Sets needs selection.
     *
     * @param needsSelection the needs selection
     */
    public void setNeedsSelection(boolean needsSelection) {
        this.needsSelection = needsSelection;
    }

    /**
     * Has next state boolean.
     *
     * @return the boolean
     */
    public boolean hasNextState() {
        return stateIterator.hasNext();
    }

    /**
     * Next state.
     */
    public void nextState() {
        currentState = stateIterator.next();
    }

    /**
     * Sets already used.
     *
     * @param alreadyUsed the already used
     */
    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    /**
     * Is already used boolean.
     *
     * @return the boolean
     */
    /*
     * Method to know if the selected effect is still callable.
     */
    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }
}
