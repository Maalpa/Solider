package com.solider.war.core.sprites.StaticObjectsModel;

import com.solider.war.core.helpers.PositionType;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

import static com.solider.war.core.Config.FIELD_SIZE;

/**
 * User: PKepa1
 * Date: 04.01.15
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class Barrack extends StaticObject {

	public static String IMAGE = "sprites/barrack.png";
	public static String JSON  = "json_config/barrack.json";
	private int[][] colisionFields;



	public Barrack(float x, float y, MapPoint[][] map, MarkArea markArea, PositionType positionType,  GroupLayer... layer ) {

		super(layer[0], x, y, IMAGE, JSON);
		this.width = 160.0f;
		this.height = 145.2f;

		colisionFields = new int[][] { { 0,0,1,1,1,1,0 },
									   { 0,1,1,1,1,1,1 },
									   { 1,1,1,1,1,1,1 },
									   { 1,1,1,1,1,1,1 },
									   { 0,1,1,1,1,1,1 },
									   { 0,0,1,1,1,1,0 }  };
		this.x = x;
		this.y = y;
		calcColision(map, markArea);
	}

	@Override
	public int[][] getColisionFields() {
		return colisionFields;
	}


	/*************************************************************************
	 * Draws occupided firlds for processing collisions.
	 * @param map
	 * @param markArea
	 * @return
	 ***********************************************************************/
	@Override
	public MPoint calcColision( MapPoint[][] map, MarkArea markArea ) {

		float posX = x-(this.width/2);
		float posY = y-(this.height/2);
		int corX = (int) (posX/FIELD_SIZE);
		int corY = (int) (posY/FIELD_SIZE);

		for(int i=0;i< colisionFields.length; i++) {
			for(int j=0 ; j<colisionFields[i].length ; j++) {
				if(colisionFields[i][j] == 1 ) {
					int drawX = (corX+j) +1; // only hear +1
					int drawY = (corY+i) +1; // only hear +1
					occupideFields.add(new MPoint(drawX, drawY));
					map[drawX][drawY].setOccupied(true);
					markArea.markPath(drawX*FIELD_SIZE, drawY*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
				}
			}
		}

		return new MPoint(corX,corY);
	}
}
