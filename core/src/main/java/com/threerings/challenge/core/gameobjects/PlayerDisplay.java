package com.threerings.challenge.core.gameobjects;

import com.threerings.challenge.core.listeners.PlayerListener;
import com.threerings.challenge.core.listeners.RestartListener;
import com.threerings.challenge.core.pojos.Player;
import playn.core.Image;
import playn.core.ImageLayer;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created on/by:
 * User: evan
 * Date: 8/19/14
 */
public class PlayerDisplay extends GameObject implements PlayerListener, RestartListener {
    private Image player1Image;
    private Image player2Image;
    private Image whitePieceImage;
    private Image blackPieceImage;
    private int currentPlayer;

    public PlayerDisplay(float screenPositionX, float screenPositionY, int initialPlayerTurn) {
        this.screenPositionX = screenPositionX;
        this.screenPositionY = screenPositionY;

        player1Image = assets().getImage("images/player1-turn.png");
        player2Image = assets().getImage("images/player2-turn.png");
        blackPieceImage = assets().getImage("images/black-piece.png");
        whitePieceImage = assets().getImage("images/white-piece.png");

        currentPlayer = initialPlayerTurn;
    }

    @Override
    public void playerTurnChanged(int currentPlayer) {
        System.out.println("turn changed");
        System.out.println("currentPlayer = " + currentPlayer);

        this.currentPlayer = currentPlayer;
    }

    @Override
    public void restartClicked() {
        this.currentPlayer = Player.PLAYER_ONE;
    }

    @Override
    public void paint(float v) {
        super.paint(v);

        //paint the player text image
        //TODO look into whether PlayN can display and render text easily and do that instead of an image here?
        ImageLayer playerDisplayLayer;
        if(currentPlayer == Player.PLAYER_ONE) {
            playerDisplayLayer = graphics().createImageLayer(player1Image);
        } else {
            playerDisplayLayer = graphics().createImageLayer(player2Image);
        }

        graphics().rootLayer().add(playerDisplayLayer);
        playerDisplayLayer.setTranslation(screenPositionX, screenPositionY);

        //paint a picture of the disc color for the current players turn
        ImageLayer playerDisplayDiscLayer;
        if(currentPlayer == Player.PLAYER_ONE) {
            playerDisplayDiscLayer = graphics().createImageLayer(whitePieceImage);
        } else {
            playerDisplayDiscLayer = graphics().createImageLayer(blackPieceImage);
        }

        graphics().rootLayer().add(playerDisplayDiscLayer);
        float discXPosition = screenPositionX - whitePieceImage.width();
        playerDisplayDiscLayer.setTranslation(discXPosition, screenPositionY);
    }
}
