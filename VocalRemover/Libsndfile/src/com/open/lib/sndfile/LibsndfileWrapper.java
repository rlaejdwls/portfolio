package com.open.lib.sndfile;

public class LibsndfileWrapper {
	private Connector connector;
	
	public static final int CONVERT_SUCCESS = 0;
	public static final int INPUT_FILE_OPEN_FAIL = 1;
	public static final int OUTPUT_FILE_OPEN_FAIL = 2;
	public static final int FILE_FORMAT_NOT_SUPPORT = 3;
	public static final int FILE_NOT_STEREO = 4;
	
	public LibsndfileWrapper() {
		connector = new Connector();
	}
	
	public int callEffectInvert(String inputPath, String outputPath) {
		return connector.callLibsndjniInvert(inputPath, outputPath, 2.2f);
	}
	public int callEffectInvert(String inputPath, String outputPath, float volume) {
		return connector.callLibsndjniInvert(inputPath, outputPath, volume);
	}
}
