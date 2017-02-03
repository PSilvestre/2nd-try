package data;

import org.lwjgl.opengl.Display;

import helpers.Artist;
import helpers.InputHandler;
import helpers.Physics;

public class Game {
	private static final int STARTING_NUMBER_TILES_X = 7;
	private static final int STARTING_NUMBER_TILES_Y = 5;
	
	
	TileMap tileMap;
	Hero player;
	InputHandler input;
	HUD hud;
	
	public static final int FPS = 60;
	
	boolean isRunning;
	
	public Game(){
		isRunning = true;
		player = new Hero("players/hero", Artist.getScreenWidth()/STARTING_NUMBER_TILES_X, Artist.getScreenHeight()/STARTING_NUMBER_TILES_Y);
		tileMap = new TileMap(STARTING_NUMBER_TILES_X, STARTING_NUMBER_TILES_Y, player);
		input = new InputHandler();
		hud = new HUD("Hud/cursor3", tileMap.getWidthOfTiles(), tileMap.getHeightOfTiles());
		
	}
	
	public void run(){
		while (isRunning) {
			if(input.getRequestedExit())
				isRunning = false;
			
			input.checkInput(tileMap, player);
			
			checkPlayerExiting();
			
			player.update(tileMap);
			tileMap.update((int) (player.getCenterX()/tileMap.getWidthOfTiles()), (int) (player.getCenterY()/tileMap.getHeightOfTiles()));
			
			
			tileMap.draw();
			player.draw();
			
			hud.draw();
			

			Display.update();
			Display.sync(FPS);
		}
	}

	private void checkPlayerExiting() {
		if (Physics.areColiding(player.getX(), player.getY(), player.getWidth(), player.getHeight(), tileMap.getExitX(), tileMap.getExitY(), (float) tileMap.getWidthOfTiles(), (float) tileMap.getHeightOfTiles())) {
			
			player.stopMoving();
			tileMap.remakeMap(player);
			player.recalculateDimensions(tileMap.getWidthOfTiles(), tileMap.getHeightOfTiles());
			player.recalculateSpeeds(tileMap.getWidthOfTiles(), tileMap.getHeightOfTiles());
			
			tileMap.setShadowsNeedUpdate(true);
		}
		
	}
	
	
}
