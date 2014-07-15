package com.solider.war.core.sprites.model;

import static playn.core.PlayN.graphics;

import java.util.LinkedList;

import playn.core.GroupLayer;

import com.solider.war.core.model.GPoint;
import com.solider.war.core.path.PathPoint;
import com.solider.war.core.sprites.Animation;

public class Barrel extends Animation {
	
	public static String IMAGE = "sprites/barrel.png";
	public static String JSON  = "json_config/barrel.json";
	private Shot shot;
	private GroupLayer shotLayer;
	private GroupLayer parentLayer;

	public Barrel(float x, float y, GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);
		this.width = 40.0f;
		this.height = 64.0f;
		this.parentLayer = layer[0];
		shotLayer = graphics().createGroupLayer();
		parentLayer.add(shotLayer);	
	}
	
	public void fire() {
		if(this.fire) {
			if( this.shot == null) {
				this.shot = new Shot(x, y, this.shotLayer );
			}
			this.shot.fire();
			this.shot.pointRotation( this.x , this.y, this.angle);
			shot.setRotation(this.rotation);
			if( this.shot.getCounting() == 6 ) {
				this.shotLayer.removeAll();
				this.shot = null;
				this.fire = false;
			}
		} else {
			this.shotLayer.removeAll();
		}
	}

	@Override
	public boolean isInRange(Animation enemy) {
		// TODO Auto-generated method stub
		return false;
	}
}
