package com.example.viewdrawhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class RelativeDrawLayout extends RelativeLayout {

	private static final String TAG = "RelativeDrawLayout";
	protected static final long RECOVER_TIME = 3000;
	private ViewDragHelper dragHelper;
	private View content;
	private View hideView;
	View releasedChild_view;
	private int layout_top;
	private boolean isOpen = false;
	private float first_left = 0, end_left = 0;
	private boolean isFirstTouch = true;

	public RelativeDrawLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public RelativeDrawLayout(Context context) {
		super(context);
		initView();
	}

	public RelativeDrawLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		dragHelper = ViewDragHelper.create(this, new Callback() {

			@Override
			public boolean tryCaptureView(View arg0, int arg1) {

				return arg0 == content;
			}

			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
				if (left < -hideView.getWidth()) {
					return -hideView.getWidth();
				}
				if (left > getWidth() - child.getWidth()) {
					return getWidth() - child.getWidth();
				}
				return left;
			}

			@Override
			public int getViewHorizontalDragRange(View child) {
				return hideView.getWidth();
			}

			int last_width = 0;

			@Override
			public void onViewPositionChanged(View changedView, int left,
					int top, int dx, int dy) {
				super.onViewPositionChanged(changedView, left, top, dx, dy);
			}

			@Override
			public void onViewReleased(final View releasedChild,
					final float xvel, float yvel) {
				isFirstTouch = true;
				final int childWidth = hideView.getWidth();
				float offset_x = end_left - first_left;
				layout_top = releasedChild.getTop();
				releasedChild_view = releasedChild;
				int width = childWidth;
				if (xvel > 500 || offset_x >= width / 4) {
					width = 0;
					isOpen = false;
				} else if (xvel < -500 || offset_x <= -width / 4) {
					width = -childWidth;
					isOpen = true;
				} else {
					width = last_width;
					isOpen = true;
				}
				dragHelper.settleCapturedViewAt(width, layout_top);
				last_width = width;
				invalidate();

			}
		});
		dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (isFirstTouch) {
				first_left = event.getX();
				isFirstTouch = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			end_left = event.getX();
			isFirstTouch = true;
			break;

		default:
			break;
		}
		dragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return dragHelper.shouldInterceptTouchEvent(ev);
	}

	float dispatch_x = 0, dispatch_y = 0;

	boolean isTouch = false;
	boolean isDispatch = false;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dispatch_x = event.getX();
			dispatch_y = event.getY();
			isTouch = true;
			break;
		case MotionEvent.ACTION_MOVE:
			if (isTouch) {
				isTouch = false;
				float last_x = event.getX();
				float last_y = event.getY();

				float off_x = Math.abs(last_x - dispatch_x);
				float off_y = Math.abs(last_y - dispatch_y);

				if (off_y + 10 >= off_x) {
					isDispatch = false;
				} else {
					isDispatch = true;
				}
			}
			break;
		default:
			break;
		}
		// 拦截左右不拦截上下
		if (isDispatch) {
			getParent().requestDisallowInterceptTouchEvent(true); // 让父类不拦截触摸事件
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height = getChildAt(0).getMeasuredHeight();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (dragHelper.continueSettling(true)) {
			invalidate();
			// ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		content = getChildAt(1);
		hideView = getChildAt(0);
	}

	public void Open() {
		isOpen = true;
		dragHelper.smoothSlideViewTo(content, -hideView.getWidth(),
				content.getTop());
		invalidate();
	}

	public void close() {
		isOpen = false;
		dragHelper.smoothSlideViewTo(content, 0, content.getTop());
		invalidate();
	}

	public boolean isOpening() {
		return isOpen;
	}

	public boolean isClosed() {
		return !isOpen;
	}

}
