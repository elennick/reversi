package com.threerings.challenge.core.gameobjects;

import com.threerings.challenge.core.listeners.PlayerListener;
import com.threerings.challenge.core.listeners.RestartListener;
import com.threerings.challenge.core.managers.GameLogicManager;
import com.threerings.challenge.core.pojos.Player;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

import java.util.ArrayList;
import java.util.List;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created on/by:
 * User: evan
 * Date: 8/18/14
 */
public class GameBoard extends GameObject implements RestartListener {
    private Disc[][] discs;  //null spots in this grid count as empty with no disc on the spot
    private Image boardSquareImage;
    private boolean isAcceptingInput;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private List<PlayerListener> playerListeners;
    private int width;
    private int height;
//    private int pieceWidth;
//    private int pieceHeight;
    private static final int PIECE_WIDTH = 60; //pixels
    private static final int PIECE_HEIGHT = 60; //pixels

    /**
     * GameBoard constructor.
     *
     * @param width    How many squares wide this board should be.
     * @param height   How many squares tall this board should be.
     */
    public GameBoard(int width, int height) {
        //we dont want the width or height to ever be odd numbers, otherwise our starting pieces can't be centered since they are 2x2
        this.width = width;
        this.height = height;

        if(this.width % 2 > 0) {
            this.width++;
        }
        if(this.height % 2 > 0) {
            this.height++;
        }

        boardSquareImage = assets().getImage("images/board-square.png");
//        this.pieceWidth = Math.round(boardSquareImage.width());        //stopped working after 1.4 -> 1.8.5 playn upgrade, width always comes back as 0
//        this.pieceHeight = Math.round(boardSquareImage.height());

        this.isAcceptingInput = true;

        player1 = new Player(Player.PLAYER_ONE, Disc.WHITE);
        player2 = new Player(Player.PLAYER_TWO, Disc.BLACK);

        playerListeners = new ArrayList<PlayerListener>();

        reset();
    }

    /**
     * Sets the board to the start of the game.
     */
    public void reset() {
        //this is the grid of spaces on the board, we consider the top left to be (0, 0)
        discs = new Disc[height][width];

        assignStartingPieces();

        this.currentPlayer = player1;
    }

    public void setDisc(int x, int y, int color) throws ArrayIndexOutOfBoundsException {
        Disc disc = discs[x][y];
        if(null == disc) {
            disc = new Disc(color, x, y);
        }

        discs[x][y] = disc;
    }

    @Override
    public void paint(float v) {
        super.paint(v);

        for(int i = 0; i < discs.length; i++) {
            for(int j = 0; j < discs[0].length; j++) {
                Disc disc = getDisc(i, j);

                if(null != disc) {
                    //if there is a disc at this spot, paint it
                    disc.setScreenPosition(i * PIECE_WIDTH, j * PIECE_HEIGHT);
                    disc.paint(v);
                } else {
                    //otherwise paint a piece of the empty gameboard
                    paintBoardSquare(i, j);
                }
            }
        }
    }

    @Override
    public void restartClicked() {
        reset();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayerListener(PlayerListener listener) {
        playerListeners.add(listener);
    }

    public Disc getDisc(int x, int y) throws ArrayIndexOutOfBoundsException {
        return discs[x][y];
    }

    public Disc[][] getDiscs() {
        return discs;
    }

    public int getWidth() {
        return discs.length;
    }

    public int getHeight() {
        return discs[0].length;
    }

    /**
     * Transitions to the next players turn.
     */
    public void nextPlayerTurn() {
        updateScores();

        if(currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }

        //notify anyone who has registered to listen for player turn change events
        for (PlayerListener playerListener : playerListeners) {
            playerListener.playerTurnChanged(currentPlayer.getPlayerNumber());
        }

        //check to see if this player has any valid moves available, if not, pass to the next player
        //if both players have no valid moves available, game is over
        boolean playerHasMoves = GameLogicManager.INSTANCE.areThereAnyValidMovesForTheCurrentPlayer(this);
        if(!playerHasMoves) {
            currentPlayer.setHasValidMovesAvailable(false);

            if(!player1.hasValidMovesAvailable() && !player2.hasValidMovesAvailable()) {
                //TODO game is over, reset or give a button to restart or something
                System.out.println("*** NO MOVES LEFT, GAME IS OVER!");
                System.out.println("FINAL SCORE:");
                System.out.println("Player 1 -> " + player1.getScore());
                System.out.println("Player 2 -> " + player2.getScore());
            } else {
                nextPlayerTurn();
            }
        } else {
            //it's possible for a player to have no valid moves and then be given a valid move the next turn by his opponent
            //so make sure we set this back to true in case it was previously false
            currentPlayer.setHasValidMovesAvailable(true);
        }
    }

    /**
     * Sets up the initial board state for a new game board.
     */
    private void assignStartingPieces() {
        int xIndex = (getWidth() / 2)  - 1;   //we enforce the x and y values being even in the constructor so this should be safe
        int yIndex = (getHeight() / 2)  - 1;

        discs[xIndex][yIndex] = new Disc(Disc.WHITE, xIndex, yIndex);
        discs[xIndex + 1][yIndex] = new Disc(Disc.BLACK, xIndex + 1, yIndex);
        discs[xIndex][yIndex + 1] = new Disc(Disc.BLACK, xIndex, yIndex + 1);
        discs[xIndex + 1][yIndex + 1] = new Disc(Disc.WHITE, xIndex + 1, yIndex + 1);
    }

    /**
     * Draws an empty piece of the board and sets a listener on it for clicks.
     *
     * @param x
     * @param y
     */
    private void paintBoardSquare(final int x, final int y) {
        ImageLayer pieceLayer = graphics().createImageLayer(boardSquareImage);
        graphics().rootLayer().add(pieceLayer);
        pieceLayer.setTranslation(x * PIECE_WIDTH, y * PIECE_HEIGHT);

        pieceLayer.addListener(new Pointer.Adapter() {
            public void onPointerStart(Pointer.Event event) {
                System.out.println("board square clicked at board position (" + x + "," + y + ")");

                if(isAcceptingInput) {
                    GameLogicManager.INSTANCE.handleGameBoardClick(x, y, GameBoard.this);
                }
            }
        });
    }

    /**
     * Updates player scores to reflect the current board state.
     */
    private void updateScores() {
        int playerOneScore = 0;
        int playerTwoScore = 0;

        for(int i = 0; i < discs.length; i++) {
            for(int j = 0; j < discs[0].length; j++) {
                Disc disc = getDisc(i, j);

                if(disc != null) {
                    if(disc.getCurrentColor() == player1.getDiscColor()) {
                        playerOneScore++;
                    } else if(disc.getCurrentColor() == player2.getDiscColor()) {
                        playerTwoScore++;
                    }
                }
            }
        }

        player1.setScore(playerOneScore);
        player2.setScore(playerTwoScore);
    }
}
