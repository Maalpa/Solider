package com.solider.war.core.sprites.model;

import java.util.LinkedList;

import playn.core.GroupLayer;

import com.solider.war.core.model.MousePoint;
import com.solider.war.core.path.PathPoint;
import com.solider.war.core.sprites.Animation;

public class Barrel extends Animation {
	
	public static String IMAGE = "sprites/barrel.png";
	public static String JSON = "sprites/barrel.json";

	public Barrel(float x, float y, GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);
		
		this.width = 40.0f;
		this.height = 64.0f;
	}
	
	public void update(int delta, MousePoint mousePoint) {
		super.update(delta, mousePoint);
	}
}
