package com.solider.war.core.model;

public class GameMap {
	
	private final int POOL_SIZE = 30;
	private int width; 
	private int height; 
	private int[][] mapGrid;
	
	public GameMap(int width , int height) {		
		this.width = width;
		this.height = height;
		mapGrid = new int[width/POOL_SIZE][height/POOL_SIZE];
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
	
	public int getPOOL_SIZE() {
		return POOL_SIZE;
	} 
}
