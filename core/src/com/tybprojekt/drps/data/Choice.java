package com.tybprojekt.drps.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public enum Choice {
	ROCK("c_batu.png"),
	PAPER("c_kertas.png"),
	SCISSORS("c_gunting.png");
	
	public Texture sprite;
	
	Choice(String path) {
		this.sprite = new Texture(path);
		sprite.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}
