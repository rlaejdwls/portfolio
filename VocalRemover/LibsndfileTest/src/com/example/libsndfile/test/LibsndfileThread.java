package com.example.libsndfile.test;

import com.example.libsndfile.test.event.OnCallbackThreadEventListener;
import com.open.lib.sndfile.LibsndfileWrapper;

public class LibsndfileThread extends Thread {
	private OnCallbackThreadEventListener onCallbackThreadEventListener = null;
	
	private LibsndfileWrapper wrapper;
	private String inputPath = "";
	private String outputPath = "";
	private float volume = 0f;
	
	public LibsndfileThread(String inputPath, String outputPath) {
		this(inputPath, outputPath, 2.2f);
	}

	public LibsndfileThread(String inputPath, String outputPath, float volume) {
		wrapper = new LibsndfileWrapper();
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.volume = volume;
	}
	
	public void setOnCallbackThreadEventListener(OnCallbackThreadEventListener l) {
		this.onCallbackThreadEventListener = l;
	}
	
	@Override
	public void run() {
		int result = wrapper.callEffectInvert(inputPath, outputPath, volume);
		
		if (onCallbackThreadEventListener != null) {
			onCallbackThreadEventListener.OnCallbackThreadEvent(result);
		}
	}
}
