package com.mundane.examassistant.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mundane.examassistant.utils.DensityUtils;

/**
 * @author : mundane
 * @time : 2017/7/11 20:10
 * @description :
 * @file : TitleItemDecoration.java
 */

public class BottomSheetItemDecoration extends RecyclerView.ItemDecoration {

	private final int mDividerHeight;

	public BottomSheetItemDecoration(Context context) {
		mDividerHeight = DensityUtils.dp2px(context, 10);
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		outRect.top = mDividerHeight;
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		int spanCount = getSpanCount(parent);
		int childCount = parent.getAdapter().getItemCount();
		if (isLastRow(parent, itemPosition, spanCount, childCount)) {
			// 如果是最后一行，则还需要绘制底部
			outRect.bottom = mDividerHeight;
		}
	}

	private int getSpanCount(RecyclerView parent) {
		int spanCount = 0;
		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
		}
		return spanCount;
	}

	private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			int maxNotLastRawPosition = childCount - childCount % spanCount;
			if (pos >= maxNotLastRawPosition) {
				return true;
			}
		}
		return false;
	}

}
