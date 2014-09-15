/**
 * @author PKepa1
 * 
 * 
 */
package com.solider.war.core.sprites.model;

import static playn.core.PlayN.log;


import com.solider.war.core.model.GPoint;
import com.solider.war.core.sprites.Animation;

import playn.core.GroupLayer;
import playn.core.util.Callback;
import org.jbox2d.collision.shapes.EdgeShape;

public class Solider extends Animation {
	
	private static final String IMAGE = "sprites/solider_20px.png";
	private static final String JSON  = "json_config/solider.json";
	
	public Solider( final float x, final float y, final GroupLayer... layer) {
		super(layer[0], x, y, IMAGE, JSON);	
		this.width = 20.0f;
		this.height = 20.0f;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInRange(Animation enemy) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString(){
		return "Solider";
	}
}





