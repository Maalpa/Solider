package com.solider.war.core.tools;
import com.solider.war.core.model.GPoint;

public class Point {
	
	public static final GPoint startPoint = new GPoint();					// Start point when mouse button is pressed down
	public static final GPoint transformStartPoint = new GPoint();			// Start point when mouse button is pressed down with map transform
	public static final GPoint endPoint = new GPoint();						// End point when mouse button is pressed up
	public static final GPoint transformEndPoint = new GPoint();			// End point when mouse button is pressed up with map transform
	public static final GPoint mousePoint = new GPoint();					// Current cords for mouse 
	public static final GPoint transformMousePoint = new GPoint();			// Current cords for mouse with map transform
	public static final GPoint soliderPoint = new GPoint();
	
	
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




	public static GPoint transformPoint(float x, float y) {
		return new GPoint((x+(-Transform.getX())), (y+(-Transform.getY())));
	}

	public static  void setSoliderPoint(float x , float y) {
		soliderPoint.setX(x);
		soliderPoint.setY(y);
	}
	
	public static GPoint getSoliderPoint() {
		return soliderPoint;
	}

	public static GPoint getStartpoint() {
		return startPoint;
	}

	public static GPoint getEndpoint() {
		return endPoint;
	}

	public static GPoint getMousepoint() {
		return mousePoint;
	}
	
	public static GPoint getTransformMousePoint() {
		return transformMousePoint;
	}
	
	public static GPoint getTransformStartPoint() {
		return transformStartPoint;
	}

	public static GPoint getTransformEndPoint() {
		return transformEndPoint;
	}
}
