package com.solider.war.core.model;

public class MousePoint {
	
	private float x; 
	private float y;
	
	public MousePoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public MousePoint() {
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
