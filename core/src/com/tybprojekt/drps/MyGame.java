package com.tybprojekt.drps;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tybprojekt.drps.screens.MemorizeScreen;

public class MyGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera cam;
	public Stack<Screen> screenStack;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
		screenStack = new Stack<Screen>();
		setScreen(new MemorizeScreen(this));
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
