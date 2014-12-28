package com.solider.war.core;

import com.solider.war.core.helpers.MapHelper;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.sprites.StaticObjectsModel.Bags;
import com.solider.war.core.sprites.model.Blow;
import com.solider.war.core.sprites.model.Solider;
import com.solider.war.core.sprites.model.Tank;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import com.solider.war.core.tools.Transform;
import playn.core.*;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;

import java.util.ArrayList;
import java.util.List;

import static com.solider.war.core.Config.*;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class MainGame extends Game.Default {
	
	public final  MapPoint[][] map =  new MapPoint[PATH_MAP_SIZE][PATH_MAP_SIZE];
	private boolean MOUSE_RIGHT_BUTTON_DOWN = false;
 	private boolean MOUSE_LEFT_BUTTON_DOWN = false;
	private boolean MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = false;
 	private boolean KEY_CTRL_DOWN = false;
	private GroupLayer animationLayer_2RD;
	private GroupLayer animationLayer_3RD;
	private List<Animation> animations = new ArrayList<Animation>();
	private List<StaticObject> staticObjects = new ArrayList<StaticObject>();
	private ImageLayer bgLayer;
	private MarkArea markArea;
	private Tank tank;
	private GroupLayer layer;
	private Blow blow;

	public MainGame() {
		super(16); // call update every 33ms (30 times per second)
	}

	@Override
	public void init() {
		// fill table with value equals -1
		for(int i = 0; i< map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				map[i][j] = new MapPoint(i, j);
				map[i][j].setValue(-1);
				map[i][j].setVisited(false);
			}
		}

		Image bgImage = assets().getImage("sprites/map.png");

		// create a group layer to hold everything
		layer = graphics().createGroupLayer();
		bgLayer = graphics().createImageLayer(bgImage);
		animationLayer_2RD = graphics().createGroupLayer();
		animationLayer_3RD = graphics().createGroupLayer();

	    // draw a soothing flat background
	    CanvasImage bgtile = graphics().createImage(FIELD_SIZE, FIELD_SIZE);
	    bgtile.canvas().setFillColor(0xFFCCCCCC);
	    bgtile.canvas().setStrokeColor(0xFFFFFFFF);
	    bgtile.canvas().strokeRect(0, 0, FIELD_SIZE, FIELD_SIZE);
	    bgtile.setRepeat(true, true);
	    ImageLayer bg = graphics().createImageLayer(bgtile);

	    bg.setWidth(MAP_SIZE);
	    bg.setHeight(MAP_SIZE);

		graphics().rootLayer().add(layer);
		layer.add(bgLayer);  // BACKGROUND
		layer.add(animationLayer_2RD);
		layer.add(animationLayer_3RD);


		// Add one solider sprite  to game the game
		addSolider(100, 250, GameStatics.RED_SOLIDER);
		int a = 0;
		int b;
		for(int i = 0; i < 20; i++) {
			a=a+10;
			b=100;
			addSolider(a,b, GameStatics.GREEN_SOLIDER);
		}

		a=0;
		for(int i = 0; i < 20; i++) {
			a=a+10;
			b=200;
			addSolider(a,b, GameStatics.RED_SOLIDER);
		}

		// Adding grid !!!
		if( DRAW_GRID ) {
			layer.add(bg);
		}

		markArea = new MarkArea(layer);
		addTank(50,50);
		addBags(190,195, markArea);

//		addBlow(300, 300);


//////////////////////////////////////////////////////////////////////////
//***********************************************************************
//			MOUSE
//***********************************************************************
		PlayN.mouse().setListener(new Mouse.Adapter() {
			    @Override
			    public void onMouseDown(ButtonEvent event) {
			    	Point.setStartPoint(event.x(), event.y());
			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {
			    		MOUSE_RIGHT_BUTTON_DOWN = true;
//					    GPoint gamePoint = Point.transformPoint(event.x(),event.y());
//					    addBlow(gamePoint.getX(), gamePoint.getY());
			    	}

			    	if( event.button() ==  Mouse.BUTTON_LEFT ) {
			    		MOUSE_LEFT_BUTTON_DOWN = true;
			    		if(KEY_CTRL_DOWN == true ) {
			    			int corX =(int) (Point.getTransformStartPoint().getX()/FIELD_SIZE);
			    			int corY =(int) (Point.getTransformStartPoint().getY()/FIELD_SIZE);
			    			markArea.markPathOnClick(corX*FIELD_SIZE, corY*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
			    			map[corX][corY].setOccupied(true);
			    		}
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
			    		checkMapBoundariesForCamera(event);
			    	} else {
			    		MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = false;
			    	}
			    	if( MOUSE_LEFT_BUTTON_DOWN  ) {

					}
			    }

			    @Override
			    public void onMouseUp(ButtonEvent event) {

			    	markArea.intersects(animations);
			    	markArea.clear( );
			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {

			    		MOUSE_RIGHT_BUTTON_DOWN = false;
			    		Point.setMousePoint(event.x(), event.y());
			    		if(!MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN) {

			    			for (Animation animation : animations) {
								if(animation.isSelected()) {
									MapPoint mapPoint = MapHelper.getPointOnMap(new MapPoint( (int) animation.getX(), (int) animation.getY()));
									map[mapPoint.getX()][mapPoint.getY()].setOccupied(false);
								}
							}

				    		for (Animation animation : animations) {
								if(animation.isSelected()) {
									animation.setPath(animation, markArea, map);
								}
							}
			    		}
			    	}

					if( event.button() ==  Mouse.BUTTON_LEFT  ) {
						MOUSE_LEFT_BUTTON_DOWN = false;
						for(Animation animation : animations) {
							animation.setSelected(false);
						}
						for(Animation animation : animations) {
							animation.select(event.x(), event.y());
						}
					}
			    }

			    @Override
			    public void onMouseWheelScroll(WheelEvent event) {
			    }
		});

//////////////////////////////////////////////////////////////////////////
//***********************************************************************
//			KEYBOARD
//***********************************************************************

	    PlayN.keyboard().setListener(new Keyboard.Adapter() {
	    	@Override
	        public void onKeyDown(Keyboard.Event event) {
	    		KEY_CTRL_DOWN = true;
	        }
	        @Override
	        public void onKeyUp(Keyboard.Event event) {
	        	KEY_CTRL_DOWN = false;
	        }
	     });

	}

///////////////////////////////////////////////////////////////////////////
//*************************************************************************
// 			UPDATE AND  FUNCTIONS
//*************************************************************************
	@Override
	public void update(int delta) {
		for (Animation animation : animations) {
 			if (animation.isMoving()) animation.update(delta, animations, animation, markArea, map);
			if (animation instanceof Tank) {
				((Tank) animation).updateBarrel(delta, animations);
			}
			animation.fire();
			if(animation instanceof Blow) {
			 	if(animation.isFire()) {
					animation.update(delta, animations, animation, markArea, map);
				} else {
					try {
						animationLayer_2RD.remove(animation.getSprite().layer());
						animations.remove(animation);
					} catch (UnsupportedOperationException ex ) {
						System.out.println("Blad w trakcie usuwania " + ex.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
	}
	
	private void addSolider(float x, float y, String imagePath) {
		Solider solider = new Solider(x, y, imagePath, animationLayer_2RD);
		animations.add(solider);
	}
	
	private void addTank(float x, float y) {
		this.tank = new Tank(x, y, animationLayer_2RD, animationLayer_3RD);
		animations.add(tank);
	}

	private void addBlow(float x, float y) {
		blow = new Blow(x, y, animationLayer_2RD);
		animations.add(blow);
		blow.setMoving(true);
		blow.setFire(true);
	}

	private void addBags(float x, float y, MarkArea markArea) {
		staticObjects.add(new Bags(x, y, map, markArea, animationLayer_2RD));
	}
	
	// checking Boundaries if camera doesn't come out off map size
	private void checkMapBoundariesForCamera(MotionEvent event ) {
		
		float tempTransformX = event.x() - Point.getTransformStartPoint().getX();
		float tempTransformY = event.y() - Point.getTransformStartPoint().getY();
		
		if(tempTransformX <= 0 &&  (tempTransformX - WINDOW_WIDTH) >= (-MAP_SIZE) ) {
			Transform.setX(tempTransformX);
			layer.setTx(Transform.getX());
		}
		
		if(tempTransformY <= 0 && (tempTransformY - WINDOW_HEIGHT) >= (-MAP_SIZE) ) {
			Transform.setY(tempTransformY);
			layer.setTy(tempTransformY);
		}
	}
}
