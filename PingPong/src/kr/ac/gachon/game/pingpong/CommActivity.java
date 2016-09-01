package kr.ac.gachon.game.pingpong;

import kr.ac.gachon.game.pingpong.model.TestModel;
import kr.ac.gachon.game.pingpong.util.BluetoothCommService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommActivity extends Activity {

    private Button mSendButton;
    private BluetoothCommService mCommService = null;
    private EditText editId;
    private EditText editSpeed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm);

        mCommService = BluetoothCommService.getService();
        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
                // Send a message using content of the edit text widget
                editId = (EditText)findViewById(R.id.editId);
                String id = editId.getText().toString();
                editSpeed = (EditText)findViewById(R.id.editSpeed);
                String speed = editSpeed.getText().toString();
                float speedF = Float.parseFloat(speed);
//                TestModel TM = new TestModel(10f, id, speedF);
//                sendObject(TM);
			}
        });
     }

    private void sendObject(TestModel TM) {
        // Check that we're actually connected before trying anything
        if (mCommService.getState() != BluetoothCommService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
//        mCommService.write(TM);
        editId.setText(null);
    }
}
