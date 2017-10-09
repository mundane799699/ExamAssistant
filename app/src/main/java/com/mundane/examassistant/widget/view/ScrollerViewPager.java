package com.mundane.examassistant.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mundane.examassistant.widget.Mscroller;
import java.lang.reflect.Field;

/**
 * Created by mundane on 2017/8/12.
 */

public class ScrollerViewPager extends ViewPager {

    private Mscroller mScroller;


    public ScrollerViewPager(Context context) {
        super(context);
        init();
    }


    public ScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public interface OnActionListener{
		void onActionUp();

		void onActionDown();
	}

	private OnActionListener mOnActionListener;

	public void setOnActionListener(OnActionListener onActionListener) {
		mOnActionListener = onActionListener;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_OUTSIDE:
				if (mOnActionListener != null) {
					mOnActionListener.onActionUp();
				}
				break;
			case MotionEvent.ACTION_DOWN:
				if (mOnActionListener != null) {
					mOnActionListener.onActionDown();
				}
				break;
			default:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private void init() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new Mscroller(getContext());
            mField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setNoDuration(boolean noDuration) {
        mScroller.setNoDuration(noDuration);
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
