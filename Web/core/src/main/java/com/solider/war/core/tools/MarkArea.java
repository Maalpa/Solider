package com.solider.war.core.tools;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.ImageLayer;


/**
 * @author Paweł Kępa
 * 
 */
public class MarkArea {
	
	private static final int WIDTH = 1021;
	private static final int HEIGHT = 1021;
	private static final float ALPHA = 0.5f;
	private static final int COLOR = 0xff87ceeb;
	
	private int clearX;
	private int clearY;
	private int clearWidthX;
	private int clearWidthY;
	
	// cords used to check if objects intersets with others objects
	private int x;
	private int y;
	private int width;
	private int height;
	
	private CanvasImage	bgtile;	
	private ImageLayer bg;
	private Canvas canvas;
	
	public MarkArea(GroupLayer parentLayer) {
		this.bgtile = graphics().createImage(WIDTH, HEIGHT);
    	this.canvas = bgtile.canvas();
    	this.canvas.setFillColor(COLOR).setAlpha(ALPHA);
    	this.bg = graphics().createImageLayer(bgtile);
    	this.bg.setWidth(WIDTH);
    	this.bg.setHeight(HEIGHT);
 
    	// Add to main layer 
    	parentLayer.add(bg);
	}
	
	public void startMarking(int x, int y, int width, int height) {
		this.canvas.clearRect(clearX, clearY, clearWidthX, clearWidthY);
		this.canvas.fillRect( x , y, width, height);		
		this.clearX = x;
		this.clearY = y;
		this.clearWidthX = width;
		this.clearWidthY = height;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public void markPath(int x, int y, int width, int height) {
		this.canvas.fillRect( x , y, width, height);
	}
	
	public void mark(float startX, float startY, float endX, float endY) {
		
		// first 
		if( (startX < endX) && (startY < endY) ) {
			startMarking( (int) (startX), (int) (startY), (int) (endX - startX), (int) (endY - startY));
		} 
		// secound
		else if ( (startX > endX) &&  (startY < endY) ) {
			startMarking( (int) (endX), (int) (startY), (int) (startX - endX), (int) (endY - startY));
		}
		// third 
		else if ( (startX > endX) && (startY > endY) ) {
			startMarking( (int) (endX), (int) (endY), (int) (startX - endX), (int) (startY - endY));
		}
		// four
		else if ( (startX < endX) &&  (startY > endY) ) {
			startMarking( (int) (startX), (int) (endY), (int) (endX - startX), (int) (startY - endY));
		}
	}
	
	public void clear(){
		canvas.clear();
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
	
}
