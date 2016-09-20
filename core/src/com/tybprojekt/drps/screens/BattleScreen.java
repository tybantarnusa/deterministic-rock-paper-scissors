package com.tybprojekt.drps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.tybprojekt.drps.Methods;
import com.tybprojekt.drps.MyGame;
import com.tybprojekt.drps.StaticData;
import com.tybprojekt.drps.data.Choice;

public class BattleScreen extends BaseScreen {

	private Skin skin;
	private Label scoreLabel;
	
	private Image rock;
	private Image paper;
	private Image scissors;
	
	private Image enemys;
	private Image yours;
	private Image star;
	
	private State currentState;
	private int result;
	private int iteration;
	
	private float timeLeft;
	private Image timeGauge;
	private Group timer;
	private Label timeLabel;
	
	private Group comboUI;
	private Label comboLabel;
	private float comboTime;
	private int combo;
	
	private Image hitGauge;
	private Group hitter;
	
	private float shake;
	private float hitShake;
	
	private enum State {
		STANDBY,
		CHOOSING,
		RESULT,
		TRANSITION
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
		
		_tex = loadTexture("star.png");
		star = new Image(_tex);
		star.setScale(0f);
		star.setPosition(0, 100, Align.center);
		star.setOrigin(Align.center);
		
		stage.addActor(star);
		stage.addActor(rock);
		stage.addActor(paper);
		stage.addActor(scissors);
		
		makeTimerUI();
		makeHPUI();
		
		comboTime = -1;
		
		skin = new Skin(Gdx.files.internal("skins/myskin.json"));
		scoreLabel = new Label("Score: ", skin, "roboto-24", Color.BLACK);
		scoreLabel.setAlignment(Align.right);
		scoreLabel.setText("Score:\r\n" + StaticData.SCORE);
		scoreLabel.setPosition(320, 220);
		scoreLabel.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stage.addActor(scoreLabel);
		
		timeLabel = new Label("", skin, "roboto-12", Color.BLACK);
		timeLabel.setAlignment(Align.right);
		timeLabel.setPosition(390, 285);
		timeLabel.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stage.addActor(timeLabel);
		makeUIDesc();
		makeComboUI();
	}
	
	private void makeUIDesc() {
		Label _label = new Label("TIME", skin, "roboto-12", Color.BLACK);
		_label.setPosition(-395, 255);
		_label.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stage.addActor(_label);
		_label = new Label("HIT METER", skin, "roboto-12", Color.BLACK);
		_label.setPosition(-395, -223);
		_label.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stage.addActor(_label);
		
	}
	
	private void makeComboUI() {
		comboUI = new Group();
		
		comboLabel = new Label("", skin, "combo-big", Color.BLACK);
		comboLabel.setAlignment(Align.bottomRight);
		comboLabel.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Label tulisanCombo = new Label("Combo", skin, "combo-small", Color.BLACK);
		tulisanCombo.setPosition(-(93-71), -(64-42));
		tulisanCombo.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		comboLabel.setPosition(-(93-74), -(53-37), Align.bottomRight);
		comboLabel.setOrigin(Align.center);
		comboUI.addActor(tulisanCombo);
		comboUI.addActor(comboLabel);
		comboUI.setPosition(-(400-145), 300-125);
		comboUI.addAction(Actions.alpha(0));
		stage.addActor(comboUI);
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
		_img.setPosition(0, 286, Align.center);
		timer.addActor(_img);
		
		stage.addActor(timer);
	}
	
	private void makeHPUI() {
		hitter = new Group();
		hitShake = 0;
		
		Image _img;
		
		_img = new Image(loadTexture("health_l1.png"));
		_img.setPosition(-395, -256-(63/2f));
		_img.setSize(790f, 63);
		hitter.addActor(_img);
		
		hitGauge = _img = new Image(loadTexture("health.png"));
		_img.setPosition(-395, -256-(63/2f));
		_img.setSize(hitGaugeSize(), 63);
		hitter.addActor(_img);
		
		_img = new Image(loadTexture("health_l2.png"));
		_img.setPosition(0, -256, Align.center);
		hitter.addActor(_img);
		
		stage.addActor(hitter);
	}
	
	private Texture loadTexture(String path) {
		Texture _tex = new Texture(path);
		_tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return _tex;
	}
	
