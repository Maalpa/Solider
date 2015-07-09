/**
 * @author PKepa1
 * 
 * 
 */
package com.solider.war.core.sprites.model;

import com.solider.war.core.SpriteAssets;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.Animation;
import playn.core.GroupLayer;

public class Solider extends Animation {

	public Solider( final float x, final float y, SpriteAssets assets, final GroupLayer... layer) {
		super(layer[0], x, y, assets.getImage(), assets.getJson());
		this.width = assets.getWidth();
		this.height = assets.getHeight();
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





