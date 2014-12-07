package com.threerings.challenge.java;

import playn.core.PlayN;
import playn.java.JavaGraphics;
import playn.java.JavaPlatform;

import com.threerings.challenge.core.Reversi;

public class ReversiJava {

    public static void main(String[] args) {
        JavaPlatform.Config config = new JavaPlatform.Config();
        config.width = 800;
        config.height = 600;

        JavaPlatform platform = JavaPlatform.register(config);
        platform.assets().setPathPrefix("com/threerings/challenge/resources");

        PlayN.run(new Reversi());
    }
}
