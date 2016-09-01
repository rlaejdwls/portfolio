package kr.ac.gachon.game.pingpong.model;

import kr.ac.gachon.game.pingpong.data.Ball;

import com.google.gson.annotations.Expose;

public class SendMessageModel {
	@Expose private GameState state;
	@Expose private Ball ball;
	
	public SendMessageModel(GameState state, Ball ball) {
		super();
		this.state = state;
		this.ball = ball;
	}
	
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}

	@Override
	public String toString() {
		return "SendMessageModel [state=" + state + ", ball=" + ball + "]";
	}
}
