package com.tybprojekt.drps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tybprojekt.drps.screens.SplashScreen;

public class MyGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera cam;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		screen.dispose();
	}
}
