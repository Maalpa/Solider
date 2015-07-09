package com.solider.war.core;

import com.solider.war.core.helpers.MapHelper;
import com.solider.war.core.helpers.PositionType;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.sprites.StaticObjectsModel.Bags;
import com.solider.war.core.sprites.StaticObjectsModel.Baricade;
import com.solider.war.core.sprites.StaticObjectsModel.Barrack;
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
import java.util.concurrent.CopyOnWriteArrayList;

import static com.solider.war.core.Config.*;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import com.solider.war.core.SpriteAssets.*;


public class MainGame extends Game.Default {
	
	public final  MapPoint[][] map =  new MapPoint[PATH_MAP_SIZE][PATH_MAP_SIZE];   // game map:  1 - occupide field, 0 - free field
	private boolean MOUSE_RIGHT_BUTTON_DOWN = false;                                // flag for mouse right button
 	private boolean MOUSE_LEFT_BUTTON_DOWN = false;                                 // flag form mose left button
	private boolean MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN = false;               // mose moving with right button - is using for marking places and selecting animations
 	private boolean KEY_CTRL_DOWN = false;                                          // TODO change this in ALT !!!
	private GroupLayer animationLayer_2RD;                                          // layer for static object e.g. Buildings
	private GroupLayer animationLayer_3RD;                                          // for animations
	private GroupLayer animationLayer_4RD;                                          // for parts placed in an animation e.g. Barrel
	private GroupLayer animationLayer_5RD;
	private List<Animation> animations = new CopyOnWriteArrayList<Animation>();             // list of animations
	private List<StaticObject> staticObjects = new CopyOnWriteArrayList<StaticObject>();       // static objects
	private ImageLayer bgLayer;                                                                  // background image layer
	private MarkArea markArea;                                                                   // class using to selecting animations for current  player
	private Tank tank;                                                                           // will be removed
	private GroupLayer layer;                                                                    // Main layer in the game !!!
	private Blow blow;                                                                           // will be removed

	public MainGame() {
		super(30); // call update every 33ms (30 times per second)
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
		animationLayer_4RD = graphics().createGroupLayer();
		animationLayer_5RD = graphics().createGroupLayer();

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
		layer.add(bgLayer);             // BACKGROUND
		layer.add(animationLayer_2RD);
		layer.add(animationLayer_3RD);
		layer.add(animationLayer_4RD);
		layer.add(animationLayer_5RD);


		// Adding grid !!!
		if( DRAW_GRID ) {
			layer.add(bg);
		}
		// Static Objects !!!!
		markArea = new MarkArea(layer);
		addBarrack(300, 300, markArea);
		addBaricade(400, 400, SpriteAssets.BARICADE_HORIZONTAL, markArea);
		addBaricade(450, 450, SpriteAssets.BARICADE_VERTICAL, markArea);

		// Add one solider sprite  to game the game
		addSolider(100, 250, SpriteAssets.RED_SOLIDER);
		int a = 0;
		int b;
		for(int i = 0; i < 20; i++) {
			a=a+10;
			b=100;
			addSolider(a,b, SpriteAssets.GREEN_SOLIDER);
		}

		a=0;
		for(int i = 0; i < 20; i++) {
			a=a+10;
			b=200;
			addSolider(a,b, SpriteAssets.RED_SOLIDER);
		}

		addTank(50,50);

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
					    GPoint gamePoint = Point.transformPoint(event.x(),event.y());
//					    addBlow(gamePoint.getX(), gamePoint.getY());
			    	}

