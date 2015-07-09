package com.solider.war.core.sprites.StaticObjectsModel;


import com.solider.war.core.model.GPoint;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import playn.core.GroupLayer;

import static com.solider.war.core.Config.FIELD_SIZE;

/**
 * Created with IntelliJ IDEA.
 * User: PK_PC
 * Date: 12.10.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class Bags extends StaticObject {

	public static String IMAGE = "sprites/bag.png";
	public static String JSON  = "json_config/bag.json";


	protected int[][] colisionFields = {    {0,1,1,1,1,1,1,1,0},
											{1,1,0,0,0,0,0,1,1},
											{1,0,0,0,0,0,0,0,1}
									   };

	public Bags(float x, float y, MapPoint[][] map, MarkArea markArea,  GroupLayer... layer ) {
		super(layer[0], x, y, IMAGE, JSON);
		this.width = 170.0f;
		this.height = 40.2f;

		System.out.println("*********************************");
		System.out.println("position("+x+","+y+")");
		System.out.println("*********************************");

		this.x = x;
		this.y = y;
		calcColision(map, markArea);
	}

	public int[][] getColisionFields() {
		return colisionFields;
	}

	public void setColisionFields(int[][] colisionFields) {
		this.colisionFields = colisionFields;
	}




	public MPoint calcColision( MapPoint[][] map, MarkArea markArea ) {

		System.out.println("*********************************");
		System.out.println("position("+x+","+y+")");
		System.out.println("*********************************");

		float posX = x-(this.width/2);
		float posY = y-(this.height/2);

		int corX =(int) (posX/FIELD_SIZE);
		int corY =(int) (posY/FIELD_SIZE);

		System.out.println(corX+","+corY);
		for(int i=0;i< colisionFields.length; i++) {
			for(int j=0 ; j<colisionFields[i].length ; j++) {
				if(colisionFields[i][j] == 1 ) {
					int drawX = (corX+j);
					int drawY = (corY+i);
					map[drawX][drawY].setOccupied(true);
					markArea.markPath(drawX*FIELD_SIZE, drawY*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
				}
			}
		}

		return new MPoint(corX,corY);
	}
}
