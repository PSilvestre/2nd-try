package data;

import static helpers.Artist.*;
import static helpers.Clock.*;
import org.newdawn.slick.opengl.Texture;

public class Monster {
	private float x, y;
	private float width, height;
	private float speed, health, damage;

	private boolean firstRun;

	private Texture texture;

	public Monster(String texture, float x, float y, float width, float height, float speed, float health,
			float damage) {
		this.texture = loadPNG(texture);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.firstRun = true;
	}

	public void update() {
		if (firstRun)
			firstRun = false;
		else
			x += Delta() * speed;
	}

	public void draw() {
		drawTexturedQuad(texture, x, y, width, height);
	}
}
