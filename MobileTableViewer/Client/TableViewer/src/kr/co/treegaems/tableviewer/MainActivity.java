package kr.co.treegaems.tableviewer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.co.treegaems.tableviewer.custom.ListViewAdapter;
import kr.co.treegaems.tableviewer.event.OnHttpConnectCallBack;
import kr.co.treegaems.tableviewer.event.model.HttpConnectEvent;
import kr.co.treegaems.tableviewer.model.ColumnInfo;
import kr.co.treegaems.tableviewer.model.StructureInfo;
import kr.co.treegaems.tableviewer.model.TableInfo;
import kr.co.treegaems.tableviewer.util.HttpConnect;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final MainActivity main = this;
	private StructureInfo info = null;
	
	private ListView lstColumnView = null;
	private ListViewAdapter lstColumnAdapter = null;
	
	private EditText driver;
	private EditText url;
	private EditText userName;
	private EditText password;
	private EditText ip;
	
	private String xml;
	private int currentPosition = -1;
	
	public byte[] decrypt(byte[] bytes, byte[] pwd) {
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] ^ pwd[i % pwd.length]);
		}
		
		return bytes;
	}

	private OnClickListener onClickLisener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onTableList();
		}
	};
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			main.setContentView(R.layout.activity_table_info);
			
			((Button) findViewById(R.id.btnPrev)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (0 < currentPosition) {
						onColumnInfo(--currentPosition);
					}
				}
			});
			((Button) findViewById(R.id.btnGoTableList)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onTableList();
				}
			});
			((Button) findViewById(R.id.btnNext)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (info.getTableInfos().size()-1 > currentPosition) {
						onColumnInfo(++currentPosition);
					}
				}
			});
			
			onColumnInfo(position);
			currentPosition = position;
		}
	};
	
	public void onTableList() {
		main.setContentView(R.layout.activity_table_list);
		
		String body = "";
		try {
			body = URLEncoder.encode("driver", "utf-8") + "=" + URLEncoder.encode(driver.getText().toString(), "utf-8");
			body += "&" + URLEncoder.encode("url", "utf-8") + "=" + URLEncoder.encode(url.getText().toString(), "utf-8");
			body += "&" + URLEncoder.encode("username", "utf-8") + "=" + URLEncoder.encode(userName.getText().toString(), "utf-8");
			body += "&" + URLEncoder.encode("password", "utf-8") + "=" + URLEncoder.encode(password.getText().toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		new HttpConnect(new OnHttpConnectCallBack() {
			@Override
			public void onHttpConnectCallBackEvent(HttpConnectEvent event) {
				Serializer serializer = new Persister();
				
				xml = new String(decrypt(event.getResultToByte(), password.getText().toString().getBytes()));

				try {
					info = serializer.read(StructureInfo.class, xml);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (info != null) {
					ArrayList<String> list = new ArrayList<String>();
					for (TableInfo table : info.getTableInfos()) {
						list.add(table.getName());
					}
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(main, android.R.layout.simple_expandable_list_item_1, list);
					ListView lstView = (ListView) main.findViewById(R.id.lstTableView);
					
					lstView.setAdapter(adapter);
					
					lstView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					lstView.setDivider(new ColorDrawable(Color.WHITE));
					lstView.setDividerHeight(2);
					
					lstView.setOnItemClickListener(onItemClickListener);
				}
			}
		}
		, "http://" + ip.getText().toString() + "/DBAnalysor/MetadataService"
		, body);
	}
	
	public void onColumnInfo(int position) {
		if (position > -1 && info.getTableInfos().size() > position) {
			TableInfo table = info.getTableInfos().get(position);

			((TextView) findViewById(R.id.txtTableName)).setText(table.getName());
			((TextView) findViewById(R.id.txtTableComment)).setText((table.getComment() != null ? table.getComment() : ""));
			lstColumnView = (ListView) findViewById(R.id.lstColumnView);
			 
			lstColumnAdapter = new ListViewAdapter(main);
			lstColumnView.setAdapter(lstColumnAdapter);
			
			lstColumnView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			lstColumnView.setDivider(new ColorDrawable(Color.WHITE));
			lstColumnView.setDividerHeight(1);
			lstColumnView.setPadding(0, 4, 0, 4);
			 
			for (ColumnInfo column : table.getColumnInfos()) {
				lstColumnAdapter.addItem(column);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		url = ((EditText) this.findViewById(R.id.edtURL));
		driver = ((EditText) this.findViewById(R.id.edtDriver));
		userName = ((EditText) this.findViewById(R.id.edtUserName));
		password = ((EditText) this.findViewById(R.id.edtPwd));
		
		ip = ((EditText) this.findViewById(R.id.edtIP));
		
		Button btnConnect = (Button) this.findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(onClickLisener);
	}
}
