package helpers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import data.Hero;
import data.TileMap;
import data.TileType;

public class InputHandler {

	private boolean[] directionsMoving;

	private float clickedX, clickedY;
	private int clickedSquareX, clickedSquareY;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;
	private boolean requestedExit;
	
	
	public InputHandler(){
		requestedExit = false;
		resetVariables();
	}
	
	public void checkInput(TileMap tileMap, Hero player){
		if(Display.isCloseRequested()){
			requestedExit = true;
		}
		
		if (Mouse.isButtonDown(0)) {
			leftMouseButtonDown = true;
		}
		
		if (Mouse.isButtonDown(1)) {
			rightMouseButtonDown = true;
			
		}
		
		if(rightMouseButtonDown && !Mouse.isButtonDown(1)){
			rightMouseButtonDown = false;
			clickedX = Mouse.getX();
			clickedY = Mouse.getY();
			clickedY = tileMap.getHeightOfTiles()*tileMap.getNumTilesY() - clickedY;
			System.out.println(clickedX + ", " + clickedY);
			player.inventory.throwEquipped(clickedX, clickedY, player.getCenterX(), player.getCenterY(), tileMap.getWidthOfTiles(), tileMap.getHeightOfTiles());
		}

		if (leftMouseButtonDown) {
			leftMouseButtonDown = false;
			clickedX = Mouse.getX();
			clickedY = Mouse.getY();
			if (clickedX < Artist.getScreenWidth() && clickedX > 0 && clickedY > 0
					&& clickedY < Artist.getScreenHeight()) {
				clickedSquareX = (int) (clickedX / tileMap.getWidthOfTiles());
				clickedSquareY = (int) (tileMap.getNumTilesY() - (clickedY / tileMap.getHeightOfTiles()));
				if (tileMap.getTileAtPos(clickedSquareX, clickedSquareY).getType().destructable) {
					tileMap.setTileAtPos(clickedSquareX, clickedSquareY, TileType.Floor);
					tileMap.setShadowsNeedUpdate(true);
				}
			}

		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_W){
				directionsMoving[0] = !directionsMoving[0];
				
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S){
				directionsMoving[2] = !directionsMoving[2];
				
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_A){
				directionsMoving[3] = !directionsMoving[3];
				
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_D){
				directionsMoving[1] = !directionsMoving[1];
				
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				requestedExit = true;
			}
				
		}
		player.move(directionsMoving);
	}
	
	private void resetVariables(){
		leftMouseButtonDown = false;
		resetMovement();
	}
	
	private void resetMovement(){
		directionsMoving = new boolean[4];
		for(int i = 0; i < directionsMoving.length; i++){
			directionsMoving[i] = false;
		}
	}

	public boolean getRequestedExit() {
		return requestedExit;
	}
}
