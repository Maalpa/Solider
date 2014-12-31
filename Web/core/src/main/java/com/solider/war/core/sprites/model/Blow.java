package com.solider.war.core.sprites.model;

import com.solider.war.core.helpers.MapHelper;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

import java.io.Serializable;
import java.util.List;


public class Blow  extends Animation implements Serializable {

	private static final String IMAGE = "sprites/blow.png";
	private static final String JSON  = "json_config/blow.json";
	private boolean playing;
	private int imageCounter = 0;

	public Blow( float x, float y,  GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);
		this.width = 10.0f;
		this.height = 9.0f;
		this.counting = 0;
		this.playing = false;
	}


	public void update(int delta, List<Animation> animations,  Animation animation, MarkArea markArea, MapPoint[][] map) {
		if (hasLoaded) {
			//TODO remove counting and do this by some kind of time
			if(counting == 8) {
				spriteIndex = (spriteIndex + 1) % sprite.numSprites();
				sprite.setSprite(spriteIndex);
				counting = 0;
				imageCounter++;
			}
			if(imageCounter >= 4) {
				setFire(false);
			}
			counting ++;
		}
	}

	@Override
	public void fire(int delta) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isInRange(Animation enemy) {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setPointMapOccupied(MapPoint[][] map, boolean isOccupide, MapPoint point) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
