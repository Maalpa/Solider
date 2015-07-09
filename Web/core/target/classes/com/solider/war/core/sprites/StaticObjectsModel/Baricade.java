package com.solider.war.core.sprites.StaticObjectsModel;

import com.solider.war.core.SpriteAssets;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.sprites.StaticObject;
import com.solider.war.core.tools.MarkArea;
import playn.core.GroupLayer;

import java.util.ArrayList;

import static com.solider.war.core.Config.FIELD_SIZE;

/**
 * User: PKepa1
 * Date: 05.01.15
 * Time: 09:46
 */
public class Baricade  extends StaticObject {

	private SpriteAssets assets;
	private int[][] colisionFields;

	public Baricade(float x, float y, MapPoint[][] map, MarkArea markArea, SpriteAssets assets,  GroupLayer... layer ) {

		super(layer[0], x, y, assets.getImage(), assets.getJson());

		if(assets == SpriteAssets.BARICADE_HORIZONTAL) {
			colisionFields = new int[][] {{1,1,1,1,1,1,1},
							              {1,1,1,1,1,1,1},
										  {1,1,1,1,1,1,1}};

		} else if(assets == SpriteAssets.BARICADE_VERTICAL) {
			colisionFields = new int[][] {  {1,1,1},
											{1,1,1},
											{1,1,1},
											{1,1,1},
											{1,1,1},
											{1,1,1},
											{1,1,1}};
		}

		this.x = x;
		this.y = y;
		this.width = assets.getWidth();
		this.height = assets.getHeight();
		this.assets = assets;
		calcColision(map, markArea);
	}

	@Override
	public int[][] getColisionFields() {
		return colisionFields;
	}

	@Override
	public MPoint calcColision( MapPoint[][] map, MarkArea markArea ) {

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
					occupideFields.add(new MPoint(drawX, drawY));
					map[drawX][drawY].setOccupied(true);
					markArea.markPath(drawX*FIELD_SIZE, drawY*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
				}
			}
		}
		return new MPoint(corX,corY);
	}
}
