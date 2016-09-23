package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.MyGame;

public class SplashScreen extends BaseScreen {
	
	private Image splash;

	public SplashScreen(final MyGame game) {
		super(game);
		splash = new Image(loadTexture("splash.png"));
		splash.setPosition(0, 0, Align.center);
		stage.addActor(splash);
		splash.setVisible(false);
	}

	@Override
	public void show() {
		splash.addAction(Actions.alpha(0));
		splash.setVisible(true);
		splash.addAction(Actions.sequence(
			Actions.fadeIn(0.5f),
			Actions.delay(3f),
			Actions.fadeOut(0.5f),
			new Action() {
				public boolean act(float delta) {
					game.setScreen(new TitleScreen(game));
					return true;
				}
			}
		));
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	private Texture loadTexture(String path) {
		Texture _tex = new Texture(path);
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return _tex;
	}

}
