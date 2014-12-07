package com.threerings.challenge.core.pojos;

/**
 * Created on/by:
 * User: evan
 * Date: 8/19/14
 */
public class Player {
    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    private int playerNumber;
    private int discColor;
    private int score;
    private boolean hasValidMovesAvailable;

    public Player(int playerNumber, int discColor) {
        this.playerNumber = playerNumber;
        this.discColor = discColor;
        this.hasValidMovesAvailable = true;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getDiscColor() {
        return discColor;
    }

    public void setDiscColor(int discColor) {
        this.discColor = discColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean hasValidMovesAvailable() {
        return hasValidMovesAvailable;
    }

    public void setHasValidMovesAvailable(boolean hasValidMovesAvailable) {
        this.hasValidMovesAvailable = hasValidMovesAvailable;
    }
}
