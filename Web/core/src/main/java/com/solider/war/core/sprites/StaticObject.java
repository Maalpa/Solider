package com.solider.war.core.sprites;

import com.solider.war.core.Config;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import playn.core.GroupLayer;
import playn.core.util.Callback;

import static com.solider.war.core.Config.FIELD_SIZE;
import static playn.core.PlayN.log;

/**
 * Created with IntelliJ IDEA.
 * User: PK_PC
 * Date: 13.10.14
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class StaticObject {

	protected Sprite sprite;					        // sprite for images loaded from repo
	protected float x;									// center position x
	protected float y; 									// center position y
	protected int spriteIndex = 0;						// index of rendering sprite
	protected boolean hasLoaded = false; 				// set to true when resources have loaded
	protected  float width;								// width of sprite image i required for counting if object selected, working with imageX
	protected  float height; 							// width of sprite image i required for counting if object selected, working with imageY


	public StaticObject(final GroupLayer layer, final float x, final float y, final String image, final String json ) {

		this.sprite = SpriteLoader.getSprite(image, json);
		this.x = x;
		this.y = y;

		sprite.addCallback(new Callback<Sprite>() {
			@Override
			public void onSuccess(Sprite sprite) {
				sprite.setSprite(spriteIndex);
				sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
				sprite.layer().setTranslation(x, y);
				layer.add(sprite.layer());


//				//System.out.println("++++++++++++++++++++++++++++++++++++");
//
//				int width = ((int) sprite.width()) / Config.FIELD_SIZE;
//				int height = ((int) sprite.height()) / Config.FIELD_SIZE;
//				////System.out.println( "sprite.width() = " + sprite.width() );
//				//System.out.println( "sprite.height(); = " + sprite.height() );
//
//				//System.out.println(width);
//				//System.out.println(height);
//
//				//System.out.println("++++++++++++++++++++++++++++++++++++");

				hasLoaded = true;
			}

			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}

	public abstract int[][] getColisionFields();
	public abstract void setColisionFields(int[][] colisionFields);
	public abstract MPoint calcColision( MapPoint[][] map , MarkArea markArea);

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public boolean isHasLoaded() {
		return hasLoaded;
	}

	public void setHasLoaded(boolean hasLoaded) {
		this.hasLoaded = hasLoaded;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
