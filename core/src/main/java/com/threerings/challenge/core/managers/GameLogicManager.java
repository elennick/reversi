package com.threerings.challenge.core.managers;

import com.threerings.challenge.core.gameobjects.Disc;
import com.threerings.challenge.core.gameobjects.GameBoard;
import com.threerings.challenge.core.pojos.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on/by:
 * User: evan
 * Date: 8/19/14
 *
 * Manager intended to handle some core game logic stuff. I'm not really sure this actually warrants being separate from
 * the GameBoard class, the split between what is handled for the board there and here is kind of arbitrary. I just
 * wanted to get some of the more complex methods out and into their own class so GameBoard could be less cluttered.
 *
 * There is probably no need for this to be a singleton. It could probably just be static methods but I wanted to try out
 * the fancy way of doing enum singletons because it's super fancy and super cool. Actually it's not that cool, normally
 * I'd use a dependency injection tool like Spring or Guice to keep the code more loosely coupled and easily testable but
 * for this demo I'm going to disgust you with this horrible singleton. I'm sure you are full of terror after reading
 * this.
 */
public enum GameLogicManager {
    INSTANCE;

    /**
     * Iterate through all spots on the board. Any spot that is empty, check to see if it is a valid move for the
     * current player. If we find one, return true. If no valid moves are found, return false;
     *
     * @param gameBoard
     * @return
     */
    public boolean areThereAnyValidMovesForTheCurrentPlayer(GameBoard gameBoard) {
        Disc[][] discs = gameBoard.getDiscs();

        for(int i = 0; i < discs.length; i++) {
            for(int j = 0; j < discs[0].length; j++) {
                Disc disc = gameBoard.getDisc(i, j);

                //if this spot is empty check to see if its a valid move spot, if it is, return true
                if(null == disc && isValidMove(i, j, gameBoard, false)) {
                    return true;
                }
            }
        }

        //we found no valid moves
        return false;
    }

    /**
     * Verifies if a board click is valid and handles any processing necessary after that determination is made.
     *
     * @param x
     * @param y
     * @param gameBoard
     */
    public void handleGameBoardClick(int x, int y, GameBoard gameBoard) {
        if(GameLogicManager.INSTANCE.isValidMove(x, y, gameBoard, true)) {
            gameBoard.setDisc(x, y, gameBoard.getCurrentPlayer().getDiscColor());
            flipMarkedDiscs(gameBoard);
            gameBoard.nextPlayerTurn();
        } else {
            //TODO indicate that this move is invalid, display something on the screen so it doesnt just look like a lost click
        }
    }

    /**
     * Checks to make sure that the attempted board click is valid based on the current player and whether or not they
     * actually have a valid move by placing in this space.
     *
     * @param x   Where horizontally the board grid was clicked.
     * @param y   Where vertically the board grid was clicked.
     * @return    Is this move valid?
     */
    private boolean isValidMove(int x, int y, GameBoard gameBoard, boolean markDiscsForFlip) {
        return
            checkDirection(x, y, -1, 0, markDiscsForFlip, gameBoard) |  //left
            checkDirection(x, y, -1, -1, markDiscsForFlip, gameBoard) | //diagonal up-left
            checkDirection(x, y, 0, -1, markDiscsForFlip, gameBoard) |  //up
            checkDirection(x, y, 1, -1, markDiscsForFlip, gameBoard) |  //diagonal up-right
            checkDirection(x, y, 1, 0, markDiscsForFlip, gameBoard) |   //right
            checkDirection(x, y, 1, 1, markDiscsForFlip, gameBoard) |   //diagonal down-right
            checkDirection(x, y, 0, 1, markDiscsForFlip, gameBoard) |   //down
            checkDirection(x, y, -1, 1, markDiscsForFlip, gameBoard);   //diagonal down-left
    }

