package com.example.viewdrawhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.activity.MyAdapter;

public class CustomListView extends ListView {

	private static final String TAG = "MyListView";
	private Context context;

	public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	int checkedItemPosition = -1, last_checkedItemPosition = -1;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			MyAdapter adapter = (MyAdapter) getAdapter();
			int count = adapter.getCount();
			for (int i = 0; i < count; i++) {
				RelativeDrawLayout relativeDrawLayout = adapter
						.getViewAtPosition(i);
				if(relativeDrawLayout!=null&&relativeDrawLayout.isOpening()){
					relativeDrawLayout.close();
				}

			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
