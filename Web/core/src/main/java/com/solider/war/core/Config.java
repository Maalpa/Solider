package com.solider.war.core;

import static com.solider.war.core.Config.PATH_MAP_SIZE;

import com.solider.war.core.path.MapPoint;

public class Config {
	
	// Main config statics
	public static final int WINDOW_WIDTH = 700;
	public static final int WINDOW_HEIGHT = 700;
	public static final int MAP_SIZE = 1021;
	public static final int FIELD_SIZE = 10;
	public static final int PATH_MAP_SIZE = MAP_SIZE/FIELD_SIZE;
	
	// Animation sprites config
	public static final int DEFAULT_HEALTH = 100;
	public static final int DEFAULT_SHIELD = 100;
	public static final boolean DRAW_PATH = true;
	
	
	
	
}
