package com.solider.war.core.sprites;

import com.solider.war.core.IGameObject;
import com.solider.war.core.helpers.MapHelper;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.path.CalcPath;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import com.solider.war.core.tools.Transform;
import playn.core.GroupLayer;
import playn.core.util.Callback;
import java.util.LinkedList;
import java.util.List;
import static com.solider.war.core.Config.CENTER_FIELD_SIZE;
import static com.solider.war.core.Config.FIELD_SIZE;
import static playn.core.PlayN.log;

public abstract class Animation implements IGameObject {
	
	protected float x;									// center position x
	protected float y; 									// center position y		
	protected float imageX; 							// left Top position x
	protected float imageY; 							// left top position y 
	protected boolean moving;							// is animation moving on the map
	protected float angle;								// angle of sprite rotation
	protected float rotation;                           // rotation for sprite
	protected Sprite sprite;					        // sprite for images loaded from repo
	protected int spriteIndex = 0;						// index of rendering sprite
	protected boolean hasLoaded = false; 				// set to true when resources have loaded		
	protected boolean selected = false;					// is animation selected
	protected float width;								// width of sprite image i required for counting if object selected, working with imageX
	protected float height; 							// width of sprite image i required for counting if object selected, working with imageY
	protected LinkedList<MapPoint> path;				// path of single point to destination point
	protected MapPoint destinationPoint = null;  		// Object destination point - it sets when object is selected and right mouse is pressed
	protected CalcPath calcPath; 						// calculating path to destination point
	protected MapPoint prevPoint  = null;
	protected int counting = 0;							// update sprite image every 4 iteration of  main loop	
	private GPoint mousePoint = new GPoint();			


//***************************************************
//***************** Game Variables
//
	protected boolean fire;

