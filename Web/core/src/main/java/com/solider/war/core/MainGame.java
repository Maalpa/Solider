package com.solider.war.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.kerberos.KerberosKey;

import org.w3c.dom.css.RGBColor;

import com.solider.war.core.model.MousePoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.model.sprites.impl.Solider;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.*;
import playn.core.Keyboard.TypedEvent;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.Pointer.Event;
import playn.core.canvas.GroupLayerCanvas;
import static playn.core.PlayN.*;

public class MainGame extends Game.Default {
		
	private GroupLayer layer;
	private  GroupLayer soliderLayer;
	private List<Solider> soliders = new ArrayList<Solider>(0);
	private MousePoint mousePoint = new MousePoint();
	
	ImageLayer bgLayer;	
	MarkArea markArea;
	
	float transformX = 0.0f;
	float transformY = 0.0f;
	
	MousePoint startPoint = new MousePoint();
	MousePoint endPoint = new MousePoint();

	public MainGame() {
		super(25); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
		
		Image bgImage = assets().getImage("sprites/bg.png");
		

		// create a group layer to hold everything
		layer = graphics().createGroupLayer();
		bgLayer = graphics().createImageLayer(bgImage);
		soliderLayer = graphics().createGroupLayer();
		
		
		
		graphics().rootLayer().add(layer);
		layer.add(bgLayer);
		layer.add(soliderLayer);
		
		markArea = new MarkArea(layer);
		
		
		// Add one solider sprite  to game the game
		addSolider(graphics().width() / 2, graphics().height() / 2);
		
		
		// add a listener for pointer (mouse, touch) input
		PlayN.mouse().setListener(new Mouse.Adapter() {

			 	boolean mouseDown = false;
			 	boolean mouseLeftButton = false;			 	
			 	
			    @Override
			    public void onMouseDown(ButtonEvent event) {
			    	startPoint.setPoint(event.x()+(-transformX), event.y()+(-transformY));			    	
			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {
			    		mouseDown = true;
			    	}
			    	
			    	if( event.button() ==  Mouse.BUTTON_LEFT ) {
			    		mouseLeftButton = true;
			    	}
			    }
			    
			    @Override
			    public void onMouseUp(ButtonEvent event) {
			    	markArea.clear();
			    	if( event.button() ==  Mouse.BUTTON_LEFT ) {
			    		mouseLeftButton = false;
				    	mousePoint.setPoint(event.x()+(-transformX), event.y()+(-transformY));
						for (Solider solider : soliders) {
							solider.setRotationToMouse(mousePoint);
						}
			    	}
					if( event.button() ==  Mouse.BUTTON_RIGHT ) {
						mouseDown = false; 
					}
			    }
			    
			    @Override
			    public void onMouseMove(MotionEvent event) {
			    	
			    	if(mouseLeftButton) {
			    		markArea.startMarking( (int) (startPoint.getX()+(-transformX)), (int) (startPoint.getY()+(-transformY)), 
				    			(int) ((event.x()-startPoint.getX())+(-transformX)), (int)((event.y()-startPoint.getY())+(-transformY)));	
			    	}
			    	
			    	if(mouseDown) {    	
				    	endPoint.setPoint(event.x(), event.y());
						transformX = endPoint.getX() -startPoint.getX();
						transformY = endPoint.getY() -startPoint.getY();
						layer.setTranslation(transformX, transformY);
			    	}
			    }
			    
			    @Override
			    public void onMouseWheelScroll(WheelEvent event) { 
			    }
		});
		
	    PlayN.keyboard().setListener(new Keyboard.Adapter() {
	    	@Override
	        public void onKeyDown(Keyboard.Event event) {
	        	
	        }

	        @Override
	        public void onKeyUp(Keyboard.Event event) {
	          
	        }
	     });
	}

	@Override
	public void update(int delta) {
		for (Solider solider : soliders) {
			if(solider.isMoving()) solider.update(delta, mousePoint);
		}
	}
	
	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
	}
	
	private void addSolider(float x, float y) {
		Solider solider = new Solider(soliderLayer, x, y);
		soliders.add(solider);
	}
}
