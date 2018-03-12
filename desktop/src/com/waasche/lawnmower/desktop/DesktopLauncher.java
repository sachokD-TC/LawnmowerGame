package com.waasche.lawnmower.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.waasche.lawnmower.main.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Lawnmower Game";
		config.height = 1080/2;
		config.width = 1920/2;
		new LwjglApplication(new MainClass(), config);
	}
}
