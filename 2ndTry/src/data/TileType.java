package data;

import static helpers.Artist.loadPNG;

import org.newdawn.slick.opengl.Texture;

public enum TileType {
	Missing("structures/missing", false, true, false, false, 0),
	Rock("structures/rock", false, false, true, false, 1),
	Floor("structures/floor", true, true, false, false, 0),
	IndestructibleWall("structures/wall", false, false, false, false, 0),
	IronBlock("structures/iron", false, false, true, false, 50),
	GoldBlock("structures/gold", false, false, true, false, 75),
	DiamondBlock("structures/diamond", false, false, true, false, 90),
	Exit("structures/exit", true, true, false, true, 0);

	String textureName;
	Texture texture;
	boolean seeThrough;
	boolean walkable;
	public boolean destructable;
	boolean transicioner;
	int rarity;

	TileType(String textureName, boolean seeThrough, boolean walkable, boolean destructable, boolean transicioner,
			int rarity) {
		this.textureName = textureName;
		this.texture = loadPNG(this.textureName);
		this.seeThrough = seeThrough;
		this.walkable = walkable;
		this.destructable = destructable;
		this.transicioner = transicioner;
		this.rarity = rarity;
	}
}
