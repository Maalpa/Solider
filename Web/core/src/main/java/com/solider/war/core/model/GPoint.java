package com.solider.war.core.model;


/**
 * Point on the screen transformed by the camera !!!
 * This is NOT point on the map grid !!!!!!
 *
 *
 */

public class GPoint {
	
	private float x; 
	private float y;
	
	public GPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public GPoint() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public void setPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
