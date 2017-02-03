package data;

import static helpers.Artist.*;
import org.lwjgl.opengl.Display;


public class Boot {

	public Boot() {
		beginSession();
		Game game = new Game();
		game.run();
		Display.destroy();
	}

	public static void main(String[] args) {
		new Boot();
	}
}
