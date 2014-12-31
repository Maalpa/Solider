package com.solider.war.java;

import com.solider.war.core.MainGame;
import playn.core.PlayN;
import playn.java.JavaPlatform;

public class MainGameJava {

  public static void main(String[] args) {

	 JavaPlatform.Config config = new JavaPlatform.Config();
	 config.width = 200;
	 config.height = 200;

	 JavaPlatform.register(config);
	 PlayN.run(new MainGame());
  }
}
