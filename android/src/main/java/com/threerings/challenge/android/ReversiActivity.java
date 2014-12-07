package com.threerings.challenge.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.threerings.challenge.core.Reversi;

public class ReversiActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/threerings/challenge/resources");
    PlayN.run(new Reversi());
  }
}
