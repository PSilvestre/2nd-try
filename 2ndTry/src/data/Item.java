package data;

import static helpers.Artist.drawTexturedQuad;
import static helpers.Physics.areColiding;

import org.newdawn.slick.opengl.Texture;

public class Item {
	private static final float VELOCITY = 5.0f;
	
	protected float itemX;
	protected float itemY;
	protected float itemWidth;
	protected float itemHeight;
	protected float itemSize;
	protected float vx, vy;
	
	protected boolean inInventory;
	protected boolean thrown;
	
	protected float value;

	
	
	
	Item(float x, float y, float width, float height, float value, float size){
		this.itemX = x;
		this.itemY = y;
		this.itemWidth = width;
		this.itemHeight = height;
		this.itemSize = size;
		this.value = value;
		this.vx = 0;
		this.vy = 0;
		this.inInventory = true;
	}

	public Item(float size, float value, boolean inInventory) {
		this.itemSize = size;
		this.value = value;
		this.vx = 0;
		this.vy = 0;
		this.inInventory = inInventory;
		thrown = false;
	}
	
	
	protected void applyColisionPhysics(Tile tCol){
		float xTileCenter = tCol.getX() + tCol.getWidth() / 2;
		float yTileCenter = tCol.getY() + tCol.getHeight() / 2;

		float dLeft = (float) Math.sqrt(Math.pow(itemX - xTileCenter, 2) + Math.pow((itemY + itemHeight / 2) - yTileCenter, 2));
		float dRight = (float) Math.sqrt(Math.pow(itemX + itemWidth - xTileCenter, 2) + Math.pow((itemY + itemHeight / 2) - yTileCenter, 2));
		float dUpper = (float) Math.sqrt(Math.pow((itemX + itemWidth / 2) - xTileCenter, 2) + Math.pow(itemY - yTileCenter, 2));
		float dLower = (float) Math.sqrt(Math.pow((itemX + itemWidth / 2) - xTileCenter, 2) + Math.pow(itemY + itemHeight - yTileCenter, 2));
	
		if (dLeft < dRight && dLeft < dUpper && dLeft < dLower) {
			itemX = tCol.getX() + tCol.getWidth();
			//Left
			//vx = 0;
			vx = -vx;
		} else if (dRight < dUpper && dRight < dLower) {
			itemX = tCol.getX() - this.itemWidth;
			//right
			//vx = 0;
			vx = -vx;
		} else if (dUpper < dLower) {
			//up
			itemY = tCol.getY() + tCol.getHeight();
			//vy = 0;
			vy = -vy;
		} else {
			//Down
			itemY = tCol.getY() - this.itemHeight;
			//vy = 0;
			vy = -vy;
		}
	}
	
	public void update(TileMap map){
		for (int y = 0; y < map.getNumTilesY(); y++) {
			for (int x = 0; x < map.getNumTilesX(); x++) {
				Tile t = map.getTileAtPos(x, y);
				if (!t.getType().walkable && areColiding(this.itemX, this.itemY, this.itemWidth, this.itemHeight, t.getX(), t.getY(), t.getWidth(), t.getHeight())) {
					this.applyColisionPhysics(t);
				}
			}
		}
	}
	
	public void throwItem(float playerX, float playerY, float targetX, float targetY, float widthOfTiles, float heightOfTiles){
		
	}
	
	public void draw(){
		drawTexturedQuad(this.getTexture(), this.itemX, this.itemY, this.itemWidth, this.itemHeight);
	}
	
	public boolean isThrown(){
		return thrown;
	}
	
	public Texture getTexture(){
		return null;
	}

	public void setWidth(float width) {
		this.itemWidth = width * itemSize;
	}
	
	public void setHeight(float height) {
		this.itemHeight = height * itemSize;
	}
}
