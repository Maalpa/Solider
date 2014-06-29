package com.solider.war.core.sprites.model;

import playn.core.GroupLayer;

import com.solider.war.core.model.MousePoint;
import com.solider.war.core.sprites.Animation;

public class Barrel extends Animation {
	
	public static String IMAGE = "sprites/barrel.png";
	public static String JSON = "sprites/barrel.json";

	public Barrel(GroupLayer layer, float x, float y) {
		super(layer, x, y, IMAGE, JSON);
		
		this.width = 40.0f;
		this.height = 64.0f;
	}
	
	public void update(int delta, MousePoint mousePoint) {
		super.update(delta, mousePoint);
		
		
		
		
		
		
	}
	
}
