package com.mundane.examassistant.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mundane.examassistant.R;
import com.mundane.examassistant.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : mundane
 * @time : 2017/4/11 16:06
 * @description : 简易的圆点指示器
 * @file : DotIndicater.java
 */

public class SimpleDotIndicater extends LinearLayout{
	private Context mContext;
	public SimpleDotIndicater(Context context) {
		this(context, null);
	}

	public SimpleDotIndicater(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SimpleDotIndicater(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
	}

	private final int DEFAULT_TOP_MARGIN = 10;
	private final int DEFAULT_LEFT_MARGIN = 5;
	private int mCurrentPosition;
	private List<ImageView> mViewList;


	public void setDotsCount(int childCount) {
		mViewList = new ArrayList<>();
		int leftMargin = DensityUtils.dp2px(mContext, DEFAULT_LEFT_MARGIN);
		int topMargin = DensityUtils.dp2px(mContext, DEFAULT_TOP_MARGIN);
		for (int i = 0; i < childCount; i++) {
			ImageView imageView = new ImageView(mContext);
			mViewList.add(imageView);
			if (i == 0) {
				imageView.setBackgroundResource(R.drawable.shape_round_purple);
				mCurrentPosition = i;
			} else {
				imageView.setBackgroundResource(R.drawable.shape_round_white);
			}

			LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(leftMargin, topMargin, leftMargin, topMargin);
			addView(imageView, params);
		}

	}

	public void UpdateDotsState(int position) {
		mViewList.get(mCurrentPosition).setBackgroundResource(R.drawable.shape_round_white);
		mViewList.get(position).setBackgroundResource(R.drawable.shape_round_purple);
		mCurrentPosition = position;
	}



}
