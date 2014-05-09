package com.solider.war.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.solider.war.core.MainGame;

public class MainGameActivity extends GameActivity {
	
  @Override
  public void main() {
    PlayN.run(new MainGame());
  }
  
}
