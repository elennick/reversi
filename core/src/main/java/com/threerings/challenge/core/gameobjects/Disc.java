package com.threerings.challenge.core.gameobjects;

import playn.core.Image;
import playn.core.ImageLayer;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created on/by:
 * User: evan
 * Date: 8/18/14
 */
public class Disc extends GameObject {
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    private int currentColor;
    private Image blackPieceImage;
    private Image whitePieceImage;
    private boolean markForFlip;
    private int boardPositionX;
    private int boardPositionY;

    public Disc(int color, int boardPositionX, int boardPositionY) {
        this.currentColor = color;
        this.boardPositionX = boardPositionX;
        this.boardPositionY = boardPositionY;

        //TODO change how and/or where we're doing this so we arent loading the same images 60+ times? or is this ok as is?
        blackPieceImage = assets().getImage("images/black-piece.png");
        whitePieceImage = assets().getImage("images/white-piece.png");

        screenPositionX = 0;
        screenPositionY = 0;
    }

    public void flip() {
        if(BLACK == currentColor) {
            currentColor = WHITE;
        } else {
            currentColor = BLACK;
        }
    }

    public boolean isMarkedForFlip() {
        return markForFlip;
    }

    /**
     * Sets a flag indicating that this disc is going to get flipped before the next turn starts.
     */
    public void markForFlip() {
        this.markForFlip = true;
    }

    public void resetMarkForFlip() {
        this.markForFlip = false;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public int getBoardPositionX() {
        return boardPositionX;
    }

    public void setBoardPositionX(int boardPositionX) {
        this.boardPositionX = boardPositionX;
    }

    public int getBoardPositionY() {
        return boardPositionY;
    }

    public void setBoardPositionY(int boardPositionY) {
        this.boardPositionY = boardPositionY;
    }

    public void setScreenPosition(float x, float y) {
        this.screenPositionX = x;
        this.screenPositionY = y;
    }

    @Override
    public void paint(float v) {
        ImageLayer pieceLayer;
        if(BLACK == getCurrentColor()) {
            pieceLayer = graphics().createImageLayer(blackPieceImage);
        } else {
            pieceLayer = graphics().createImageLayer(whitePieceImage);
        }

        graphics().rootLayer().add(pieceLayer);
        pieceLayer.setTranslation(screenPositionX, screenPositionY);
    }

    @Override
    public String toString() {
        return "Disc at position (" + this.getBoardPositionX() + ", " + this.getBoardPositionY() + ")" +
                " with color " + getColorString(this.getCurrentColor());
    }

    public static String getColorString(int color) {
        if(color == BLACK) {
            return "BLACK";
        } else {
            return "WHITE";
        }
    }
}
