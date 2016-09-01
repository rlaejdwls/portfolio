package kr.ac.gachon.game.pingpong.event.model;

public class HttpConnectEvent {
	private String result;

	public HttpConnectEvent(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "HttpConnectEvent [result=" + result + "]";
	}
}
