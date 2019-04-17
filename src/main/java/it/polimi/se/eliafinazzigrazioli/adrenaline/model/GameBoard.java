package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    BoardSquare[][] squaresMatrix;
    int width;
    int height;

    public GameBoard() {
        //TODO
    }

    public List<BoardSquare> getRoomSquares(Room room){
        List<BoardSquare> roomSquares = new ArrayList<BoardSquare>();
        for(int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                BoardSquare square = squaresMatrix[i][j];
                if (square != null && square.getRoom()==room){
                    roomSquares.add(square);
                }
            }
        }
        return roomSquares;
    }

    public List<BoardSquare> getVisibleSquares(BoardSquare referenceSquare){
        List<BoardSquare> visibleSquares = new ArrayList<BoardSquare>();
        visibleSquares.addAll(getRoomSquares(referenceSquare.getRoom()));
        Coordinates coord = referenceSquare.getCoordinates();
        if (referenceSquare.getNorth() == InterSquareLink.DOOR){
            visibleSquares.addAll(getRoomSquares(squaresMatrix[coord.getYCoordinates()+1][coord.getXCoordinates()].getRoom()));
        }
        if (referenceSquare.getSouth() == InterSquareLink.DOOR){
            visibleSquares.addAll(getRoomSquares(squaresMatrix[coord.getYCoordinates()-1][coord.getXCoordinates()].getRoom()));
        }
        if (referenceSquare.getNorth() == InterSquareLink.DOOR){
            visibleSquares.addAll(getRoomSquares(squaresMatrix[coord.getYCoordinates()-1][coord.getXCoordinates()].getRoom()));
        }
        if (referenceSquare.getNorth() == InterSquareLink.DOOR){
            visibleSquares.addAll(getRoomSquares(squaresMatrix[coord.getYCoordinates()-1][coord.getXCoordinates()].getRoom()));
        }

    }

}
