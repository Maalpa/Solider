/**
 * @author PKepa1
 * 
 * 
 */
package com.solider.war.core.sprites.model;

import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import playn.core.GroupLayer;

public class Solider extends Animation {

	private static final String JSON  = "json_config/solider.json";

	public Solider( final float x, final float y, String imagePath , final GroupLayer... layer) {
		super(layer[0], x, y, imagePath, JSON);
		this.width = 20.0f;
		this.height = 20.0f;
	}

	@Override
	public void fire(int delta) {
			// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInRange(Animation enemy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPointMapOccupied(MapPoint[][] map, boolean isOccupide, MapPoint point) {
		map[point.getX()][point.getY()].setOccupied(isOccupide);
	}

	@Override
	public String toString(){
		return "Solider";
	}
}





