package com.solider.war.core.tools;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.ImageLayer;


/**
 * @author Paweł Kępa
 * Klasa bedzie w przyszłosci singletonem
 *
 */
public class MarkArea {
	private int clearX;
	private int clearY;
	private int clearWidthX;
	private int clearWidthY;
	
	CanvasImage	bgtile;	
	ImageLayer bg;
	Canvas canvas;
	
	public MarkArea(GroupLayer parentLayer) {
		this.bgtile = graphics().createImage(1000, 1000);
    	this.canvas = bgtile.canvas();
    	this.canvas.setFillColor(0xff87ceeb).setAlpha(0.5f);
    	this.bg = graphics().createImageLayer(bgtile);
    	this.bg.setWidth(1000);
    	this.bg.setHeight(1000);
    	
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
	
	public void clear(){
		canvas.clear();
	}
}
