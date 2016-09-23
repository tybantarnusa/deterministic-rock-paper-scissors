package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.MyGame;

public class TitleScreen extends BaseScreen {

	private Image pressStart;
	private Music bgm;
	private Sound confirm;
	
	public TitleScreen(final MyGame game) {
		super(game);
		
		bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/title.mp3"));
		confirm = Gdx.audio.newSound(Gdx.files.internal("sounds/decide.mp3"));
		
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
		bgm.setLooping(true);
		bgm.play();
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
			stage.addAction(Actions.sequence(
				new Action() {
					public boolean act(float delta) {
						confirm.play();
						bgm.stop();
						return true;
					}
				},
				Actions.delay(0.4f),
				new Action() {
					public boolean act(float delta) {
						game.setScreen(new MemorizeScreen(game));
						return true;
					}
				}
			));
			
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
		bgm.dispose();
		confirm.dispose();
	}

}
