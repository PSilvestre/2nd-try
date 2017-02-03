package data;

import static helpers.Artist.loadPNG;

import org.newdawn.slick.opengl.Texture;

public enum BombType {
	Tier5("items/Bomb", 2, 5f, 15f, 0.97f, 5.0f, 0.5f);
	

	String textureName;
	Texture texture;
	
	float size;
	
	int radius;
	float damage;
	float fuse;
	float drag;
	float value;
	

	BombType(String textureName, int radius, float damage, float fuse, float drag, float value, float size) {
		this.textureName = textureName;
		this.texture = loadPNG(this.textureName);
		this.size = size;
		this.radius = radius;
		this.damage = damage;
		this.fuse = fuse;
		this.drag = drag;
		this.value = value;
	}
}