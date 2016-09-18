package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.Constants;
import com.tybprojekt.drps.MyGame;
import com.tybprojekt.drps.StaticData;
import com.tybprojekt.drps.data.Choice;

public class MemorizeScreen extends BaseScreen {

	private Choice[] enemyChoices;
	private Group choices;
	private Texture bg;
	private Image focus;
	private Image text1;
	private Image textMemorize;
	private Image textReady;
	
	public MemorizeScreen(MyGame game) {
		super(game);
		bg = new Texture("bg1.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Texture _tex = new Texture("text1.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		text1 = new Image(_tex);
		text1.setScale(0.7f);
		text1.setPosition(-Constants.V_WIDTH/2f + 10, 170);
		stage.addActor(text1);
		focus = new Image(new Texture("focus.png"));
		focus.setPosition(-Constants.V_WIDTH/2f, 70);
		focus.setScale(Constants.V_WIDTH, 0.6f);
		stage.addActor(focus);
		
		_tex = new Texture("text_mem.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textMemorize = new Image(_tex);
		_tex = new Texture("text_ready.png");
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textReady = new Image(_tex);
		textReady.setPosition(0, -80, Align.center);
		
		enemyChoices = new Choice[] {
			Choice.PAPER,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.ROCK,
			Choice.PAPER,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.ROCK,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.ROCK,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.SCISSORS,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.PAPER,
			Choice.ROCK,
			Choice.PAPER,
			Choice.SCISSORS,
			Choice.PAPER,
			Choice.ROCK,
			Choice.SCISSORS,
			Choice.ROCK
		};
		StaticData.ENEMY_CHOICES = enemyChoices;
		StaticData.HIT = 100;
	}

	@Override
	public void show() {
		choices = new Group();
		
		textMemorize.addAction(Actions.alpha(1));
		textReady.setVisible(false);
		textMemorize.setPosition(0, -80, Align.center);
		for (int i = 0; i <= StaticData.LEVEL; i++) {
			Image enemyChoice = new Image(enemyChoices[i].sprite);
			enemyChoice.setScale(0.5f);
			enemyChoice.setPosition((i * 100 + 30) + Constants.V_WIDTH / 2f, 90);
			choices.addActor(enemyChoice);
		}
		
		choices.addAction(Actions.sequence(
				Actions.delay(0.3f),
				Actions.moveBy(-(Constants.V_WIDTH + 100 * (StaticData.LEVEL + 1)), 0, 2 + 0.4f * StaticData.LEVEL),
				Actions.delay(0.3f),
				Actions.run(new Runnable(){

					@Override
					public void run() {
						textMemorize.addAction(Actions.alpha(0, 0.3f));
						stage.addActor(textReady);
						textReady.setVisible(false);
						textReady.addAction(Actions.sequence(
							Actions.alpha(0),
							Actions.visible(true),
							Actions.repeat(3, 
								Actions.sequence(
									Actions.alpha(0),
									Actions.delay(0.3f),
									Actions.alpha(1),
									Actions.delay(0.3f)
								)
							),
							Actions.run(new Runnable(){
								public void run() {
									StaticData.LEVEL++;
									game.screenStack.push(game.getScreen());
									game.setScreen(new BattleScreen(game));
								}
							})
						));
					}
					
				})
			)
		);
		
		stage.addActor(textMemorize);
		
		stage.addActor(choices);

//		game.screenStack.push(game.getScreen());
//		game.setScreen(new BattleScreen(game));
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(bg, 0, 0);
		game.batch.end();
		
		stage.draw();
	}
	
	public void update(float delta) {
		stage.act(delta);
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
		bg.dispose();
	}

}
