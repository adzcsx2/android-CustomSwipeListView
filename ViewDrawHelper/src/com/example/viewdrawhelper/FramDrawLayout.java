package com.example.viewdrawhelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class FramDrawLayout extends FrameLayout {

	private static final String TAG = "FramDrawLayout";
	private static final float LEFT_TOUCH_AREA = 20f;
	public static final float ALPHA = 0.3f;
	private ViewDragHelper dragHelper;
	private View content;
	private View shadow;
	private View leftMenu;
	private float mLeftMenuOnScrren;
	private Activity activity;

	private boolean isOpen = false;

	public FramDrawLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public FramDrawLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public FramDrawLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public void setActvity(Activity activity) {
		this.activity = activity;
	}

	private void initView() {
		dragHelper = ViewDragHelper.create(this, new Callback() {

			@Override
			public boolean tryCaptureView(View arg0, int arg1) {
				// TODO Auto-generated method stub
				return arg0 == leftMenu;
			}

			@Override
			public void onEdgeTouched(int edgeFlags, int pointerId) {
				// TODO Auto-generated method stub
				super.onEdgeTouched(edgeFlags, pointerId);
			}

			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
				// TODO Auto-generated method stub
				int newLeft = Math.max(-leftMenu.getWidth(), Math.min(left, 0));
				return newLeft;
			}

			@Override
			public void onEdgeDragStarted(int edgeFlags, int pointerId) {
				// TODO Auto-generated method stub
				super.onEdgeDragStarted(edgeFlags, pointerId);
				dragHelper.captureChildView(leftMenu, pointerId);
			}

			@Override
			public void onViewDragStateChanged(int state) {
				// TODO Auto-generated method stub
				super.onViewDragStateChanged(state);
			}

			@Override
			public int getViewHorizontalDragRange(View child) {
				return leftMenu == child ? child.getWidth() : 0;
			}

			float alpha;

			@Override
			public void onViewPositionChanged(View changedView, int left,
					int top, int dx, int dy) {
				// TODO Auto-generated method stub
				super.onViewPositionChanged(changedView, left, top, dx, dy);
				Log.i("-------", "left = " + left + "---top=" + top + "----dx"
						+ dx);
			
				if (changedView == content) {
					leftMenu.offsetLeftAndRight(dx);
				}
				float offset = (float) (changedView.getWidth() + left)
						/ changedView.getWidth();
				mLeftMenuOnScrren = offset;
				// 0完全透明 1 不透明
				// content.setAlpha(1-mLeftMenuOnScrren+0.3f);
				alpha = mLeftMenuOnScrren - ALPHA;
				if (alpha < 0)
					alpha = 0;
				if (alpha == 0) {
					isOpen = false;
				} else if (alpha == 1-ALPHA) {
					isOpen = true;
				}
				shadow.setAlpha(alpha);
				invalidate();
			}

			@Override
			public void onViewReleased(View releasedChild, float xvel,
					float yvel) {
				// final int childWidth = leftMenu.getWidth();
				// float offset = (childWidth + releasedChild.getLeft()) * 1.0f
				// / childWidth;
				// Log.e("+++++", "xvel = "+xvel);
				// dragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0
				// && offset > 0.5f ? 0 : -childWidth,
				// releasedChild.getTop());
				// // hideView.layout(getWidth()-hideView.getWidth(),
				// hideView.getTop(), getWidth(), hideView.getBottom());
				// invalidate();
				//
				// L.e("onViewReleased");
				final int childWidth = releasedChild.getWidth();
				float offset = (childWidth + releasedChild.getLeft()) * 1.0f
						/ childWidth;
				dragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0
						&& offset > 0.5f ? 0 : -childWidth,
						releasedChild.getTop());

				invalidate();

			}
		});

		dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
		// dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		dragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getX() < LEFT_TOUCH_AREA) {
			return true;
		}
		if (isOpen) {
			return true;
		}

		return dragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int height = getChildAt(0).getMeasuredHeight();
		LayoutParams layoutParams = (LayoutParams) leftMenu.getLayoutParams();
		layoutParams.height = height;
		layoutParams.width = (int) (getChildAt(0).getMeasuredWidth() * 0.7);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		leftMenu.layout(-leftMenu.getWidth(), leftMenu.getTop(), 0,
				leftMenu.getBottom());
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
		content = getChildAt(0);
		shadow = getChildAt(1);
		leftMenu = getChildAt(2);
	}

	public void Open() {
		isOpen = true;
		dragHelper.smoothSlideViewTo(leftMenu, 0, leftMenu.getTop());
		invalidate();
	}

	public void close() {
		isOpen = false;
		dragHelper.smoothSlideViewTo(leftMenu, -leftMenu.getWidth(),
				leftMenu.getTop());
		invalidate();
	}

	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		activity.getWindow().setAttributes(lp);
	}

	public boolean isOpening() {
		return isOpen;
	}

	public boolean isClosed() {
		return !isOpen;
	}
}
