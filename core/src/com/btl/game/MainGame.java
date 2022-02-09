package com.btl.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {
	SpriteBatch batch;
	public static final  int PIM = 45;
	public static AssetManager manager;

	public static float vToc = 5;




	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("Music/song.mp3", Music.class);
		manager.load("Music/jump.mp3", Sound.class);
		manager.load("Music/death2.mp3", Sound.class);
		manager.load("Music/die.mp3", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}


	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
