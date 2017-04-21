package com.mundane.examassistant.ui.adapter;

import android.content.res.Resources;
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
 * @file : OptionSingleRvAdapter.java
 */

public class OptionSingleRvAdapter extends RecyclerView.Adapter<OptionSingleRvAdapter.OptionRvViewHolder> {

	private Question mQuestion;

	public interface OnItemClickListener{
		void onItemClicked(int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	public OptionSingleRvAdapter(Question question) {
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
				holder.mTvOption.setText(mQuestion.getOptionA());
				if (mQuestion.getShowOptionA()) {
					showIcon(holder, mQuestion.getIsOptionACorrect());
				} else {
					holder.mTvAbcde.setText("A");
					setTextColorNormal(holder);
				}
				break;
			case 1:
				holder.mTvOption.setText(mQuestion.getOptionB());
				if (mQuestion.getShowOptionB()) {
					showIcon(holder, mQuestion.getIsOptionBCorrect());
				} else {
					holder.mTvAbcde.setText("B");
					setTextColorNormal(holder);
				}
				break;
			case 2:
				holder.mTvOption.setText(mQuestion.getOptionC());
				if (mQuestion.getShowOptionC()) {
					showIcon(holder, mQuestion.getIsOptionCCorrect());
				} else {
					holder.mTvAbcde.setText("C");
					setTextColorNormal(holder);
				}
				break;
			case 3:
				holder.mTvOption.setText(mQuestion.getOptionD());
				if (mQuestion.getShowOptionD()) {
					showIcon(holder, mQuestion.getIsOptionDCorrect());
				} else {
					holder.mTvAbcde.setText("D");
					setTextColorNormal(holder);
				}
				break;
			case 4:
				holder.mTvOption.setText(mQuestion.getOptionE());
				if (mQuestion.getShowOptionE()) {
					showIcon(holder, mQuestion.getIsOptionECorrect());
				} else {
					holder.mTvAbcde.setText("E");
					setTextColorNormal(holder);
				}
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

	private void setTextColorNormal(OptionRvViewHolder holder) {
		Resources resources = holder.mTvOption.getResources();
		holder.mTvAbcde.setVisibility(View.VISIBLE);
		holder.mIvOption.setVisibility(View.GONE);
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextNormal));
		holder.mTvAbcde.setTextColor(resources.getColor(R.color.answerTextNormal));
	}

	private void showIcon(OptionRvViewHolder holder, boolean isOptonCorrect) {
		holder.mTvAbcde.setVisibility(View.GONE);
		holder.mIvOption.setVisibility(View.VISIBLE);
		Resources resources = holder.mIvOption.getResources();
		holder.mIvOption.setImageResource(isOptonCorrect ? R.drawable.icon_choice_bg_right : R.drawable.icon_choice_bg_wrong);
		holder.mTvOption.setTextColor(isOptonCorrect ? resources.getColor(R.color.answerTextCorrect) : resources.getColor(R.color.answerTextWrong));
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
		@BindView(R.id.tv_abcde)
		TextView mTvAbcde;

		public OptionRvViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
