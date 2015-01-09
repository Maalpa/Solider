package com.solider.war.core.sprites;

import com.solider.war.core.Config;
import com.solider.war.core.model.GPoint;
import com.solider.war.core.model.MPoint;
import com.solider.war.core.path.MapPoint;
import com.solider.war.core.tools.MarkArea;
import com.solider.war.core.tools.Point;
import com.solider.war.core.tools.Transform;
import playn.core.GroupLayer;
import playn.core.util.Callback;

import java.util.ArrayList;

import static com.solider.war.core.Config.FIELD_SIZE;
import static playn.core.PlayN.log;

/**
 * User: PK_PC
 */
public abstract class StaticObject {

	protected Sprite sprite;					        // sprite for images loaded from repo
	protected float x;									// center position x
	protected float y; 									// center position y
	protected float imageX; 							// left Top position x
	protected float imageY; 							// left top position y
	protected int spriteIndex = 0;						// index of rendering sprite
	protected boolean hasLoaded = false; 				// set to true when resources have loaded
	protected float width;								// width of sprite image i required for counting if object selected, working with imageX
	protected float height; 							// width of sprite image i required for counting if object selected, working with imageY
	protected boolean selected = false;
	protected ArrayList<MPoint> occupideFields = new ArrayList<MPoint>();

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
				hasLoaded = true;
				width = sprite.spriteImages().get(0).width();
				height = sprite.spriteImages().get(0).height();
			}

			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}

	public abstract int[][] getColisionFields();
	public abstract MPoint calcColision( MapPoint[][] map , MarkArea markArea);

	public void clearOccupideMapFields( MapPoint[][] map ){
		if(occupideFields != null && !occupideFields.isEmpty()) {
			for(MPoint field : occupideFields) {
				map[field.getX()][field.getY()].setOccupied(false);
			}
			occupideFields.clear();
		}
	}

	public void moveObject(float mouseX , float mouseY) {
		sprite.layer().setTranslation(mouseX,mouseY);
		x = mouseX;
		y = mouseY;
	}

	public boolean select(float mouseX , float mouseY) {
		imageX =  ((this.x+ Transform.getX()) - (width/2.0f));	// calculating where image starts by transforming
		imageY =  ((this.y+ Transform.getY()) - (height/2.0f));	// calculating where image starts by transforming

		if( ((mouseX >= (imageX))  && (mouseX <= (imageX+this.width))) && (((mouseY) >= (imageY))  && (mouseY <= (imageY+this.height))) ) {
			this.selected = true;
		}

		return selected;
	}

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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public float getImageY() {
		return imageY;
	}

	public void setImageY(float imageY) {
		this.imageY = imageY;
	}

	public float getImageX() {
		return imageX;
	}

	public void setImageX(float imageX) {
		this.imageX = imageX;
	}
}
