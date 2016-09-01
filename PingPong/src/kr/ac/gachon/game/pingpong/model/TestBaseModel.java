package kr.ac.gachon.game.pingpong.model;

import java.io.Serializable;

public class TestBaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float position;

	public TestBaseModel(float position) {
		super();
		this.position = position;
	}

	public float getPosition() {
		return position;
	}
	public void setPosition(float position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "TestBaseModel [position=" + position + "]";
	}
}
