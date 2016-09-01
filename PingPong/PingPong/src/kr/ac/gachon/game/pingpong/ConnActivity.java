package kr.ac.gachon.game.pingpong;

import kr.ac.gachon.game.pingpong.controller.Controller;
import kr.ac.gachon.game.pingpong.controller.SoundManager;
import kr.ac.gachon.game.pingpong.event.OnBluetoothConnectedCallBack;
import kr.ac.gachon.game.pingpong.util.BluetoothCommService;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ConnActivity extends Activity {

    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private Button mConnButton;
    private Button mDiscoverButton;
    
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothCommService mCommService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);
        
		//sound init
		SoundManager.getManager().init(this);
		SoundManager.getManager().addSound("hit", R.raw.hit);
		SoundManager.getManager().addSound("bgm", R.raw.music3);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "블루투스 사용불가", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mConnButton = (Button) findViewById(R.id.conn);
        mConnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				Controller.getInstance().getPlayer().setNum((byte) 1);
                Intent serverIntent = null;
                serverIntent = new Intent(ConnActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
			}
        });
        mDiscoverButton= (Button) findViewById(R.id.Discover);
        mDiscoverButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
                ensureDiscoverable();
			}
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mCommService == null) setupComm();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (mCommService != null) {
            if (mCommService.getState() == BluetoothCommService.STATE_NONE) {
                mCommService.start();
            }
        }
    }

    private void setupComm() {
        // Initialize the BluetoothCommService to perform bluetooth connections
        //서비스 싱글톤 패턴 변경 전
        //mCommService = new BluetoothCommService(this, mHandler);
        mCommService = BluetoothCommService.getService(/*mHandler*/);
        mCommService.mHandler.setOnBluetoothConnectedEvent(new OnBluetoothConnectedCallBack() {
			@Override
			public void onBluetoothConnectedCallBackEvent() {
				Intent intent = new Intent(ConnActivity.this, GameActivity.class);
	            startActivity(intent);
			}
		});
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mCommService != null) mCommService.stop();
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
            startActivity(discoverableIntent);
        }
    }


    /*
    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        sendMessage(message);
                    }
                    if(D) Log.i(TAG, "END onEditorAction");
                    return true;
                }
            };
*/
    
//    public Handler getmHandler() {
//        return mHandler;
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupComm();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BLuetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mCommService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
