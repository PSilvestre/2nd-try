package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class Tile {

	private float x, y, width, height;
	private TileType type;
	private float darkness;

	public Tile(float x, float y, float width, float height, TileType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		darkness = 1.0f;
	}

	public void draw() {
		drawTexturedQuad(this.type.texture, x, y, width, height);
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

	public Texture getTexture() {
		return this.type.texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public float getDarkness() {
		return darkness;
	}

	public void setDarkness(float d) {
		darkness = d;
	}

	public boolean isVisible() {
		return (getDarkness() == 0.0f);
	}

	public boolean isDark() {
		return getDarkness() > 0.0f;
	}

}
