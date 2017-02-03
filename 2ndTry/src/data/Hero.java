package data;

import static helpers.Artist.drawTexturedQuad;
import static helpers.Artist.loadPNG;
import static helpers.Physics.areColiding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class Hero {
	private float x, y;
	private float vx, vy;
	private float ax, ay;
	private float drag;

	private float maxVelX;
	private float maxVelY;

	private int maxHP;
	private int health;
	private int dmg;
	public Inventory inventory;

	private Texture texture;
	private float width;
	private float height;

	private List<Item> thrownItems;
	
	public Hero(String heroTextureName,float width, float height) {
		this.x = 1 * width;
		this.y = 1 * height;
		
		this.vx = 0;
		this.vy = 0;
		
		this.drag = 0.95f;
		this.maxHP = 100;
		this.health = maxHP;
		this.dmg = 10;
		this.texture = loadPNG(heroTextureName);
		
		inventory = new Inventory();
		recalculateDimensions(width, height);
		recalculateSpeeds(width, height);
		
		thrownItems = new ArrayList<Item>();
	}
	
	public void recalculateDimensions(float width, float height){
		this.width = width * 0.8f;
		this.height = height * 0.8f;
		this.inventory.recalculateDimensions(width, height);
	}
	
	public void recalculateSpeeds(float width, float height){
		this.ax = width / 100;
		this.ay = height / 100;
		this.maxVelX = width / 8; 
		this.maxVelY = height / 8; 
	}
	
	public void stopMoving(){
		this.vx = 0;
		this.vy = 0;
		this.ax = 0;
		this.ay = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getCenterX() {
		return getX() + this.width / 2;
	}

	public float getCenterY() {
		return getY() + this.height / 2;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	private void applyColisionPhysics(Tile tCol){
		float xTileCenter = tCol.getX() + tCol.getWidth() / 2;
		float yTileCenter = tCol.getY() + tCol.getHeight() / 2;

		float dLeft = (float) Math.sqrt(Math.pow(x - xTileCenter, 2) + Math.pow((y + height / 2) - yTileCenter, 2));
		float dRight = (float) Math.sqrt(Math.pow(x + width - xTileCenter, 2) + Math.pow((y + height / 2) - yTileCenter, 2));
		float dUpper = (float) Math.sqrt(Math.pow((x + width / 2) - xTileCenter, 2) + Math.pow(y - yTileCenter, 2));
		float dLower = (float) Math.sqrt(Math.pow((x + width / 2) - xTileCenter, 2) + Math.pow(y + height - yTileCenter, 2));
	
		if (dLeft < dRight && dLeft < dUpper && dLeft < dLower) {
			x = tCol.getX() + tCol.getWidth();
			//Left
			vx = 0;
			//vx = -vx;
		} else if (dRight < dUpper && dRight < dLower) {
			x = tCol.getX() - this.width;
			//right
			vx = 0;
			//vx = -vx;
		} else if (dUpper < dLower) {
			//up
			y = tCol.getY() + tCol.getHeight();
			vy = 0;
			//vy = -vy;
		} else {
			//Down
			y = tCol.getY() - this.height;
			vy = 0;
			//vy = -vy;
		}
	}

	public void update(TileMap map) {
		for (int y = 0; y < map.getNumTilesY(); y++) {
			for (int x = 0; x < map.getNumTilesX(); x++) {
				Tile t = map.getTileAtPos(x, y);
				if (!t.getType().walkable && areColiding(this.x, this.y, this.width, this.height, t.getX(), t.getY(), t.getWidth(), t.getHeight())) {
					this.applyColisionPhysics(t);
				}
			}
		}
		
		this.inventory.update(map);
	}
	
	public void move(boolean[] directions){
		if (directions[0]){
			vy -= ay;
			if(vy < -maxVelY){
				vy = -maxVelY;
			}
		}
		if (directions[1]){
			vx += ax;
			if(vx > maxVelX){
				vx = maxVelX;
			}
		}
		if (directions[2]){
			vy += ay;
			if(vy > maxVelY){
				vy = maxVelY;
			}
		}
		if (directions[3]){
			vx -= ax;
			if(vx < -maxVelX){
				vx = -maxVelX;
			}
		}
		
		
		vx *= drag;
		vy *= drag;
		
		x += vx;
		y += vy;
	}
	
	public void addItemToThrownList(Item i){
		thrownItems.add(i);
	}
	
	public Iterator<Item> getItemsThrown(){
		return thrownItems.iterator();
	}

	public void draw() {
		drawTexturedQuad(texture, x, y, width, height);
		inventory.draw();
	}

	
}