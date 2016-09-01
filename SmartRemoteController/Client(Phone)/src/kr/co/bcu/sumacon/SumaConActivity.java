package kr.co.bcu.sumacon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SumaConActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.btnConnect);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String ipAddress = ((EditText)findViewById(R.id.edtxtIp)).getText().toString();
		        String port = ((EditText)findViewById(R.id.edtxtPort)).getText().toString();
		        
				Intent intent = new Intent(SumaConActivity.this, RemoconActivity.class);
		        intent.putExtra("IP", ipAddress);
		        intent.putExtra("PORT", port);
		        
		        startActivity(intent);
			}
        });
    }
}