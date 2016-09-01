package kr.ac.gachon.game.pingpong.controller;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private static SoundManager manager;
	private Context context;
	private SoundPool sound;
	private HashMap<String, Integer> mapSound = new HashMap<String, Integer>();
	
	public synchronized static SoundManager getManager() {
		if (manager == null) {
			manager = new SoundManager();
		}
		return manager;
	}
	
	private SoundManager() {
		sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
	}
	
	public void init(Context context) {
		this.context = context;
	}
	
	public void addSound(String key, int id) {
		mapSound.put(key, sound.load(context, id, 1));
	}
	
	public void play(String key) {
		this.play(mapSound.get(key), 0.6F, 0.6F,  1,  0,  1.0F);
	}
	public void play(String key, float leftVolume, float rightVolume, int priority, int loop, float rate) {
		this.play(mapSound.get(key), leftVolume, rightVolume, priority,  loop,  rate);
	}
	public void play(int id, float leftVolume, float rightVolume, int priority, int loop, float rate) {
		sound.play(id, leftVolume, rightVolume,  priority, loop,  rate);
	}
	
	public void stop(String key) {
		this.stop(mapSound.get(key));
	}
	public void stop(int id) {
		sound.stop(id);
	}
}