	public Animation(final GroupLayer layer, final float x, final float y, final String image, final String json ) {

		this.sprite = SpriteLoader.getSprite(image, json);
		this.x = x;
		this.y = y;
		this.fire = false;
		this.calcPath = new CalcPath();

		sprite.addCallback(new Callback<Sprite>() {
			@Override
			public void onSuccess(Sprite spritee) {
				spritee.setSprite(spriteIndex);
				spritee.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
				spritee.layer().setTranslation(x, y);
				layer.add(sprite.layer());
				sprite = spritee;
				hasLoaded = true;
			}

			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}

	// Constructowr witch move object by moveX , moveY and rotate them by some angle
	public Animation(final GroupLayer layer, final float x, final float y, final float angle , final String image, final String json ) {

		this.sprite = SpriteLoader.getSprite(image, json);
		this.x = x;
		this.y = y;
		this.fire = false;
		this.calcPath = new CalcPath();

		sprite.addCallback(new Callback<Sprite>() {

			@Override
			public void onSuccess(Sprite sprite) {
			    float newX = 0.0f;
				float newY = 0.0f;
				sprite.setSprite(spriteIndex);
				sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
				newX = (float) (x + ( -(Math.cos(angle) * (40.1f))));
				newY = (float) (y + ( Math.sin(angle) * (40.1f)));
				sprite.layer().setTranslation(newX, newY);
				layer.add(sprite.layer());
				hasLoaded = true;
			}

			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}

	
	public void update(int delta, List<Animation> animations,  Animation animation, MarkArea markArea, MapPoint[][] map) {	
		if (hasLoaded) {

			//TODO remove counting and do this by some kind of time
			if(counting == 4) {
				spriteIndex = (spriteIndex + 1) % sprite.numSprites();
				sprite.setSprite(spriteIndex);
				counting = 0;
			}
			counting ++;

			this.x = sprite.layer().tx();
			this.y = sprite.layer().ty();
			
            this.x += -(Math.cos(this.angle) * (0.1f*delta));
            this.y += (Math.sin(this.angle) * (0.1f*delta));
			sprite.layer().setTranslation(this.x, this.y);
			
		    int	posX = (int) this.x;
		    int posY = (int) this.y;

			if((posX <= (destinationPoint.getX()+2.00) && posX >= (destinationPoint.getX()-2.00)) 
				&& (posY <= (destinationPoint.getY()+2.00) && posY >= (destinationPoint.getY()-2.00)))
			{
				MapPoint mapPoint = MapHelper.getPointOnMap(destinationPoint);
				if(path != null && !path.isEmpty()) {
					setNextDestinationPoint();
					prevPoint = mapPoint;
				} else {
					setPointMapOccupied(map, true, mapPoint);
					moving = false;
				}
			}
		}
	}
	
	public void setRotationToMouse(GPoint mousePoint) {
		this.rotation = (float) (-(this.findAngle(mousePoint)) + Math.PI);
		this.angle =(float) (findAngle(mousePoint) + (Math.PI / 2.0f));
		sprite.layer().setRotation(rotation);
		moving = true;
	}

	public boolean select(float mouseX , float mouseY) {

		this.selected = false;
		imageX =  ((this.x+Transform.getX()) - (width/2.0f));	// calculating where image starts by transforming
		imageY =  ((this.y+Transform.getY()) - (height/2.0f));	// calculating where image starts by transforming

		if( ((mouseX >= (imageX))  && (mouseX <= (imageX+this.width))) && (((mouseY) >= (imageY))  && (mouseY <= (imageY+this.height))) ) {
			this.selected = true;
			this.moving = false;
		}
		return selected;
	}
	
	private void fixDestinationPoint() {
		this.destinationPoint.setX((destinationPoint.getX()*FIELD_SIZE)+CENTER_FIELD_SIZE); 
		this.destinationPoint.setY((destinationPoint.getY()*FIELD_SIZE)+CENTER_FIELD_SIZE);
	}
	
	protected void setNextDestinationPoint() {
		this.destinationPoint = path.getLast();
		this.path.removeLast();
		fixDestinationPoint();
		mousePoint.setPoint( destinationPoint.getX(), destinationPoint.getY());
		setRotationToMouse(mousePoint);
	}
	
	protected double calcEnemyDistance(Animation enemy) {
		double distance; 
		distance = Math.sqrt( ((enemy.getX()-this.getX())*(enemy.getX()-this.getX())) + ((enemy.getY()-this.getY())*(enemy.getY()-this.getY())) );
		return distance;
	}
	
	public void setPath( Animation animation, MarkArea markArea, MapPoint[][] map) {

		clearBeforeSelectedDestinationPoint(map);
		this.path = this.calcPath.calcPath(animation, markArea, map);

		if(!path.isEmpty()) {
			MapPoint mapPoint = path.getFirst();
			setPointMapOccupied(map, true, mapPoint);
		}
		if(this.path.isEmpty()) {
			return;
		} else {
			if(prevPoint != null ) {
				setPointMapOccupied(map, false, destinationPoint);
			}
			destinationPoint = path.getLast(); // delete first one because this is point where animation is standing
			path.removeLast();
			setNextDestinationPoint();
		}
	}

	private void clearBeforeSelectedDestinationPoint(MapPoint[][] map){
		if(path != null && !path.isEmpty())
			setPointMapOccupied(map, false, path.getFirst());
	}
	
//*********************************************************
//******************** Abstract functions

	public abstract void fire( int delta );
	public abstract boolean isInRange( Animation enemy );
	public abstract void setPointMapOccupied(MapPoint [][] map, boolean isOccupide, MapPoint point);
	
	
//********************************************************
//******************* Getters and Setters
	
	public List<MapPoint> getPath() {
		return path;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public double angleTo(GPoint target) {
		return Math.atan2(target.getX() - this.x, target.getY() -  this.y);	
	}
	
	public float findAngle(GPoint mousePoint ) {
		return (float) angleTo(mousePoint);
	}
		
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		this.sprite.layer().setTx(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		this.sprite.layer().setTy(y);
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public boolean isHasLoaded() {
		return hasLoaded;
	}

	public void setHasLoaded(boolean hasLoaded) {
		this.hasLoaded = hasLoaded;
	}

	public int getCounting() {
		return counting;
	}

	public void setCounting(int counting) {
		this.counting = counting;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public MapPoint getDestinationPoint() {
		return destinationPoint;
	}
	
	public void setDestinationPoint(MapPoint destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public float getImageX() {
		return imageX;
	}

	public void setImageX(float imageX) {
		this.imageX = imageX;
	}

	public float getImageY() {
		return imageY;
	}

	public void setImageY(float imageY) {
		this.imageY = imageY;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		this.sprite.layer().setRotation(rotation);
	}
	
	public void setObjectRotation(float rotation) {
		this.sprite.layer().setRotation(rotation);
	}
	
	public void setPosition(float x, float y) {
		this.sprite.layer().setTranslation(x,y);
		this.x = x;
		this.y = y;
	}
	
	public boolean isFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}
}
