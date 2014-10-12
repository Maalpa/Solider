package com.solider.war.core.tools;

import com.solider.war.core.sprites.Animation;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.ImageLayer;

import java.util.List;

import static playn.core.PlayN.graphics;


/**
 * @author Paweł Kępa
 * 
 */
public class MarkArea {
	
	private static final int WIDTH = 1021;
	private static final int HEIGHT = 1021;
	private static final float ALPHA = 0.5f;
	private static final int COLOR = 0xff87ceeb;
	private static final int COLOR_RED = 0xff87eeee;
	
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
	private CanvasImage	bgtile2;	
	private ImageLayer bg;
	private ImageLayer bg2;
	private Canvas canvas;
	
	private Canvas canvas2;
	
	public MarkArea(GroupLayer parentLayer) {
		this.bgtile = graphics().createImage(WIDTH, HEIGHT);
    	this.canvas = bgtile.canvas();
    	this.canvas.setFillColor(COLOR).setAlpha(ALPHA);
    	this.bg = graphics().createImageLayer(bgtile);
    	this.bg.setWidth(WIDTH);
    	this.bg.setHeight(HEIGHT);
    	
    	// secound canvas for wall rendering
    	this.bgtile2 = graphics().createImage(WIDTH, HEIGHT);
    	canvas2 = bgtile2.canvas();
    	this.canvas2.setFillColor(COLOR_RED).setAlpha(ALPHA);
    	this.bg2 = graphics().createImageLayer(bgtile2);
    	this.bg2.setWidth(WIDTH);
    	this.bg2.setHeight(HEIGHT);
    	
    	// Add to main layer 
    	parentLayer.add(bg);
    	parentLayer.add(bg2);
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
	
	public void markPathOnClick(int x, int y, int width, int height) {
		this.canvas2.fillRect( x , y, width, height);
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
	
	public void intersects(List<Animation> animations) {
		for(Animation animation : animations) {
			if(  (animation.getX()>=this.clearX && animation.getX() <= (this.clearX + this.clearWidthX))  && 
					(animation.getY()>=this.clearY && animation.getY() <= (this.clearY + this.clearWidthY)) ) {
				animation.setSelected(true);
				System.out.println(animation.toString());
			}
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
