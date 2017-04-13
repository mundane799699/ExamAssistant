package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

	@Override
	public TypeAbstractViewHolder<SectionBean> onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.item_section, parent, false);

		return new SectionViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TypeAbstractViewHolder<SectionBean> holder, int position) {
		holder.bindHolder(mList.get(position));
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

		public SectionViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void bindHolder(SectionBean sectionBean) {
			mTvName.setText(sectionBean.chooseItem);
			mTvNum.setText(sectionBean.questionNum);
			switch (sectionBean.type) {
				case 1:
					mIvIcon.setImageResource(R.drawable.list_one);
					break;
				case 2:
					mIvIcon.setImageResource(R.drawable.list_two);
					break;
				case 3:
					mIvIcon.setImageResource(R.drawable.list_three);
					break;
				case 4:
					mIvIcon.setImageResource(R.drawable.list_four);
					break;
				case 5:
					mIvIcon.setImageResource(R.drawable.list_five);
					break;
				case 6:
					mIvIcon.setImageResource(R.drawable.list_six);
					break;
				case 7:
					mIvIcon.setImageResource(R.drawable.list_seven);
					break;
			}
		}
	}


}
