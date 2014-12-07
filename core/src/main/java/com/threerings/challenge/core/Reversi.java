package com.threerings.challenge.core;

import com.threerings.challenge.core.screens.GameScreen;
import playn.core.Game;
import playn.core.PlayN;

/**
 * Main game loop
 */
public class Reversi extends Game.Default {
    public static final int DEFAULT_UPDATE_RATE = 33;
    private Game.Default currentScreen;

    public Reversi() {
        super(DEFAULT_UPDATE_RATE);
    }

    @Override
    public void init() {
        currentScreen = new GameScreen();
        currentScreen.init();
    }

    @Override
    public void tick(int elapsed) {
        super.tick(elapsed);
        currentScreen.tick(elapsed);
    }

    @Override
    public void paint(float alpha) {
        super.paint(alpha);
        currentScreen.paint(alpha);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        currentScreen.update(delta);
    }
}
