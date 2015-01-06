package com.solider.war.core.model;

import static com.solider.war.core.Config.FIELD_SIZE;

public class GameMap {
	
	private int width; 
	private int height; 
	private int[][] mapGrid;
	
	public GameMap(int width , int height) {		
		this.width = width;
		this.height = height;
		mapGrid = new int[width/FIELD_SIZE][height/FIELD_SIZE];
		System.out.println("mapGrid size = " + mapGrid.length);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int[][] getMapGrid() {
		return mapGrid;
	}
	
	public void setMapGrid(int[][] mapGrid) {
		this.mapGrid = mapGrid;
	}
	
}
