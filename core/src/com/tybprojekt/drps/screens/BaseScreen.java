package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tybprojekt.drps.Constants;
import com.tybprojekt.drps.MyGame;

public abstract class BaseScreen implements Screen {
	protected MyGame game;
	protected Stage stage;
	
	public BaseScreen(final MyGame game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.setViewport(new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, game.cam));
	}

}
