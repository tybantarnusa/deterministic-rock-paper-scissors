package com.tybprojekt.drps.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tybprojekt.drps.Constants;
import com.tybprojekt.drps.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "DRPS: Deterministic Rock-Paper-Scissors";
		config.width = (int) Constants.V_WIDTH;
		config.height = (int) Constants.V_HEIGHT;
		config.addIcon("logo.png", Files.FileType.Internal);
		new LwjglApplication(new MyGame(), config);
	}
}
