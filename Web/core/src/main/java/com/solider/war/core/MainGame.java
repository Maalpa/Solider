package com.solider.war.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.kerberos.KerberosKey;

import org.w3c.dom.css.RGBColor;

import com.solider.war.core.model.DestinationPoint;
import com.solider.war.core.model.GameMap;
import com.solider.war.core.model.MousePoint;
import com.solider.war.core.path.CalcPath;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.sprites.model.Solider;
import com.solider.war.core.sprites.model.Tank;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import com.solider.war.core.tools.Transform;

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
	
	private boolean MOUSE_RIGHT_BUTTON_DOWN = false;
 	private boolean MOUSE_LEFT_BUTTON_DOWN = false;
 	private boolean MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = false;
 	private final int MAP_SIZE = 1021;
 	
	private GroupLayer layer;
	private GroupLayer animationLayer;
	private GameMap gameMap = new GameMap(MAP_SIZE, MAP_SIZE); 
	private List<Animation> animations = new ArrayList<Animation>();
	CalcPath calcPath; 
	
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
		animationLayer = graphics().createGroupLayer();
		
	    // draw a soothing flat background
	    CanvasImage bgtile = graphics().createImage(30, 30);
	    bgtile.canvas().setFillColor(0xFFCCCCCC);
	    bgtile.canvas().setStrokeColor(0xFFFFFFFF);
	    bgtile.canvas().strokeRect(0, 0, 30, 30);
	    bgtile.setRepeat(true, true);

	    ImageLayer bg = graphics().createImageLayer(bgtile);
	    bg.setWidth(MAP_SIZE);
	    bg.setHeight(MAP_SIZE);
	    
	    
		graphics().rootLayer().add(layer);
//		layer.add(bgLayer);  // BACKGROUND
		
		layer.add(bg);
		markArea = new MarkArea(layer);
		layer.add(animationLayer);
		
		// Add one solider sprite  to game the game
		addSolider(graphics().width() / 2, graphics().height() / 2);
		
		addTank(50,50);
		
		// add a listener for pointer (mouse, touch) input
		PlayN.mouse().setListener(new Mouse.Adapter() {
			
			    @Override
			    public void onMouseDown(ButtonEvent event) {
			    	Point.setStartPoint(event.x(), event.y());
			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {
			    		MOUSE_RIGHT_BUTTON_DOWN = true;
			    	}
			    	
			    	if( event.button() ==  Mouse.BUTTON_LEFT ) {
			    		MOUSE_LEFT_BUTTON_DOWN = true;
			    	}
			    }
			        
			    @Override
			    public void onMouseMove(MotionEvent event) {
			    
			    	if(MOUSE_LEFT_BUTTON_DOWN) {
			    			markArea.mark(Point.getTransformStartPoint().getX(),
			    						  Point.getTransformStartPoint().getY(),
			    						  event.x()+(-Transform.getX()), 
			    						  event.y()+(-Transform.getY()) );
			    			
			    			Point.setEndPoint(event.x(), event.y());
			    	}
			    	
			    	if(MOUSE_RIGHT_BUTTON_DOWN) {
			    		
			    		MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = true;
						Transform.setTransform(event.x() - Point.getTransformStartPoint().getX(), event.y() - Point.getTransformStartPoint().getY());
						layer.setTranslation(Transform.getX(), Transform.getY());
						
			    	} else {
			    		MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = false;
			    	}
			    	
			    	if( MOUSE_LEFT_BUTTON_DOWN  ) {						
						for(Animation animation : animations) {
							animation.select(event.x(), event.y(), markArea);
						}
					}
			    }
			    
			    @Override
			    public void onMouseUp(ButtonEvent event) {
			    	
			    	markArea.clear();
			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {
			    		MOUSE_RIGHT_BUTTON_DOWN = false;
			    		Point.setMousePoint(event.x(), event.y());
			    		
			    		System.out.println("Mouse Point ("+Point.getTransformMousePoint().getX()+","+ Point.getTransformMousePoint().getY()+")");
			    		int corX = (int) (Point.getTransformMousePoint().getX()/30);
			    		int corY = (int) (Point.getTransformMousePoint().getY()/30);
			    		System.out.println("Cords ("+corX +","+corY+")");
			    		
			    		Point.setSoliderPoint((corX*30)+15, (corY*30)+15);   // this is important !!!! 
			    		
			    		System.out.println("soliderPoint ("+Point.getSoliderPoint().getX() +","+Point.getSoliderPoint().getY()+")");
			    		
			    		if(!MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN) {
				    		for (Animation animation : animations) {
				    			animation.setRotationToMouse(Point.getSoliderPoint());
								if(animation.isSelected()) {
									animation.setDestinationPoint(new DestinationPoint((corX*30)+15, (corY*30)+15));
									calcPath = new CalcPath(animation, MAP_SIZE);
									calcPath.calcPath();
								}
							}
			    		}
			    		
			    	}
			    	
					if( event.button() ==  Mouse.BUTTON_LEFT  ) {
						MOUSE_LEFT_BUTTON_DOWN = false;
						for(Animation animation : animations) {
							animation.select(event.x(), event.y(), markArea);
						}
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
		for (Animation animation : animations) {
			if(animation.isMoving()) animation.update(delta, Point.getSoliderPoint());
		}
	}
	
	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
	}
	
	private void addSolider(float x, float y) {
		Solider solider = new Solider(animationLayer, x, y);	
		animations.add(solider);
	}
	
	private void addTank(float x, float y) {
		Tank tank = new Tank(animationLayer, x, y);
		animations.add(tank);
	}
}
