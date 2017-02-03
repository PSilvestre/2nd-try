package helpers;

import org.lwjgl.Sys;

public class Clock {
	public static final int MIN_MULT = -1;
	public static final int MAX_MULT = 5;

	private static boolean paused = false;
	public static long lastFrame, totalTime;
	public static float d = 0, multiplier = 1;

	public static long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static float getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta * 0.01f;
	}

	public static float Delta() {
		if (paused)
			return 0;
		else
			return d * multiplier;
	}

	public static float totalTime() {
		return totalTime;
	}

	public static float multiplier() {
		return multiplier;
	}

	public static void update() {
		d = getDelta();
		totalTime += d;
	}

	public static void changeMultiplier(int change) {
		if (multiplier + change < MIN_MULT) {
			multiplier = MIN_MULT;
		} else if (multiplier + change > MAX_MULT) {
			multiplier = MAX_MULT;
		} else {
			multiplier += change;
		}
	}

	public static void pause() {
		if (paused)
			paused = false;
		else
			paused = true;
	}

}
