package com.example.viewdrawhelper;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CustomSwipeAdapter extends BaseAdapter{

	private Map<Integer, Object> relativeDrawLayout_map = new HashMap<Integer, Object>();
	
	public abstract int getCount();
	public abstract Object getItem(int position);
	public abstract long getItemId(int position);
	public abstract View getSwipeView(int position, View convertView, ViewGroup parent);
	
	public RelativeDrawLayout getViewAtPosition(int position){
		return (RelativeDrawLayout) relativeDrawLayout_map.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		relativeDrawLayout_map.put(position, convertView);
		return getSwipeView(position, convertView, parent);
	}

}
