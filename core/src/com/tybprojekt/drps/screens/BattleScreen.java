package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
	
	private Image enemys;
	private Image yours;
	
	private State currentState;
	private int result;
	private int iteration;
	
	private float timeLeft;
	private Image timeGauge;
	private Group timer;
	
	private enum State {
		STANDBY,
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
		
		makeTimerUI();
	}
	
	private void makeTimerUI() {
		timer = new Group();
		
		Image _img;
		
		_img = new Image(loadTexture("timebar_l1.png"));
		_img.setPosition(0, 286, Align.center);
		timer.addActor(_img);
		
		timeGauge = _img = new Image(loadTexture("timegauge.png"));
		_img.setPosition(-392, 286, Align.center);
		_img.setSize(788f, 21);
		timer.addActor(_img);
		
		_img = new Image(loadTexture("timebar_l2.png"));
		_img.setPosition(timeGaugeSize(), 286, Align.center);
		timer.addActor(_img);
		
		stage.addActor(timer);
	}
	
	private Texture loadTexture(String path) {
		Texture _tex = new Texture(path);
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return _tex;
	}
	
	private float timeGaugeSize() {
		return timeLeft / 10f * 788f;
	}

	@Override
	public void show() {
		timeLeft = 10;
		float choicesYPos = -100;
		rock.setPosition(-250, choicesYPos, Align.center);
		paper.setPosition(0, choicesYPos, Align.center);
		scissors.setPosition(250, choicesYPos, Align.center);
		
		currentState = State.STANDBY;
		result = -2;
		iteration = 0;
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	
	public void update(float delta) {
		stage.act(delta);
		
		if (currentState == State.STANDBY) {
			if (enemys == null) {
				Image _img = new Image(StaticData.ENEMY_CHOICES[iteration].sprite);
				_img.setScale(0f);
				_img.setPosition(0, 100, Align.center);
				_img.setOrigin(Align.center);
				enemys = _img;
				enemys.addAction(Actions.sequence(
					Actions.scaleTo(0.5f, 0.5f, 0.2f, Interpolation.pow2),
					new Action() {
						@Override
						public boolean act(float delta) {
							toChoosingState();
							return true;
						}
					}
				));
				stage.addActor(enemys);
				
			}
		}
		
		if (currentState == State.CHOOSING && result == -2) {
			timeLeft -= delta;
			timeGauge.setSize(timeGaugeSize(), 21);
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				battle(Choice.ROCK, StaticData.ENEMY_CHOICES[iteration]);
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				battle(Choice.PAPER, StaticData.ENEMY_CHOICES[iteration]);
			} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				battle(Choice.SCISSORS, StaticData.ENEMY_CHOICES[iteration]);
			}
		}
		System.out.println(currentState + " | " + result);
		if (currentState == State.RESULT && result != -2 && enemys != null) {
			if (result == 0) {
				result = -2;
				enemys.addAction(Actions.sequence(
					new Action() {
						@Override
						public boolean act(float delta) {
							choiceGoBack();
							toChoosingState();
							return true;
						}
					}
				));
			}
			
			if (result == -1) {
				result = -2;
				iteration++;
				enemys.addAction(Actions.sequence(
					Actions.moveToAligned(0, -10, Align.center, 0.25f, Interpolation.pow5),
					Actions.scaleTo(0, 0),
					new Action() {
						@Override
						public boolean act(float delta) {
							choiceGoBack();
							toStandbyState();
							return true;
						}
					}
				));
			}
			
			if (result == 1) {
				result = -2;
				iteration++;
				enemys.addAction(Actions.sequence(
					Actions.fadeOut(0.25f, Interpolation.pow2),
					new Action() {
						@Override
						public boolean act(float delta) {
							choiceGoBack();
							toStandbyState();
							return true;
						}
					}
				));
			}
		}
		
		if (currentState == State.RESULT && result == -2) {
			currentState = State.CHOOSING;
		}
		
		if (iteration >= StaticData.LEVEL) {
			game.setScreen(game.screenStack.pop());
		}
	}
	
	private void battle(Choice player, Choice enemy) {
		result = Methods.battle(player, enemy);
		switch (player) {
		case ROCK:
			yours = rock;
			break;
		case PAPER:
			yours = paper;
			break;
		case SCISSORS:
			yours = scissors;
			break;
		}
		chooseAnimation();
	}
	
	private void chooseAnimation() {
		Action toResultStateAction = new Action() {
			public boolean act(float delta) {
				toResultState();
				return true;
			}
		};
		
		yours.addAction(Actions.sequence(
			Actions.moveToAligned(0, -10, Align.center, 0.25f, Interpolation.pow2),
			toResultStateAction
		));
	}
	
	private void choiceGoBack() {
		float choicesYPos = -100;
		rock.addAction(Actions.sequence(
			Actions.moveToAligned(-250, choicesYPos, Align.center, 0.2f, Interpolation.pow2),
			new Action() {
				@Override
				public boolean act(float delta) {
					result = -2;
					return true;
				}
			}
		));
		paper.addAction(Actions.moveToAligned(0, choicesYPos, Align.center, 0.2f, Interpolation.pow2));
		scissors.addAction(Actions.moveToAligned(250, choicesYPos, Align.center, 0.2f, Interpolation.pow2));
	}
	
	private void toStandbyState() {
		enemys = null;
		result = -2;
		currentState = State.STANDBY;
	}
	
	private void toChoosingState() {
		currentState = State.CHOOSING;
	}
	
	private void toResultState() {
		currentState = State.RESULT;
	}
	
	private String resultToString(int result) {
		if (result == -2) return "Null";
		if (result == -1) return "Lose";
		if (result == 0) return "Draw";
		return "Win";
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
