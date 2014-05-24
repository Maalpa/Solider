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
		canvas.clearRect(clearX, clearY, clearWidthX, clearWidthY);
		canvas.fillRect( x , y, width, height);		
		clearX = x;
		clearY = y;
		clearWidthX = width;
		clearWidthY = height;
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
}
