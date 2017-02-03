package data;

import static helpers.Artist.*;

import java.util.Random;

import helpers.Artist;
import static org.lwjgl.opengl.GL11.*;

public class TileMap {
	private Tile[][] map;

	private int numTilesX;
	private int numTilesY;
	private int widthOfTiles;
	private int heightOfTiles;

	private int exitX, exitY;
	private int level;

	private boolean shadowsNeedUpdate;

	public TileMap(int numTilesX, int numTilesY, Hero player) {
		this.numTilesX = numTilesX;
		this.numTilesY = numTilesY;
		level = 0;
		remakeMap(player);
		
	}
	
	public void remakeMap(Hero player){
		level = level + 1;
		numTilesX = numTilesX + 1;
		numTilesY = numTilesY + 1;
		map = new Tile[numTilesX][numTilesY];

		widthOfTiles = Artist.getScreenWidth() / numTilesX;
		heightOfTiles = Artist.getScreenHeight() / numTilesY;

		Random r;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * widthOfTiles, j * heightOfTiles, widthOfTiles, heightOfTiles, TileType.Floor);
				if (i == 0 || i == numTilesX - 1 || j == 0 || j == numTilesY - 1) {
					map[i][j] = new Tile(i * widthOfTiles, j * heightOfTiles, widthOfTiles, heightOfTiles,
							TileType.IndestructibleWall);
				}
			}
		}
		int rarityOfBlock;
		r = new Random(System.currentTimeMillis());
		int tileX;
		int tileY;
		int multiplier = r.nextInt(5) + 1;
		for (int i = 0; i < level * level * multiplier; i++) {
			rarityOfBlock = r.nextInt(100);
			tileX = r.nextInt(numTilesX - 2) + 1;
			tileY = r.nextInt(numTilesY - 2) + 1;
			if (rarityOfBlock >= TileType.DiamondBlock.rarity) {
				map[tileX][tileY] = new Tile(tileX * widthOfTiles, tileY * heightOfTiles, widthOfTiles, heightOfTiles,
						TileType.DiamondBlock);
			} else if (rarityOfBlock >= TileType.GoldBlock.rarity) {
				map[tileX][tileY] = new Tile(tileX * widthOfTiles, tileY * heightOfTiles, widthOfTiles, heightOfTiles,
						TileType.GoldBlock);
			} else if (rarityOfBlock >= TileType.IronBlock.rarity) {
				map[tileX][tileY] = new Tile(tileX * widthOfTiles, tileY * heightOfTiles, widthOfTiles, heightOfTiles,
						TileType.IronBlock);
			} else {
				map[tileX][tileY] = new Tile(tileX * widthOfTiles, tileY * heightOfTiles, widthOfTiles, heightOfTiles,
						TileType.Rock);
			}
		}
		
		setTileAtPos((int) (player.getX() / this.getWidthOfTiles()), (int) (player.getY() / this.getHeightOfTiles()),
				TileType.Floor);
		exitX = r.nextInt(numTilesX - 2) + 1;
		exitY = r.nextInt(numTilesY - 2) + 1;
		map[exitX][exitY] = new Tile(exitX * widthOfTiles, exitY * heightOfTiles, widthOfTiles, heightOfTiles,
				TileType.Exit);
		setShadowsNeedUpdate(true);
	}

	public Tile getTileAtPos(int x, int y) {
		return map[x][y];
	}

	public void setTileAtPos(int x, int y, TileType type) {
		map[x][y].setType(type);
	}

	private void makeBordersVisible() {
		for (int i = 0; i < numTilesY; i++) {
			for (int j = 0; j < numTilesX; j++) {
				if (i == 0 || j == 0 || i == numTilesY - 1 || j == numTilesX - 1)
					map[j][i].setDarkness(0.0f);
			}
		}
	}

	private void floodFillLight(int x, int y) {
		if (map[x][y].getType().seeThrough && map[x][y].isDark()) {
			
			map[x][y].setDarkness(0.0f);
			
			floodFillLight(x - 1, y);
			floodFillLight(x + 1, y);
			floodFillLight(x, y - 1);
			floodFillLight(x, y + 1);
			
			map[x + 1][y].setDarkness(0.0f);
			map[x - 1][y].setDarkness(0.0f);
			map[x][y + 1].setDarkness(0.0f);
			map[x][y - 1].setDarkness(0.0f);
		}
	}

	public boolean getShadowsNeedUpdate() {
		return shadowsNeedUpdate;
	}

	public void setShadowsNeedUpdate(boolean state) {
		shadowsNeedUpdate = state;
	}

	private void setAllDark() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].setDarkness(1.0f);
			}
		}
	}

	public void update(int playerX, int playerY) {
		if(shadowsNeedUpdate){
			setAllDark();
			floodFillLight(playerX, playerY);
			makeBordersVisible();
			shadowsNeedUpdate = false;
		}
		
		
	}

	public void draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Tile t = map[i][j];
				drawTexturedQuad(t.getTexture(), t.getX(), t.getY(), t.getWidth(), t.getHeight());
				
				glColor4f(0f, 0f, 0f, t.getDarkness());
				glDisable(GL_TEXTURE_2D);
				drawQuad(t.getX(), t.getY(), t.getWidth(), t.getHeight());
				glEnable(GL_TEXTURE_2D);
				glColor4f(1f, 1f, 1f, 1f);
			}
		}

	}

	public float getExitX() {
		return exitX * widthOfTiles;
	}

	public float getExitY() {
		return exitY * heightOfTiles;
	}

	public float getNumTilesX() {
		return numTilesX;
	}

	public float getNumTilesY() {
		return numTilesY;
	}

	public float getWidthOfTiles() {
		return widthOfTiles;
	}

	public float getHeightOfTiles() {
		return heightOfTiles;
	}
}
