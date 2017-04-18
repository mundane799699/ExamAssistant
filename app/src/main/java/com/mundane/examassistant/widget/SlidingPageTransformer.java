package com.mundane.examassistant.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author : mundane
 * @time : 2017/4/18 14:51
 * @description :
 * @file : SlidingPageTransformer.java
 */

public class SlidingPageTransformer implements ViewPager.PageTransformer {
	@Override
	public void transformPage(View page, float position) {
		int pageWidth = page.getWidth();
		int pageHeight = page.getHeight();

		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			page.setAlpha(0);

		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			page.setAlpha(1);
			page.setTranslationX(0);
			page.setScaleX(1);
			page.setScaleY(1);

		} else if (position <= 1) { // (0,1]
			// Fade the page out.
			// Counteract the default slide transition
			page.setAlpha(1);
			page.setTranslationX(pageWidth * -position);

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			page.setAlpha(0);
		}
	}
}
