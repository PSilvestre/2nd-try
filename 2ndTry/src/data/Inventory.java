package data;

public class Inventory {
	
	private Item equiped;
	
	private Item[] bag;
	private int counter;
	
	public Inventory(){
		setEquiped(new Bomb(BombType.Tier5, true));
		bag = new Item[5];
		counter = 0;
	}
	
	public void throwEquipped(float clickedX, float clickedY, float playerX, float playerY, float widthOfTiles, float heightOfTiles){
		getEquiped().throwItem(clickedX, clickedY, playerX, playerY, widthOfTiles, heightOfTiles);
	}
	
	
	
	public void update(TileMap map){
		getEquiped().update(map);
		for(int i = 0; i < counter; i++){
			bag[i].update(map);
		}
	}
	
	public void draw(){
		if(getEquiped().isThrown())
			getEquiped().draw();
		for(int i = 0; i < counter; i++){
			bag[i].draw();
		}
	}

	public Item getEquiped() {
		return equiped;
	}

	public void setEquiped(Item equiped) {
		this.equiped = equiped;
	}

	public void recalculateDimensions(float width, float height) {
		equiped.setWidth(width);
		equiped.setHeight(height);
		for(int i = 0; i < counter; i++){
			bag[i].setWidth(width);
			bag[i].setHeight(height);
		}
	}
}
