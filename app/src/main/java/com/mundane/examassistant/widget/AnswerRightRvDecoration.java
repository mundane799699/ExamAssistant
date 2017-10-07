package com.mundane.examassistant.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.mundane.examassistant.R;
import com.mundane.examassistant.utils.DensityUtils;

/**
 * Created by mundane on 2017/10/5 上午10:52
 */

public class AnswerRightRvDecoration extends RecyclerView.ItemDecoration {

	private final Paint mPaint;
	private int mDividerHeight;

	private int mOffset;


	public AnswerRightRvDecoration(Context context) {
		mDividerHeight = DensityUtils.dp2px(context, 1);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		mPaint.setColor(context.getResources().getColor(R.color.gray_efefef));
		mOffset = DensityUtils.dp2px(context, 20);
	}


	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
		int childCount = parent.getChildCount();
		for (int i = 1; i < childCount; i++) {
			View child = parent.getChildAt(i);
			final int left = parent.getPaddingLeft();
			final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int bottom = child.getTop() + layoutParams.topMargin;
			final int top = bottom - mDividerHeight;
			c.drawRect(left + mOffset, top, right - mOffset, bottom, mPaint);
		}


	}


	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDrawOver(c, parent, state);
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		int childAdapterPosition = parent.getChildAdapterPosition(view);
		if (childAdapterPosition != 0) {
			outRect.set(0, mDividerHeight, 0, 0);
		}
	}


}
