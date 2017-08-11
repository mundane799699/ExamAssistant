package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.SectionBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/13 11:40
 * @description :
 * @file : SectionAdapter.java
 */

public class SectionAdapter extends RecyclerView.Adapter<TypeAbstractViewHolder<SectionBean>> {

	private List<SectionBean> mList;

	public SectionAdapter(List<SectionBean> list) {
		mList = list;
	}

	public interface OnItemClickListener{
		void onItemClick(SectionBean section, int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	@Override
	public TypeAbstractViewHolder<SectionBean> onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.item_section, parent, false);

		return new SectionViewHolder(view);
	}

	private int mLastSelectedPosition = -1;

	@Override
	public void onBindViewHolder(TypeAbstractViewHolder<SectionBean> holder, final int position) {
		final SectionBean section = mList.get(position);
		holder.bindHolder(section);
		if (section.isSelected) {
			mLastSelectedPosition = position;
		}
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLastSelectedPosition > -1) {
					mList.get(mLastSelectedPosition).isSelected = false;
				}
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(section, position);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mList == null || mList.isEmpty() ? 0 : mList.size();
	}

	static class SectionViewHolder extends TypeAbstractViewHolder<SectionBean> {

		@BindView(R.id.iv_icon)
		ImageView mIvIcon;
		@BindView(R.id.tv_name)
		TextView mTvName;
		@BindView(R.id.tv_num)
		TextView mTvNum;
		@BindView(R.id.ll_bg)
		LinearLayout mLlBg;

		public SectionViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void bindHolder(SectionBean sectionBean) {
			mTvName.setText(sectionBean.questionType);
			mTvNum.setText(String.format("%d题", sectionBean.questionNum));
			mLlBg.setSelected(sectionBean.isSelected);
			mIvIcon.setImageResource(sectionBean.type);
		}
	}


}
