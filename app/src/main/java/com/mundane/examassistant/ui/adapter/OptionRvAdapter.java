package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mundane.examassistant.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/18 10:42
 * @description :
 * @file : OptionRvAdapter.java
 */

public class OptionRvAdapter extends RecyclerView.Adapter<OptionRvAdapter.OptionRvViewHolder> {

	private List<String> mList;

	public OptionRvAdapter(List<String> list) {
		mList = list;
	}

	@Override
	public OptionRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_option, parent, false);
		return new OptionRvViewHolder(view);
	}

	@Override
	public void onBindViewHolder(OptionRvViewHolder holder, int position) {
		String option = mList.get(position);
		switch (position) {
			case 0:
				holder.mIvOption.setImageResource(R.drawable.option_a);
				break;
			case 1:
				holder.mIvOption.setImageResource(R.drawable.optoin_b);
				break;
			case 2:
				holder.mIvOption.setImageResource(R.drawable.option_c);
				break;
			case 3:
				holder.mIvOption.setImageResource(R.drawable.option_d);
				break;
			case 4:
				holder.mIvOption.setImageResource(R.drawable.option_e);
			default:
				break;
		}
		holder.mTvOption.setText(option);
	}

	@Override
	public int getItemCount() {
		return mList == null || mList.isEmpty() ? 0 : mList.size();
	}


	static class OptionRvViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.iv_option)
		ImageView mIvOption;
		@BindView(R.id.tv_option)
		TextView mTvOption;

		public OptionRvViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
