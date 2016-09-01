package kr.co.treegaems.tableviewer.event.model;

public class HttpConnectEvent {
	private byte[] result;

	public HttpConnectEvent(byte[] result) {
		super();
		this.result = result;
	}

	public byte[] getResultToByte() {
		return result;
	}
	public String getResultToString() {
		return new String(result);
	}
	public void setResult(byte[] result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "HttpConnectEvent [result=" + result + "]";
	}
}
