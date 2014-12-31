package com.solider.war.core.sprites.model;


import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import playn.core.GroupLayer;

import java.io.Serializable;

public class Shot  extends Animation implements Serializable {
	
	private static final String IMAGE = "sprites/shot.png";
	private static final String JSON  = "json_config/shot.json";
	private boolean playing;
	
	public Shot( float x, float y,  float angle ,  GroupLayer... layer) {
		super(layer[0], x, y,angle, IMAGE, JSON);
		this.width = 10.0f;
		this.height = 9.0f;
		this.counting = 0;
		this.playing = false;
	}
	
	public void pointRotation(float bx, float by,  float angle ) {
		float x, y;
		x = (float) (bx + ( -(Math.cos(angle) * (40.1f))));
        y = (float) (by + ( Math.sin(angle) * (40.1f)));
        this.setPosition(x, y);
	}

	@Override
	public void fire(int delta) {
		if (hasLoaded) {
			sprite.setSprite(counting);
			counting ++;
		}
	}

	@Override
	public boolean isInRange(Animation enemy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPointMapOccupied(MapPoint[][] map, boolean isOccupide, MapPoint point) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String toString(){
		return "Shot";
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
}