    /**
     * Check in a horizontal, vertical or diagonal line to see if this is a valid move in the specified direction from
     * the starting origin point.
     *
     * params:
     * xDirection = -1 means left
     * xDirection = 1 means right
     * yDirection = -1 means up
     * yDirection = 1 means down
     *
     * If a valid move is found, mark all discs for flipping. We don't want to flip them yet but we don't want to have
     * to run through this process again so we will mark them until we are ready to flip.
     *
     * @param x   Starting origin point x
     * @param y   Starting origin point y
     * @param xDirection  Direction to traverse on the x-axis.
     * @param yDirection  Direction to traverse on the y-axis.
     * @return
     */
    private boolean checkDirection(int x, int y, int xDirection, int yDirection, boolean markDiscsForFlip, GameBoard gameBoard) {
        List<Disc> discsToFlipIfValidMoveFound = new ArrayList<Disc>();
        Player currentPlayer = gameBoard.getCurrentPlayer();

        //set our tracking x,y values to start at the specified origin space...
        int currentX = x;
        int currentY = y;

        //...and then immediately move one spot in the specified direction to start checking if the move is valid
        currentX += xDirection;
        currentY += yDirection;

        //this loop iterates through the specified direction until it either notices the move isn't valid, the move IS valid or that it hit the end of the board
        while(currentX >= 0 && currentY >= 0 && currentX < gameBoard.getDiscs().length && currentY < gameBoard.getDiscs()[0].length) {
            Disc currentPositionDisc = gameBoard.getDisc(currentX, currentY);

            if(isWithinOneSpace(x, y, currentX, currentY)) {

                //if the space immediately next to this disc is empty or the same color, it is no good so we can stop the check for this direction and return false
                if(null == currentPositionDisc || currentPositionDisc.getCurrentColor() == currentPlayer.getDiscColor()) {
                    return false;
                }

            } else {

                //if we get to an empty spot after never finding a valid move then we can return false for this direction
                if(null == currentPositionDisc) {
                    return false;
                }

                //if we have made it to a same color disc after having passed over at least 1 other enemy colored disc then we found a valid move! return true
                if(currentPositionDisc.getCurrentColor() == currentPlayer.getDiscColor()) {
                    if(markDiscsForFlip) {
                        for (Disc disc : discsToFlipIfValidMoveFound) {
                            disc.markForFlip();
                        }
                    }

                    return true;
                }
            }

            //no definitive result from checking this spot so mark it as something we MIGHT flip move on to the next one in the direction
            discsToFlipIfValidMoveFound.add(currentPositionDisc);
            currentX += xDirection;
            currentY += yDirection;
        }

        //if the loop stops before we return anything it means we hit the edge of the board and we never found the move to be valid, return false
        return false;
    }

    /**
     * Flips all discs that were marked for flipping and then resets their flip flag. "Flip Flag" is the name of my
     * hardcore punk band.
     *
     * @param gameBoard
     */
    private void flipMarkedDiscs(GameBoard gameBoard) {
        Disc[][] discs = gameBoard.getDiscs();

        for(int i = 0; i < discs.length; i++) {
            for(int j = 0; j < discs[0].length; j++) {
                Disc disc = gameBoard.getDisc(i, j);
                if(null != disc && disc.isMarkedForFlip()) {
                    disc.flip();
                    disc.resetMarkForFlip();
                }
            }
        }
    }

    /**
     * Compares disc 1 coords to disc 2 coords to see if these two coords are within one space of each other either
     * horizontally, vertically or diagonally.
     *
     * @param disc1X
     * @param disc1Y
     * @param disc2X
     * @param disc2Y
     * @return
     */
    private boolean isWithinOneSpace(int disc1X, int disc1Y, int disc2X, int disc2Y) {
        //TODO probably some cleaner way to do this, figure it out
        if((disc1X == disc2X ||disc1X == disc2X - 1 ||disc1X == disc2X + 1) &&
                (disc1Y == disc2Y ||disc1Y == disc2Y - 1 ||disc1Y == disc2Y + 1)) {

            return true;
        }

        return false;
    }
}
