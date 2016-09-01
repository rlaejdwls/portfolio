package kr.ac.gachon.game.pingpong.event.model;

public class BluetoothMessageReadEvent {
	private Object object;

	public BluetoothMessageReadEvent(Object object) {
		super();
		this.object = object;
	}

	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "BluetoothMessageReadEvent [object=" + object + "]";
	}
}
