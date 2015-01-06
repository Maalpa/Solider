package com.solider.war.core.sprites.StaticObjectsModel;

import com.solider.war.core.helpers.PositionType;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

/**
 * User: PKepa1
 * Date: 04.01.15
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class Barrack extends StaticObject {

	public static String IMAGE = "sprites/barrack.png";
	public static String JSON  = "json_config/barrack.json";


	public Barrack(float x, float y, MapPoint[][] map, MarkArea markArea, PositionType positionType,  GroupLayer... layer ) {

		super(layer[0], x, y, IMAGE, JSON);
		this.width = 160.0f;
		this.height = 145.2f;

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
