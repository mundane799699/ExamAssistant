package com.mundane.examassistant.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.mundane.examassistant.utils.LogUtils;

/**
 * Created by mundane on 2017/8/12.
 */

public class Mscroller extends Scroller{
    private boolean mNoDuration;

    public Mscroller(Context context) {
        super(context);
    }


    public Mscroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }


    public Mscroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		LogUtils.d("ViewPager's Scroll duration = " + duration);
        if (mNoDuration) {
            super.startScroll(startX, startY, dx, dy, 0);
        } else {
            super.startScroll(startX, startY, dx, dy, 500);
        }
    }


    public void setNoDuration(boolean noDuration) {
        mNoDuration = noDuration;
    }
}
