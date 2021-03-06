package com.solider.war.core.sprites.model;

import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import playn.core.GroupLayer;

import java.io.Serializable;

import static playn.core.PlayN.graphics;

public class Barrel extends Animation implements Serializable {

	
	public static String IMAGE = "sprites/barrel.png";
	public static String JSON  = "json_config/barrel.json";
	private Shot shot;
	private GroupLayer shotLayer;
	private GroupLayer parentLayer;
	private int fireTime = 0;


	public Barrel(float x, float y, GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);
		this.width = 22.0f;
		this.height = 39.0f;
		this.parentLayer = layer[0];
		this.shotLayer = graphics().createGroupLayer();
		this.parentLayer.add(shotLayer);
	}

	public void fire(int delta) {
		if(this.fire) {
			if( this.shot == null) {
				this.shot = new Shot(x, y, this.angle ,  this.shotLayer );
			}
			this.shotLayer.setVisible(true);
			if((this.shot.isHasLoaded() && !this.shot.isPlaying()) ) {
				this.shot.setPlaying(true);
			}
			this.shot.pointRotation( this.x , this.y, this.angle);
			this.shot.setRotation(this.rotation);

			fireTime += delta;
			if(fireTime > 100) {
				this.setFire(false);
				fireTime= 0;
			}
		} else {
			this.shot = null;
			this.shotLayer.setVisible(false);
			this.shotLayer.removeAll();
		}
	}

	public void pointRotation(float bx, float by,  float angle ) {
		float x, y;
		if(Math.cos(angle) != 1.0 ) {
			x = (float) (bx + ( -(Math.cos(angle) * (5.1f))));
			y = (float) (by + ( Math.sin(angle) * (5.1f)));
		}
		else {
			y = by-5;
			x = bx;
		}
        this.setPosition(x, y);
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
		return "Barrel";
	}
}