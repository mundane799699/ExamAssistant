package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.db.entity.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/18 10:42
 * @description :
 * @file : OptionRvAdapter.java
 */

public class OptionRvAdapter extends RecyclerView.Adapter<OptionRvAdapter.OptionRvViewHolder> {

	private Question mQuestion;

	public interface OnItemClickListener{
		void onItemClicked(int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	public OptionRvAdapter(Question question) {
		this.mQuestion = question;
	}

	@Override
	public OptionRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_option, parent, false);
		return new OptionRvViewHolder(view);
	}

	@Override
	public void onBindViewHolder(OptionRvViewHolder holder, final int position) {
		switch (position) {
			case 0:
				if (mQuestion.getShowOptionA()) {
					showIcon(holder, mQuestion.getIsOptionACorrect());
				} else {
					holder.mIvOption.setImageResource(R.drawable.option_a);
				}
				holder.mTvOption.setText(mQuestion.getOptionA());
				break;
			case 1:
				if (mQuestion.getShowOptionB()) {
					showIcon(holder, mQuestion.getIsOptionBCorrect());
				} else {
					holder.mIvOption.setImageResource(R.drawable.optoin_b);
				}
				holder.mTvOption.setText(mQuestion.getOptionB());
				break;
			case 2:
				if (mQuestion.getShowOptionC()) {
					showIcon(holder, mQuestion.getIsOptionCCorrect());
				} else {
					holder.mIvOption.setImageResource(R.drawable.option_c);
				}
				holder.mTvOption.setText(mQuestion.getOptionC());
				break;
			case 3:
				if (mQuestion.getShowOptionD()) {
					showIcon(holder, mQuestion.getIsOptionDCorrect());
				} else {
					holder.mIvOption.setImageResource(R.drawable.option_d);
				}
				holder.mTvOption.setText(mQuestion.getOptionD());
				break;
			case 4:
				if (mQuestion.getShowOptionE()) {
					showIcon(holder, mQuestion.getIsOptionECorrect());
				} else {
					holder.mIvOption.setImageResource(R.drawable.option_e);
				}
				holder.mTvOption.setText(mQuestion.getOptionE());
			default:
				break;
		}

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClicked(position);
				}
			}
		});
	}

	private void showIcon(OptionRvViewHolder holder, boolean isOptonCorrect) {
		holder.mIvOption.setImageResource(isOptonCorrect ? R.drawable.answer_correct : R.drawable.answer_wrong);
	}

	@Override
	public int getItemCount() {
		if (mQuestion == null) {
			return 0;
		} else if (!TextUtils.isEmpty(mQuestion.getOptionE())) {
			return 5;
		} else if (!TextUtils.isEmpty(mQuestion.getOptionD())) {
			return 4;
		} else {
			return 2;
		}
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
