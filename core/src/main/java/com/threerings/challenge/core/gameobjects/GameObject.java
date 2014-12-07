package com.threerings.challenge.core.gameobjects;

import com.threerings.challenge.core.Reversi;
import playn.core.Game;

/**
 * Created on/by:
 * User: evan
 * Date: 8/20/14
 *
 * Superclass to house common stuff related to simple objects being shown on the screen.
 */
abstract public class GameObject extends Game.Default {
    protected float screenPositionX;
    protected float screenPositionY;

    public GameObject() {
        super(Reversi.DEFAULT_UPDATE_RATE);
    }

    public float getScreenPositionX() {
        return screenPositionX;
    }

    public void setScreenPositionX(float screenPositionX) {
        this.screenPositionX = screenPositionX;
    }

    public float getScreenPositionY() {
        return screenPositionY;
    }

    public void setScreenPositionY(float screenPositionY) {
        this.screenPositionY = screenPositionY;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    public void paint(float alpha) {
        super.paint(alpha);
    }

    @Override
    public void tick(int elapsed) {
        super.tick(elapsed);
    }
}
