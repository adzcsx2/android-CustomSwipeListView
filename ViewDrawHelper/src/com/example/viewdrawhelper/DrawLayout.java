package com.example.viewdrawhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class DrawLayout extends LinearLayout {

	private ViewDragHelper dragHelper;
	private View content;
	private View hideView;

	public DrawLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public DrawLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public DrawLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		dragHelper = ViewDragHelper.create(this, new Callback() {

			@Override
			public boolean tryCaptureView(View arg0, int arg1) {
				// TODO Auto-generated method stub
				return arg0 == content;
			}

			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
				// TODO Auto-generated method stub
				final int leftBound = getPaddingLeft();
//				Log.e(">>>>>getPaddingLeft", leftBound + "");
//				Log.e(">>>>>getLeft", getLeft() + "");

				if (left < -hideView.getWidth()) {
					return -hideView.getWidth();
				}
				if (left > getWidth() - child.getWidth()) {
					return getWidth() - child.getWidth();
				}
				return left;
			}

			// @Override
			// public int clampViewPositionVertical(View child, int top, int dy)
			// {
			// // TODO Auto-generated method stub
			// final int toptBound = getPaddingTop();
			// final int bottom = getHeight() - child.getHeight() - toptBound;
			// final int newLeft = Math.min(Math.max(top, toptBound), bottom);
			//
			// return newLeft;
			// }
	        @Override
	        public int getViewHorizontalDragRange(View child) {
	        	Log.e(".......", hideView.getWidth() + "");
	            return hideView.getWidth();
	        }
//			@Override
//			public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//				dragHelper.captureChildView(hideView, pointerId);
//			}
			@Override
			public void onViewPositionChanged(View changedView, int left,
					int top, int dx, int dy) {
				// TODO Auto-generated method stub
				super.onViewPositionChanged(changedView, left, top, dx, dy);
				Log.e("-------", "left = "+left+"---top="+top+"----dx"+dx);
				if(changedView==content){
					hideView.offsetLeftAndRight(dx);
				}
				invalidate();
			}
			@Override
			public void onViewReleased(View releasedChild, float xvel,
					float yvel) {
				final int childWidth = hideView.getWidth();
				float offset = (childWidth + releasedChild.getLeft()) * 1.0f
						/ childWidth;
				Log.e("+++++", "xvel = "+xvel);
					dragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0
							&& offset > 0.5f ? 0 : -childWidth,
							releasedChild.getTop());
					// hideView.layout(getWidth()-hideView.getWidth(), hideView.getTop(), getWidth(), hideView.getBottom());
					invalidate();
//					if(releasedChild==hideView){
//						Log.e("hideView",  "..........");
//					}
			}
		});
			
		dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
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
		return dragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
    @Override  
    public void computeScroll()  
    {  
        super.computeScroll();  
        if(dragHelper.continueSettling(true))  
        {  
            invalidate();  
//            ViewCompat.postInvalidateOnAnimation(this);  
        }  
    }  
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        content = getChildAt(0);
        hideView =getChildAt(1);
    }
}
