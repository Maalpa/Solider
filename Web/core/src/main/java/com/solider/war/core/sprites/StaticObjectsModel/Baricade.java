package com.solider.war.core.sprites.StaticObjectsModel;

import com.solider.war.core.SpriteAssets;
import com.solider.war.core.helpers.PositionType;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

/**
 * User: PKepa1
 * Date: 05.01.15
 * Time: 09:46
 */
public class Baricade  extends StaticObject {



	public Baricade(float x, float y, MapPoint[][] map, MarkArea markArea, SpriteAssets assets,  GroupLayer... layer ) {

		super(layer[0], x, y, assets.getImage(), assets.getJson());

		this.width = assets.getWidth();
		this.height = assets.getHeight();

		System.out.println("this.width = " + width);
		System.out.println("this.height = " + height);

		System.out.println("*********************************");
		System.out.println("position("+x+","+y+")");
		System.out.println("*********************************");

		this.x = x;
		this.y = y;
		calcColision(map, markArea);
	}

	@Override
	public int[][] getColisionFields() {
		return new int[0][];  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setColisionFields(int[][] colisionFields) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public MPoint calcColision(MapPoint[][] map, MarkArea markArea) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
