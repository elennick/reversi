package com.threerings.challenge.core.gameobjects;

import com.threerings.challenge.core.listeners.RestartListener;
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
 * Date: 8/20/14
 */
public class RestartDisplay extends GameObject {
    private Image buttonImage;
    private List<RestartListener> listeners;

    public RestartDisplay(float screenPositionX, float screenPositionY) {
        this.screenPositionX = screenPositionX;
        this.screenPositionY = screenPositionY;

        this.buttonImage = assets().getImage("images/restart-button.png");

        listeners = new ArrayList<RestartListener>();
    }

    @Override
    public void paint(float v) {
        super.paint(v);

        ImageLayer restartButtonLayer = graphics().createImageLayer(buttonImage);
        graphics().rootLayer().add(restartButtonLayer);
        restartButtonLayer.setTranslation(screenPositionX, screenPositionY);

        restartButtonLayer.addListener(new Pointer.Adapter() {
            public void onPointerStart(Pointer.Event event) {
                System.out.println("restart clicked!");
                notifyListenersOfRestartClicked();
            }
        });
    }

    public void notifyListenersOfRestartClicked() {
        for (RestartListener listener : listeners) {
            listener.restartClicked();
        }
    }

    public void addRestartClickedListener(RestartListener listener) {
        listeners.add(listener);
    }
}
