package com.solider.war.core.tools;

public class Transform {
	private static float X = 0.0f;
	private static float Y = 0.0f;
	
	public static float getX() {
		return X;
	}
	public static float getY() {
		return Y;
	}
	
	public static void setTransform(float x, float y) {
		Transform.X = x;
		Transform.Y = y;
	}
	public static void setX(float x) {
		X = x;
	}
	public static void setY(float y) {
		Y = y;
	}
}
