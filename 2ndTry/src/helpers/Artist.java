package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	private static int screenWidth = 1280;
	private static int screenHeight = 960;
	
	

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		Artist.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		Artist.screenHeight = screenHeight;
	}

	public static void beginSession() {
		Display.setTitle("2nd try");
		try {
			Display.setDisplayMode(new DisplayMode(getScreenWidth(), getScreenHeight()));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, getScreenWidth(), getScreenHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
		
		Mouse.setGrabbed(true);
	}

	public static void drawQuad(float x, float y, float width, float height) { 
		         
		// set the color of the quad (R,G,B,A)
		
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	public static void drawTexture(Texture t, float x, float y, float size, float width, float height){
		t.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width * size, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width * size, height * size);
		glTexCoord2f(0, 1);
		glVertex2f(0, height * size);
		glEnd();
		glLoadIdentity();
	}

	public static void drawTexturedQuad(Texture t, float x, float y, float width, float height) {
		t.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}

	public static void drawTintedTexturedQuad(Texture t, float x, float y, float width, float height, float red,
			float green, float blue, float alpha) {
		t.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glColor4f(red, green, blue, alpha);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}

	public static Texture loadTexture(String path, String fileType) {
		Texture t = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {

			t = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static Texture loadPNG(String name) {
		Texture t = null;
		t = loadTexture("res/" + name + ".png", "PNG");
		return t;
	}

}
