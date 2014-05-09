package com.solider.war.model.sprites;

import static playn.core.PlayN.log;
import playn.core.GroupLayer;
import playn.core.util.Callback;

import com.solider.war.core.model.MousePoint;
import com.solider.war.core.sprites.Sprite;
import com.solider.war.core.sprites.SpriteLoader;

import org.jbox2d.dynamics.contacts.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.common.*;
import org.jbox2d.collision.*;
import org.jbox2d.dynamics.joints.*;

public abstract class Animation {
	
	private float x; 
	private float y; 
	private boolean moving;
	private float angle;
	private Sprite sprite;
	private int spriteIndex = 0;
	private boolean hasLoaded = false; // set to true when resources have loaded
	// and we can update	
	int counting = 0;
	
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
			
			if((posX <= (mousePoint.getX()+2.00) && posX >= (mousePoint.getX()-2.00)) 
				&& (posY <= (mousePoint.getY()+2.00) && posY >= (mousePoint.getY()-2.00)) )
			{
				sprite.layer().setTranslation(mousePoint.getX(), mousePoint.getY());
				moving = false;
			}
		}
	}
	
	public void setRotationToMouse(MousePoint mousePoint) {
		float rotation = (float) (-(this.findAngle(mousePoint)) + Math.PI);
		this.angle =(float) (findAngle(mousePoint) + (Math.PI / 2.0f));
		sprite.layer().setRotation(rotation);
		moving = true;
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
}
