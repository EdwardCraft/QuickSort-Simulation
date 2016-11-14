package com.quicksort.simulation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quicksort.simulation.Quicksort;
import com.quicksort.simulation.utils.Contants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Contants.APP_WIDTH;
		config.height = Contants.APP_HEIGHT;

		new LwjglApplication(new Quicksort(), config);
	}
}
