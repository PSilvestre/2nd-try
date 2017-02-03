package data;

import static helpers.Physics.areColiding;

import org.newdawn.slick.opengl.Texture;

public class Bomb extends Item{
	private float VELOCITY = 15.0f;
	
	private BombType type;
	private float timeSinceThrown;
	float lastThrownRatio;
	
	public Bomb(float x, float y, float width, float height, BombType type, boolean activated){
		super(x,y,width,height, type.value, type.size);
		
		this.type = type;
		timeSinceThrown = -1.0f;
	}
	
	public Bomb(BombType type, boolean inInventory){
		super(type.size, type.value, inInventory);
		this.type = type;
		timeSinceThrown = -1.0f;
	}

	private void explode(TileMap tm){
		short explodedTileX = (short) (this.itemX / tm.getWidthOfTiles());
		short explodedTileY = (short) (this.itemY / tm.getHeightOfTiles());
		int halfRadius = type.radius / 2;
		for(int y = -halfRadius; y <= halfRadius; y++){
			for(int x = -halfRadius; x <= halfRadius ; x++){
				if(tm.getTileAtPos(explodedTileX + x, explodedTileY + y).getType().destructable)
					tm.setTileAtPos(explodedTileX + x, explodedTileY + y, TileType.Floor);
			}
		}
		tm.setShadowsNeedUpdate(true);
	}
	
	public BombType getType(){
		return this.type;
	}
	
	public void throwItem(float targetX, float targetY, float playerX, float playerY, float widthOfTiles, float heightOfTiles){
		this.thrown = true;
		timeSinceThrown = 0.0f;
		
		float xDist = targetX - playerX;
		float yDist = targetY - playerY;
		
		float angle = (float) (Math.atan2(yDist, xDist));
		
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		System.out.println(Math.sqrt(Math.pow(cos, 2) + Math.pow(sin, 2)));
		this.vx = (float) (VELOCITY * cos);
		this.vy = (float) (VELOCITY * sin);
		
		this.itemX = playerX - this.itemWidth/2;
		this.itemY = playerY - this.itemHeight/2;
	}
	
	public void update(TileMap map){
		this.vx *= type.drag;
		this.vy *= type.drag;
		this.itemX += this.vx;
		this.itemY += this.vy;
		
		if(timeSinceThrown != -1.0f){
			timeSinceThrown += 0.1f;
			if(timeSinceThrown >= type.fuse){
				explode(map);
				reset();
			}
		}
		
		for (int y = 0; y < map.getNumTilesY(); y++) {
			for (int x = 0; x < map.getNumTilesX(); x++) {
				Tile t = map.getTileAtPos(x, y);
				if (!t.getType().walkable && areColiding(this.itemX, this.itemY, this.itemWidth, this.itemHeight, t.getX(), t.getY(), t.getWidth(), t.getHeight())) {
					this.applyColisionPhysics(t);
				}
			}
		}
	}
	
	public void reset(){
		this.thrown = false;
		this.inInventory = true;
		timeSinceThrown = -1.0f;
	}
	
	public Texture getTexture(){
		return type.texture;
	}
}
