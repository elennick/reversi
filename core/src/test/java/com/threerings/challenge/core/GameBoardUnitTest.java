package com.threerings.challenge.core;

/**
 * Created on/by:
 * User: evan
 * Date: 8/18/14
 */
public class GameBoardUnitTest {
    //TODO have to figure out how to get assets to load in tests, for some reason maven is not copying test resources
    //TODO when running mvn test so GameBoard is getting a NPE trying to load board-square.png
    //TODO probably NOT going to spend much more time on this since this is just a demo/challenge app
//    @Test
//    public void testCanSetDisc() {
//        GameBoard gameBoard = new GameBoard(8, 8, 0, 0);
//
//        gameBoard.setDisc(0, 0, Disc.BLACK);
//        Assert.assertEquals(Disc.BLACK, gameBoard.getDisc(0, 0).getCurrentColor());
//    }
//
//    @Test
//    public void testCanFlipDisc() {
//        GameBoard gameBoard = new GameBoard(8, 8);
//
//        gameBoard.setDisc(0, 0, Disc.BLACK);
//        gameBoard.getDisc(0, 0).flip();
//        Assert.assertEquals(Disc.WHITE, gameBoard.getDisc(0, 0).getCurrentColor());
//    }
//
//    @Test
//    public void testIsBoardRightSizeAfterCreation() {
//        GameBoard gameBoard = new GameBoard(1, 8);
//
//        Assert.assertEquals(1, gameBoard.getHeight());
//        Assert.assertEquals(8, gameBoard.getWidth());
//    }
//
//    @Test
//    public void testCheckDirection() {
//        GameBoard gameBoard = new GameBoard(8, 8, 0, 0);
//
//        gameBoard.checkDirection(3, 4, 1, 0, false);
//    }
}
