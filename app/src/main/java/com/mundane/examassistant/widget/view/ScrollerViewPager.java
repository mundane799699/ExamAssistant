package com.mundane.examassistant.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
