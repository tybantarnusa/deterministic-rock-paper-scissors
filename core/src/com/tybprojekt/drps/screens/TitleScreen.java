package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.MyGame;

public class TitleScreen extends BaseScreen {

	private Image pressStart;
	
	public TitleScreen(final MyGame game) {
		super(game);
		
		Texture _tex = loadTexture("title.png");
		Image _img = new Image(_tex);
		_img.setPosition(0, 0, Align.center);
		stage.addActor(_img);
		
		_tex = loadTexture("press_enter.png");
		pressStart = new Image(_tex);
		pressStart.setPosition(0, -165, Align.center);
		stage.addActor(pressStart);
	}
	
	private Texture loadTexture(String path) {
		Texture _tex = new Texture(path);
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return _tex;
	}

	@Override
	public void show() {
		pressStart.addAction(Actions.forever(Actions.sequence(
			Actions.fadeOut(0.7f),
			Actions.delay(0.2f),
			Actions.fadeIn(0.7f),
			Actions.delay(0.5f)
		)));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			game.setScreen(new MemorizeScreen(game));
		}
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

}
