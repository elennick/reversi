package com.threerings.challenge.core.screens;

import com.threerings.challenge.core.Reversi;
import com.threerings.challenge.core.gameobjects.GameBoard;
import com.threerings.challenge.core.gameobjects.GameObject;
import com.threerings.challenge.core.gameobjects.PlayerDisplay;
import com.threerings.challenge.core.gameobjects.RestartDisplay;
import playn.core.*;

import java.util.ArrayList;
import java.util.List;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created on/by:
 * User: evan
 * Date: 8/18/14
 *
 * This is the main gameplay screen.
 */
public class GameScreen extends Game.Default {
    private GameBoard gameBoard;
    private PlayerDisplay playerDisplay;
    private RestartDisplay restartDisplay;
    private List<GameObject> gameObjects;
    private final static int BOARD_WIDTH = 8;
    private final static int BOARD_HEIGHT = 8;
    public static final boolean DEBUG = false;
    private ImageLayer bgLayer;

    public GameScreen() {
        super(Reversi.DEFAULT_UPDATE_RATE);
    }

    @Override
    public void init() {
        // create and background image layer
        Image bgImage = assets().getImage("images/bg.png");
        bgLayer = graphics().createImageLayer(bgImage);

        //init all the displays and drawable objects we need for our game and store them in a list
        gameObjects = new ArrayList<GameObject>();

        gameBoard = new GameBoard(BOARD_WIDTH, BOARD_HEIGHT);
        playerDisplay = new PlayerDisplay(550f, 100f, gameBoard.getCurrentPlayer().getPlayerNumber());
        restartDisplay = new RestartDisplay(550f, 200f);

        gameObjects.add(gameBoard);
        gameObjects.add(playerDisplay);
        gameObjects.add(restartDisplay);

        gameBoard.addPlayerListener(playerDisplay);
        restartDisplay.addRestartClickedListener(gameBoard);
        restartDisplay.addRestartClickedListener(playerDisplay);
    }

    @Override
    public void update(int v) {
        super.update(v);

        //iterate through all the game objects and tell them to update()
        for (GameObject gameObject : gameObjects) {
            if(null != gameObject) {
                gameObject.update(v);
            }
        }
    }

    @Override
    public void paint(float v) {
        super.paint(v);

        long startDrawTime;
        if(DEBUG) {
            startDrawTime = System.currentTimeMillis();
        }

        //iterate through all drawable game objects and tell them to paint()
        graphics().rootLayer().clear();     //deprecated after upgrading from 1.4 -> 1.8.5
        graphics().rootLayer().add(bgLayer);

        for (GameObject gameObject : gameObjects) {
            if(null != gameObject) {
                gameObject.paint(v);
            }
        }

        if(DEBUG) {
            long endDrawTime = System.currentTimeMillis();
            long timeToDraw = endDrawTime - startDrawTime;

            System.out.println("took " + timeToDraw + " ms to paint()");
        }
    }

    @Override
    public void tick(int elapsed) {
        super.tick(elapsed);

        //iterate through all the game objects and tell them to update()
        for (GameObject gameObject : gameObjects) {
            if(null != gameObject) {
                gameObject.tick(elapsed);
            }
        }
    }
}
