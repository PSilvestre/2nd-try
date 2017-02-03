package data;

import static helpers.Artist.drawTexturedQuad;
import static helpers.Artist.loadPNG;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class HUD {
	private Texture cursorTexture;
	private float cursorWidth;
	private float cursorHeight;
	
	public HUD(String cursorTextureName, float widthOfTile, float heightOfTile){
		cursorTexture = loadPNG(cursorTextureName);
		cursorWidth = widthOfTile / 2;
		cursorHeight = heightOfTile / 2;
		
	}
	
	public void draw() {
		drawTexturedQuad(cursorTexture, Mouse.getX() - cursorWidth / 2, Artist.getScreenHeight() - Mouse.getY() - cursorHeight / 2, cursorWidth, cursorHeight);;
	}
}
