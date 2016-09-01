package kr.ac.gachon.game.pingpong.handler;

import kr.ac.gachon.game.pingpong.event.OnBluetoothConnectedCallBack;
import kr.ac.gachon.game.pingpong.event.OnBluetoothMessageReadCallBack;
import kr.ac.gachon.game.pingpong.event.model.BluetoothMessageReadEvent;
import kr.ac.gachon.game.pingpong.util.BluetoothCommService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothHandler extends Handler {
    // Debugging
    private static final String TAG = "BluetoothCommService";
    private static final boolean D = true;
    
	private OnBluetoothConnectedCallBack onBluetoothConnected;
	private OnBluetoothMessageReadCallBack onBluetoothMessageRead;
	
	public void setOnBluetoothConnectedEvent(
			OnBluetoothConnectedCallBack onBluetoothConnectedEvent) {
		this.onBluetoothConnected = onBluetoothConnectedEvent;
	}
	public void setOnBluetoothMessageRead(
			OnBluetoothMessageReadCallBack onBluetoothMessageRead) {
		this.onBluetoothMessageRead = onBluetoothMessageRead;
	}

	@Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case BluetoothCommService.MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                    case BluetoothCommService.STATE_CONNECTED:
                    	if (onBluetoothConnected != null)
                    		onBluetoothConnected.onBluetoothConnectedCallBackEvent();
                        break;
                    case BluetoothCommService.STATE_CONNECTING:
                        break;
                    case BluetoothCommService.STATE_LISTEN:
                    	break;
                    case BluetoothCommService.STATE_NONE:
                        break;
                }
                break;
            case BluetoothCommService.MESSAGE_WRITE:
                break;
            case BluetoothCommService.MESSAGE_READ:
            	if (onBluetoothMessageRead != null) {
	                BluetoothMessageReadEvent event = new BluetoothMessageReadEvent(msg.obj);
	                onBluetoothMessageRead.OnBluetoothMessageReadCallBackEvent(event);
            	}
                break;
            case BluetoothCommService.MESSAGE_DEVICE_NAME:
                //save the connected device's name
                //String mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
//                Toast.makeText(getApplicationContext(), "Connected to "
//                        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case BluetoothCommService.MESSAGE_TOAST:
//                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
//                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