	private float timeGaugeSize() {
		return timeLeft / 10f * 788f;
	}
	
	private float hitGaugeSize() {
		return StaticData.HIT / 100f * 790f;
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
		shake = 0;
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
		
		game.cam.position.x = MathUtils.random(-shake, shake);
		game.cam.position.y = MathUtils.random(-shake, shake);
		shake -= delta * 10;
		if (shake <= 0.5f) shake = 0;
		
		comboTime -= delta;		
		if (comboTime <= 0) comboTime = -1;
		
		if (comboTime <= 0 && combo > 0) {
			combo = 0;
			comboUI.addAction(Actions.alpha(0, 0.05f));
		}
		
		timeLabel.setText("" + ((int) timeLeft + 1));
		
		hitter.setPosition(MathUtils.random(-hitShake, hitShake), MathUtils.random(-hitShake, hitShake));
		hitShake -= delta * 10;
		if (hitShake <= 0.5f) hitShake = 0;
		
		if (currentState == State.STANDBY && iteration < StaticData.LEVEL) {
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
				
				star.addAction(Actions.parallel(
					Actions.scaleTo(1.2f, 1.2f, 0.4f, Interpolation.pow2),
					Actions.rotateBy(-150f, 0.8f, Interpolation.pow3Out)
				));
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
		if (currentState == State.RESULT && result != -2 && enemys != null) {
			if (result == 0) {
				result = -2;
				enemys.addAction(Actions.sequence(
					new Action() {
						@Override
						public boolean act(float delta) {
							choiceGoBack();
							StaticData.HIT -= 1;
							if (hitShake == 0) hitShake = 3;
							comboTime = -1;
							return true;
						}
					},
					Actions.delay(0.2f),
					new Action() {
						@Override
						public boolean act(float delta) {
							toChoosingState();
							return true;
						}
					}
				));
			}
			
			else if (result == -1) {
				result = -2;
				iteration++;
				enemys.addAction(Actions.sequence(
					Actions.moveToAligned(0, -10, Align.center, 0.25f, Interpolation.pow5),
					Actions.scaleTo(0, 0),
					new Action() {
						@Override
						public boolean act(float delta) {
							StaticData.HIT -= 5;
							if (hitShake == 0) hitShake = 4;
							comboTime = -1;
							choiceGoBack();
							toStandbyState();
							return true;
						}
					}
				));
			}
			
			else if (result == 1) {
				result = -2;
				iteration++;
				enemys.addAction(Actions.sequence(
					new Action() {
						public boolean act(float delta) {
							if (shake == 0) shake = 5;
							StaticData.SCORE += 10;
							comboTime = 1f;
							if (comboTime > 0) combo++;
							StaticData.SCORE += combo > 1 ? combo : 0;
							
							comboLabel.setText("" + combo);
							if (combo >= 2) {
								comboUI.addAction(Actions.sequence(
									Actions.scaleTo(1.35f, 1.35f),
									Actions.alpha(1),
									Actions.scaleTo(1, 1, 0.1f, Interpolation.pow2Out)
								));
							}
							
							scoreLabel.setText("Score:\r\n" + StaticData.SCORE);
							return true;
						}
					},
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
		if (currentState == State.TRANSITION && star.getScaleX() >= 1.2f) {
			stage.addAction(Actions.sequence(
				Actions.delay(0.5f),
				new Action() {
					public boolean act(float delta) {
						star.addAction(Actions.parallel(
							Actions.fadeOut(0.5f),
							Actions.scaleTo(0f, 0f, 0.7f, Interpolation.pow2Out),
							Actions.rotateBy(-150f, 5f, Interpolation.pow2Out)
						));
						return true;
					}
				},
				Actions.delay(1.5f),
				new Action() {
					public boolean act(float delta) {
						dispose();
						game.setScreen(game.screenStack.pop());
						return true;
					}
				}
			));
		}
		
		if (iteration >= StaticData.LEVEL) {
			currentState = State.TRANSITION;
		}
		
		if (timeLeft <= 0) {
			timeLeft = 0;
			StaticData.HIT -= 1;
		}
		
		float targetSize = hitGauge.getWidth() + (hitGaugeSize() - hitGauge.getWidth()) * 0.3f;
		hitGauge.setSize(targetSize, hitGauge.getHeight());
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
