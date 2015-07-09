package com.solider.war.core;

/**
 * User: PK_PC
 * Date: 06.01.15
 * Time: 10:21
 */
public enum SpriteAssets {

	BARICADE_HORIZONTAL("sprites/baricade_horizontal.png","json_config/baricade_horizontal.json", 120.0f , 30.0f ),
	BARICADE_VERTICAL("sprites/baricade_vertical.png","json_config/baricade_vertical.json", 30.0f, 120.0f),
	RED_SOLIDER("sprites/solider_20px_red.png", "json_config/solider.json", 20.0f, 20.0f),
	GREEN_SOLIDER("sprites/solider_20px.png",  "json_config/solider.json", 20.0f, 20.0f );

    private	String image;
    private String json;
    private float width;
    private float height;

	private SpriteAssets(String image, String json, float width, float height) {
		this.image = image;
		this.json = json;
		this.width = width;
		this.height = height;
	}

	public String getImage() {
		return image;
	}

	public String getJson() {
		return json;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
