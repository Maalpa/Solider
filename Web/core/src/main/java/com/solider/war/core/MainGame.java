package com.solider.war.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.kerberos.KerberosKey;

import org.w3c.dom.css.RGBColor;

import com.solider.war.core.model.MousePoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import com.solider.war.core.tools.Transform;
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
	
	ImageLayer bgLayer;	
	MarkArea markArea;

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
		
	    // draw a soothing flat background
	    CanvasImage bgtile = graphics().createImage(30, 30);
	    bgtile.canvas().setFillColor(0xFFCCCCCC);
//	    bgtile.canvas().fillRect(0, 0, 64, 64);
	    bgtile.canvas().setStrokeColor(0xFFFFFFFF);
	    bgtile.canvas().strokeRect(0, 0, 30, 30);
	    bgtile.setRepeat(true, true);

	    ImageLayer bg = graphics().createImageLayer(bgtile);
	    bg.setWidth(1021);
	    bg.setHeight(1021);
	    
		graphics().rootLayer().add(layer);
		//layer.add(bgLayer);  // BACKGROUND
		
		layer.add(bg);
		markArea = new MarkArea(layer);
		layer.add(soliderLayer);
		
		// Add one solider sprite  to game the game
		addSolider(graphics().width() / 2, graphics().height() / 2);
		
		// add a listener for pointer (mouse, touch) input
		PlayN.mouse().setListener(new Mouse.Adapter() {
				
			 	boolean mouseDown = false;
			 	boolean mouseLeftButton = false;
			 	
			    @Override
			    public void onMouseDown(ButtonEvent event) {
			    	Point.setStartPoint(event.x(), event.y());		    	
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
			    		Point.setMousePoint(event.x(), event.y());
			    		System.out.println("Mouse Point ("+Point.getTransformMousePoint().getX()+","+ Point.getTransformMousePoint().getY()+")");
			    		int corX = (int) (Point.getTransformMousePoint().getX()/30);
			    		int corY = (int) (Point.getTransformMousePoint().getY()/30);
			    		System.out.println("Cords ("+corX +","+corY+")");
			    		Point.setSoliderPoint((corX*30)+15, (corY*30)+15);
			    		System.out.println("soliderPoint ("+Point.getSoliderPoint().getX() +","+Point.getSoliderPoint().getY()+")");
						for (Solider solider : soliders) {
							solider.setRotationToMouse(Point.getSoliderPoint());
						}
			    	}
			    	
					if( event.button() ==  Mouse.BUTTON_RIGHT ) {
						mouseDown = false; 
					}
			    }
			    
			    @Override
			    public void onMouseMove(MotionEvent event) {
			    	
			    	if(mouseLeftButton) {
			    			markArea.mark(Point.getTransformStartPoint().getX(),
			    						  Point.getTransformStartPoint().getY(),
			    						  event.x()+(-Transform.getX()), 
			    						  event.y()+(-Transform.getY()) );
			    	}
			    	
			    	if(mouseDown) {
			    		Point.setEndPoint(event.x(), event.y());
						Transform.setTransform(event.x() - Point.getTransformStartPoint().getX(), event.y() - Point.getTransformStartPoint().getY());
						layer.setTranslation(Transform.getX(), Transform.getY());
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
			if(solider.isMoving()) solider.update(delta, Point.getSoliderPoint());
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
