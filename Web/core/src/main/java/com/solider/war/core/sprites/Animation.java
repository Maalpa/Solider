package com.solider.war.core.sprites;

import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.util.Callback;

import com.google.common.collect.Lists;
import com.solider.war.core.model.DestinationPoint;
import com.solider.war.core.model.MousePoint;
import com.solider.war.core.path.PathPoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Transform;


public abstract class Animation {
	
	protected float x;									// center position x
	protected float y; 									// center position y
	protected float imageX; 							// left Top position x
	protected float imageY; 							// left top position y 
	protected boolean moving;							// is animation moving on the map
	protected float angle;								// angle of sprite rotation
	protected Sprite sprite;					
	protected int spriteIndex = 0;						// index of rendering sprite
	protected boolean hasLoaded = false; 				// set to true when resources have loaded		
	protected boolean selected = false;					// is animation selected
	protected float width;								// width of sprite image i required for couting if object selected, working with imageX 
	protected float height;								// width of sprite image i required for couting if object selected, working with imageY 
	protected LinkedList<PathPoint> path;				// path of single point to destination point
	protected PathPoint destinationPoint = null;  		// Object destination point - it sets when object is selected and right mouse is pressed
	
	private int counting = 0;							// update sprite image every 4 iteration of  main loop	
	private MousePoint mousePoint = new MousePoint();
	
	
	public Animation(final GroupLayer layer, final float x, final float y, final String image, final String json ) {

		sprite = SpriteLoader.getSprite(image, json);
		this.x = x;
		this.y = y;

		sprite.addCallback(new Callback<Sprite>() {
			
			@Override
			public void onSuccess(Sprite sprite) {
				sprite.setSprite(spriteIndex);
				sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
				sprite.layer().setTranslation(x, y);
				layer.add(sprite.layer());
				hasLoaded = true;
			}
			
			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}
	
	public void update(int delta, MousePoint mousePoint) {	
		if (hasLoaded) {
			if(counting == 4) {
				spriteIndex = (spriteIndex + 1) % sprite.numSprites();
				sprite.setSprite(spriteIndex);
				counting = 0;
			}
			counting ++;
			
			this.x = sprite.layer().tx();
			this.y = sprite.layer().ty();
			
            this.x += - (Math.cos(this.angle) * (0.1f*delta));
            this.y += (Math.sin(this.angle) * (0.1f*delta));
			sprite.layer().setTranslation(this.x, this.y);
			
		    int	posX = (int) this.x;
		    int posY = (int) this.y;
		    
			if((posX <= (destinationPoint.getX()+2.00) && posX >= (destinationPoint.getX()-2.00)) 
				&& (posY <= (destinationPoint.getY()+2.00) && posY >= (destinationPoint.getY()-2.00)) )
			{	
				if(path != null && !path.isEmpty()) {
					setNextDestinationPoint();
				} else {
					moving = false;
				}
			}
		}
	}
	
	public void setRotationToMouse(MousePoint mousePoint) {
		if(selected) {
			float rotation = (float) (-(this.findAngle(mousePoint)) + Math.PI);
			this.angle =(float) (findAngle(mousePoint) + (Math.PI / 2.0f));
			sprite.layer().setRotation(rotation);
			moving = true;
		}
	}
	
	public boolean select(float mouseX , float mouseY, MarkArea markArea) {
		
		System.out.println("Selected mouse (" + mouseX + "," + mouseY + ")" );
		System.out.println("Selected tank (" + imageX + "," + imageY + ")" );
		System.out.println("Selected tank + delta (" + (this.x+this.width) + "," + (this.y+this.height) + ")" );
		System.out.println("Image size (" + (this.width) + "," + (this.height) + ")" );		
		System.out.println("Transform (" + Transform.getX() + "," + Transform.getY() + ")" );
		
		imageX = (float) ((this.x+Transform.getX()) - (width/2.0f));	// calculating where image starts by transforming
		imageY = (float) ((this.y+Transform.getY()) - (height/2.0f));	// calculating where image starts by transforming
		
		if( ((mouseX >= (imageX))  && (mouseX <= (imageX+this.width))) && (((mouseY) >= (imageY))  && (mouseY <= (imageY+this.height))) ) {
			System.out.println("Selected by click");
			selected = true;
			moving = false;
		} else {
			System.out.println("Not Selected by click");
			selected = false;
		}
		
		if( ((imageX >= (markArea.getX()))  && (mouseX <= (markArea.getX()+markArea.getWidth()))) && (((mouseY) >= (markArea.getY()))  && (mouseY <= (markArea.getY()+markArea.getHeight()))) ) {
			System.out.println("Selected by mark area ");			
			selected = true;
			moving = false;
		} 
		
		return selected;
	}
	
	private void fixDestinationPoint() {
		this.destinationPoint.setX((destinationPoint.getX()*30)+15);
		this.destinationPoint.setY((destinationPoint.getY()*30)+15);
	}
	
	public void setNextDestinationPoint() {
		this.destinationPoint = path.pollLast();
		if(this.destinationPoint != null ) {
			fixDestinationPoint();
			mousePoint.setPoint( destinationPoint.getX(), destinationPoint.getY());
			setRotationToMouse(mousePoint);
		}
	}
	
	public void setPath(LinkedList<PathPoint> path) {
		this.path = path;
		this.destinationPoint = path.pollLast(); // delete first one because this is point where animation is standing 
		setNextDestinationPoint();
	}
	
	public List<PathPoint> getPath() {
		return path;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public double angleTo(MousePoint target) {
		return Math.atan2(target.getX() - this.x, target.getY() -  this.y);	
	}
	
	public float findAngle(MousePoint mousePoint ) {
		return (float) angleTo(mousePoint);
	}
		
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
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

	public PathPoint getDestinationPoint() {
		return destinationPoint;
	}
	
}
