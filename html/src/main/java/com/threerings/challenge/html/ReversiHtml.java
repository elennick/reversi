package com.threerings.challenge.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.threerings.challenge.core.Reversi;

public class ReversiHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("reversi/");
    PlayN.run(new Reversi());
  }
}
