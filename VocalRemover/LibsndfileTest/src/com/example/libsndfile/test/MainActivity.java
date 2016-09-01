package com.example.libsndfile.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.example.libsndfile.test.event.OnCallbackThreadEventListener;
import com.open.lib.sndfile.LibsndfileWrapper;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnCall = (Button)this.findViewById(R.id.btnExecute);
		Button btnNormalizingCall = (Button) this.findViewById(R.id.btnNormalize);
		Button btnOpen = (Button)this.findViewById(R.id.btnOpen);
		Button btnSave = (Button)this.findViewById(R.id.btnSave);
		final EditText txtInputPath = (EditText) this.findViewById(R.id.txtInputPath);
		final EditText txtOutputPath = (EditText) this.findViewById(R.id.txtOutputPath);
		final ProgressBar progressCircle = (ProgressBar) this.findViewById(R.id.progressBar);
		final SeekBar seekBar = (SeekBar) this.findViewById(R.id.seekBar);
		
		progressCircle.setVisibility(View.INVISIBLE);
		seekBar.setMax(5);
		seekBar.setProgress(2);
		
		//Open 창 불러오기
		btnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleFileDialog FileOpenDialog = new SimpleFileDialog(MainActivity.this, "FileOpen", new SimpleFileDialog.SimpleFileDialogListener() { 
					@Override public void onChosenDir(String chosenDir) { 
						txtInputPath.setText(chosenDir);  
					}
				}); 
				FileOpenDialog.Default_File_Name = ""; 
				FileOpenDialog.chooseFile_or_Dir();
			}
		});
		
		//Save 창 불러오기
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleFileDialog FileSaveDialog = new SimpleFileDialog(MainActivity.this, "FileSave", new SimpleFileDialog.SimpleFileDialogListener() {
					@Override
					public void onChosenDir(String chosenDir) {
						txtOutputPath.setText(chosenDir);
					}
				});
				FileSaveDialog.Default_File_Name = "";
				FileSaveDialog.chooseFile_or_Dir();
			}
		});
		
		//변환 처리
		OnClickListener onClickEvent = new OnClickListener() {
			@Override
			public void onClick(View v) {
				//처리 스레드 생성
				LibsndfileThread thread = null;
				if (v.getId() == R.id.btnExecute) {
					thread = new LibsndfileThread(txtInputPath.getText().toString().trim(), 
							txtOutputPath.getText().toString().trim());
				} else if (v.getId() == R.id.btnNormalize) {
					float volume = (seekBar.getProgress() + 20.0f) / 10.0f;
					
					thread = new LibsndfileThread(txtInputPath.getText().toString().trim(), 
							txtOutputPath.getText().toString().trim(), volume);
				}
				
				//콜백 스레드에 이벤트 등록
				thread.setOnCallbackThreadEventListener(new OnCallbackThreadEventListener() {
					@Override
					public void OnCallbackThreadEvent(int result) {
						String msg = "";
						
						//변환 결과에 따른 처리
						switch (result) {
						case LibsndfileWrapper.CONVERT_SUCCESS:
							msg = "파일 변환 성공";
							break;
						case LibsndfileWrapper.FILE_FORMAT_NOT_SUPPORT:
							msg = "지원하지 않는 포멧입니다.";
							break;
						case LibsndfileWrapper.FILE_NOT_STEREO:
							msg = "스테레오 형식의 음악 파일이 아닙니다.";
							break;
						case LibsndfileWrapper.INPUT_FILE_OPEN_FAIL:
							msg = "파일을 여는데 실패했습니다.";
							break;
						case LibsndfileWrapper.OUTPUT_FILE_OPEN_FAIL:
							msg = "파일을 쓰는데 실패했습니다.";
						}
						
						final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);
						myAlertDialog.setTitle("알림");
						myAlertDialog.setMessage(msg);
						myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
							public void onClick(DialogInterface arg0, int arg1) {
								// do something when the OK button is clicked
							}});
						runOnUiThread(new Runnable() {
			                @Override
			                public void run() {
			                	progressCircle.setVisibility(View.INVISIBLE);
			                	myAlertDialog.show();
			                }
			            });
					}
				});
				
				//변환 시작
				thread.start();
				progressCircle.setVisibility(View.VISIBLE);
			}
		};
		
		//변환 처리 이벤트 등록
		btnCall.setOnClickListener(onClickEvent);
		btnNormalizingCall.setOnClickListener(onClickEvent);
	}
}
