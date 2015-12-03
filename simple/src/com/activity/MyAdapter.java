package com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewdrawhelper.CustomSwipeAdapter;
import com.example.viewdrawhelper.R;
import com.example.viewdrawhelper.RelativeDrawLayout;

public class MyAdapter extends CustomSwipeAdapter {
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Toast mToast;
	private Map<Integer, Object> relativeDrawLayout_map = new HashMap<Integer, Object>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public RelativeDrawLayout getViewAtPosition(int position) {
		return (RelativeDrawLayout) relativeDrawLayout_map.get(position);
	}

	public void setData(List<Map<String, Object>> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	private class Holder {
		Button btn_qulified, btn_no_ulified;
		TextView myText;
	}

	@Override
	public View getSwipeView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.main, parent, false);

			mToast = Toast
					.makeText(parent.getContext(), "", Toast.LENGTH_SHORT);
			holder = new Holder();
			holder.btn_no_ulified = (Button) convertView
					.findViewById(R.id.btn_no_ulified);
			holder.btn_qulified = (Button) convertView
					.findViewById(R.id.btn_qulified);
			holder.myText = (TextView) convertView.findViewById(R.id.myText);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		relativeDrawLayout_map.put(position, convertView);

		holder.myText.setText(list.get(position).get("name").toString());

		holder.btn_no_ulified.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mToast.setText("不合格");
				mToast.show();
			}
		});
		holder.btn_qulified.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mToast.setText("合格");
				mToast.show();
			}
		});
		holder.myText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mToast.setText("点击item");
				mToast.show();
			}
		});
		return convertView;

	}

}