			    	if( event.button() ==  Mouse.BUTTON_LEFT ) {
			    		MOUSE_LEFT_BUTTON_DOWN = true;
			    		if(KEY_CTRL_DOWN == true ) {
			    			int corX = (int) (Point.getTransformStartPoint().getX()/FIELD_SIZE);
			    			int corY = (int) (Point.getTransformStartPoint().getY()/FIELD_SIZE);
							// TODO improve blow
                            markArea.markPathOnClick(corX*FIELD_SIZE, corY*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
			    		    map[corX][corY].setOccupied(true);

						    for(StaticObject staticObject : staticObjects) {
							    staticObject.select(event.x(), event.y());
							    if(staticObject.isSelected()) {
							        staticObject.clearOccupideMapFields(map);
							    }
						    }
			    		}
			    	}
			    }
			    @Override
			    public void onMouseMove(MotionEvent event) {
				    Point.setMousePoint(event.x(), event.y());
			    	if(MOUSE_LEFT_BUTTON_DOWN) {
					    if( !KEY_CTRL_DOWN ) {
			    			markArea.mark(Point.getTransformStartPoint().getX(),
			    						  Point.getTransformStartPoint().getY(),
			    						  event.x()+(-Transform.getX()),
			    						  event.y()+(-Transform.getY()) );
			    			Point.setEndPoint(event.x(), event.y());
					    }
					    if(KEY_CTRL_DOWN) {
						    for(StaticObject staticObject : staticObjects) {
							    if(staticObject.isSelected()) {
								    int inX = (int) ((event.x()+(-Transform.getX()))/FIELD_SIZE);
								    int inY = (int) ((event.y()+(-Transform.getY()))/FIELD_SIZE);
								    float posX = ((inX*FIELD_SIZE)+(FIELD_SIZE/2));
								    float posY = ((inY*FIELD_SIZE)+(FIELD_SIZE/2));
								    staticObject.moveObject(posX, posY);
							    }
						    }
					    }
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
				    // calc colisions for static objects...
				    for(StaticObject staticObject : staticObjects) {
					    staticObject.calcColision(map, markArea);
					    staticObject.setSelected(false);
				    }

			    	if( event.button() ==  Mouse.BUTTON_RIGHT ) {
			    		MOUSE_RIGHT_BUTTON_DOWN = false;
			    		Point.setMousePoint(event.x(), event.y());
			    		if(!MOUSE_HAVE_MOVING_WITH_RIGHT_BITTON_DOWN) {
			    			for (Animation animation : animations) {
								if(animation.isSelected()) {
									MapPoint mapPoint = MapHelper.getPointOnMap(new MapPoint( (int) animation.getX(), (int) animation.getY()));
									animation.setPointMapOccupied(map, false, mapPoint);
									animation.setPath(animation, markArea, map);
								}
							}
			    		}
			    	}
					if( event.button() ==  Mouse.BUTTON_LEFT  ) {

						System.out.println("++++++++++++++++++++++++++LEFT+++++++++++++++++++++++++++++++");

						MOUSE_LEFT_BUTTON_DOWN = false;
						System.out.println("ANIMATIONS lenght" + animations.size());
						int iterated = 0;
						for (Animation animation : animations) {
							animation.setSelected(false);
							animation.select(event.x(), event.y());
							iterated++;
						}
						System.out.println("Iterated Animations = " + iterated);
					}

					if(DRAW_OCCUPIDE_FIELDS)  drawOccupideFields();

				    markArea.intersects(animations);
				    markArea.clear();
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
			    switch (event.key()) {
				    case ALT: {
					    KEY_CTRL_DOWN = true;
					   break;
				    }
				    case SPACE: {
					    addBaricade(Point.getTransformMousePoint().getX(), Point.getTransformMousePoint().getY(), SpriteAssets.BARICADE_HORIZONTAL, markArea);
					    break;
				    }
				    case A: {
					    addBaricade(Point.getTransformMousePoint().getX(), Point.getTransformMousePoint().getY(), SpriteAssets.BARICADE_VERTICAL, markArea);
				        break;
				    }
			    }
	        }
	        @Override
	        public void onKeyUp(Keyboard.Event event) {
		        switch (event.key()) {
			        case ALT: {
				        KEY_CTRL_DOWN = false;
				        break;
			        }
		        }
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
				animation.fire(delta);
			}

			if( animation instanceof Blow ) {
			 	if(animation.isFire()) {
					animation.update(delta, animations, animation, markArea, map);
				} else {
					try {
						animation.getSprite().layer().destroy();
						animations.remove(animation);
					} catch (UnsupportedOperationException ex ) {
						System.out.println("Bląd w trakcie usuwania " + ex.getMessage());
					}
				}
			}

		}
		// send pack of data to the server
	}

	// helper methot to show if field is occupied
	private void drawOccupideFields(){
		markArea.clear();
		for(int i=0; i< map.length; i++) {
			for(int j=0;j<map.length;j++) {
				if(map[i][j].isOccupied()) {
					markArea.markPathOnClick(i*FIELD_SIZE, j*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
				}
			}
		}
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
	}
	
	private void addSolider(float x, float y, SpriteAssets assets) {
		Solider solider = new Solider(x, y, assets, animationLayer_3RD);
		animations.add(solider);
	}

	private void addTank(float x, float y) {
		this.tank = new Tank(x, y, animationLayer_3RD, animationLayer_4RD);
		animations.add(tank);
	}

	private void addBlow(float x, float y) {
		blow = new Blow(x, y, animationLayer_3RD);
		animations.add(blow);
		blow.setMoving(true);
		blow.setFire(true);
	}

	private void addBags(float x, float y, MarkArea markArea) {
		staticObjects.add(new Bags(x, y, map, markArea, animationLayer_2RD));
	}

	private void addBarrack(float x, float y, MarkArea markArea) {
		staticObjects.add( new Barrack( x, y, map, markArea, PositionType.HORIZONTAL, animationLayer_2RD) );
	}

	private void addBaricade(float x, float y, SpriteAssets assets , MarkArea markArea) {
		staticObjects.add( new Baricade( x, y, map, markArea, assets, animationLayer_2RD) );
	}

	private void addTower(float x, float y, SpriteAssets assets , MarkArea markArea) {
		staticObjects.add( new Baricade( x, y, map, markArea, assets, animationLayer_2RD) );
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

