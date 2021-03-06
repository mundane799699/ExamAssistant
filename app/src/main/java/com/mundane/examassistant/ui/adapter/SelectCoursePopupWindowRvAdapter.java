package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.CourseItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/11 10:03
 * @description :
 * @file : SelectCoursePopupWindowRvAdapter.java
 */

public class SelectCoursePopupWindowRvAdapter extends RecyclerView.Adapter<SelectCoursePopupWindowRvAdapter.CustomPopupWindowRvViewHolder> {

	private List<CourseItem> mList;

	public SelectCoursePopupWindowRvAdapter(List<CourseItem> list) {
		mList = list;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).isSelected) {
				mLastSelectedPosition = i;
			}
		}
	}

	public interface OnItemClickListener {
		void onItemClicked(CourseItem item);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	@Override
	public CustomPopupWindowRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_course_popupwindow_rv, parent, false);
		return new CustomPopupWindowRvViewHolder(view);
	}

	private int mLastSelectedPosition = -1;

	@Override
	public void onBindViewHolder(CustomPopupWindowRvViewHolder holder, final int position) {
		final CourseItem item = mList.get(position);
		String name = item.name;
		holder.tv.setText(name);
		holder.tv.setSelected(item.isSelected);
		if (item.isSelected) {
			mLastSelectedPosition = position;
		}
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLastSelectedPosition > -1) {
					mList.get(mLastSelectedPosition).isSelected = false;
				}
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClicked(item);
				}
//				SelectCoursePopupWindowRvAdapter.this.notifyDataSetChanged();
			}
		});
	}

	@Override
	public int getItemCount() {
		return mList == null || mList.isEmpty() ? 0 : mList.size();
	}

	static class CustomPopupWindowRvViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv)
		TextView tv;

		public CustomPopupWindowRvViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
