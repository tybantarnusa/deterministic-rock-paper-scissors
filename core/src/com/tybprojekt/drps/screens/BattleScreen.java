package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.Methods;
import com.tybprojekt.drps.MyGame;
import com.tybprojekt.drps.StaticData;
import com.tybprojekt.drps.data.Choice;

public class BattleScreen extends BaseScreen {

	private Image rock;
	private Image paper;
	private Image scissors;
	
	private State currentState;
	private int result;
	private int iteration;
	
	private enum State {
		CHOOSING,
		RESULT
	}
	
	public BattleScreen(MyGame game) {
		super(game);
		
		Texture _tex;
		
		_tex = new Texture("c_batu.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		rock = new Image(_tex);
		rock.setScale(0.8f);
		
		_tex = new Texture("c_kertas.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		paper = new Image(_tex);
		paper.setScale(0.8f);
		
		_tex = new Texture("c_gunting.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		scissors = new Image(_tex);
		scissors.setScale(0.8f);
		
		stage.addActor(rock);
		stage.addActor(paper);
		stage.addActor(scissors);
	}

	@Override
	public void show() {
		float choicesYPos = -100;
		rock.setPosition(-250, choicesYPos, Align.center);
		paper.setPosition(0, choicesYPos, Align.center);
		scissors.setPosition(250, choicesYPos, Align.center);
		
		currentState = State.CHOOSING;
		result = -2;
		iteration = 0;
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	
	public void update(float delta) {
		stage.act(delta);
		
		if (currentState == State.CHOOSING) {
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				battle(Choice.ROCK, StaticData.ENEMY_CHOICES[iteration]);
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				battle(Choice.PAPER, StaticData.ENEMY_CHOICES[iteration]);
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				battle(Choice.SCISSORS, StaticData.ENEMY_CHOICES[iteration]);
			}
			
		}
		
		if (iteration >= StaticData.LEVEL) {
			game.setScreen(game.screenStack.pop());
		}
	}
	
	private void battle(Choice player, Choice enemy) {
		result = Methods.battle(player, enemy);
		iteration++;
		System.out.println(player + " vs " + enemy + ": " + result);
//		currentState = State.RESULT;
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
