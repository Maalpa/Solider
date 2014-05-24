package com.solider.war.core.tools;

import com.solider.war.core.model.MousePoint;

public class Point {
	
	public static final MousePoint startPoint = new MousePoint();					// Start point when mouse button is pressed down
	public static final MousePoint transformStartPoint = new MousePoint();			// Start point when mouse button is pressed down with map transform
	
	public static final MousePoint endPoint = new MousePoint();						// End point when mouse button is pressed up
	public static final MousePoint transformEndPoint = new MousePoint();			// End point when mouse button is pressed up with map transform
	
	public static final MousePoint mousePoint = new MousePoint();					// Current cords for mouse 
	public static final MousePoint transformMousePoint = new MousePoint();			// Current cords for mouse with map transform
	
	
	public static final MousePoint soliderPoint = new MousePoint();
	
	
	public static void setStartPoint(float x, float y) {
		startPoint.setX(x);
		startPoint.setY(y);
		transformStartPoint.setX(x+(-Transform.getX()));
		transformStartPoint.setY(y+(-Transform.getY()));
	}
	
	public static void setEndPoint(float x, float y ) {
		endPoint.setX(x);
		endPoint.setY(y);
		transformEndPoint.setX(x+(-Transform.getX()));
		transformEndPoint.setY(y+(-Transform.getY()));
	}
	
	public static void setMousePoint(float x, float y) {
		mousePoint.setX(x);
		mousePoint.setY(y);
		transformMousePoint.setX(x+(-Transform.getX()));
		transformMousePoint.setY(y+(-Transform.getY()));
	}
	
	public static  void setSoliderPoint(float x , float y) {
		soliderPoint.setX(x);
		soliderPoint.setY(y);
	}
	
	
	public static MousePoint getSoliderPoint() {
		return soliderPoint;
	}

	public static MousePoint getStartpoint() {
		return startPoint;
	}

	public static MousePoint getEndpoint() {
		return endPoint;
	}

	public static MousePoint getMousepoint() {
		return mousePoint;
	}
	
	public static MousePoint getTransformMousePoint() {
		return transformMousePoint;
	}
	

	public static MousePoint getTransformStartPoint() {
		return transformStartPoint;
	}

	public static MousePoint getTransformEndPoint() {
		return transformEndPoint;
	}
}
