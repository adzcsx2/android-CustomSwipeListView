package com.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.viewdrawhelper.FramDrawLayout;
import com.example.viewdrawhelper.R;

public class MainActivity extends Activity {
	private ListView lv;
	private MyAdapter adapter;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Button btn ;
	private FramDrawLayout layout;
	private boolean isOpen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		btn = (Button) findViewById(R.id.btn);
		layout = (FramDrawLayout) findViewById(R.id.framLyout);
		lv = (ListView) findViewById(R.id.lv);
		adapter = new MyAdapter();
		lv.setAdapter(adapter);
		for (int i = 0; i < 40; i++) {
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("name", i+++"");
			map.put("isOpen", false);
			list.add(map);
		}
		layout.setActvity(MainActivity.this);
		adapter.setData(list);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!isOpen){
					layout.Open();
					isOpen = true;
				}else {
					layout.close();
					isOpen = false;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
