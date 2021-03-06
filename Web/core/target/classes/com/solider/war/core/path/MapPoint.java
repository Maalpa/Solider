package com.solider.war.core.path;

public class MapPoint {

	private int x;
	private int y;
	private boolean visited = false;
	private boolean occupied = false; // is this field is occupied
	private int value = -1;
	private double destinationValue = 0;

	public MapPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public double getDestinationValue() {
		return destinationValue;
	}

	public void setDestinationValue(double destinationValue) {
		this.destinationValue = destinationValue;
	}
}

