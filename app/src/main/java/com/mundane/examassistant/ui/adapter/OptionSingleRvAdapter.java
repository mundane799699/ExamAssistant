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
				setStatus(mQuestion.getOptionAStatus(), holder, position);
				break;
			case 1:
				holder.mTvOption.setText(mQuestion.getOptionB());
				setStatus(mQuestion.getOptionBStatus(), holder, position);
				break;
			case 2:
				holder.mTvOption.setText(mQuestion.getOptionC());
				setStatus(mQuestion.getOptionCStatus(), holder, position);
				break;
			case 3:
				holder.mTvOption.setText(mQuestion.getOptionD());
				setStatus(mQuestion.getOptionDStatus(), holder, position);
				break;
			case 4:
				holder.mTvOption.setText(mQuestion.getOptionE());
				setStatus(mQuestion.getOptionEStatus(), holder, position);
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

	private void setStatus(int status, OptionRvViewHolder holder, int position) {
		switch (status) {
			case 0:
				setNormalStatus(holder, position);
				break;
			case 1:
				setCorrectStatus(holder);
				break;
			case 2:
				setWrongStatus(holder);
				break;
		}
	}

	private void setWrongStatus(OptionRvViewHolder holder) {
		Resources resources = holder.mIvOption.getResources();
		holder.mTvAbcde.setVisibility(View.GONE);
		holder.mIvOption.setVisibility(View.VISIBLE);
		holder.mIvOption.setImageResource(R.drawable.icon_choice_bg_wrong);
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextWrong));
	}

	private void setCorrectStatus(OptionRvViewHolder holder) {
		Resources resources = holder.mIvOption.getResources();
		holder.mTvAbcde.setVisibility(View.GONE);
		holder.mIvOption.setVisibility(View.VISIBLE);
		holder.mIvOption.setImageResource(R.drawable.icon_choice_bg_right);
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextCorrect));
	}

	private void setLetterByPosition(OptionRvViewHolder holder, int position) {
		switch (position) {
			case 0:
				holder.mTvAbcde.setText("A");
				break;
			case 1:
				holder.mTvAbcde.setText("B");
				break;
			case 2:
				holder.mTvAbcde.setText("C");
				break;
			case 3:
				holder.mTvAbcde.setText("D");
				break;
			case 4:
				holder.mTvAbcde.setText("E");
				break;
			default:
				break;
		}
	}

	private void setNormalStatus(OptionRvViewHolder holder, int position) {
		setLetterByPosition(holder, position);
		Resources resources = holder.mTvOption.getResources();
		holder.mIvOption.setVisibility(View.GONE);
		holder.mTvAbcde.setVisibility(View.VISIBLE);
		holder.mTvAbcde.setTextColor(resources.getColor(R.color.answerTextNormal));
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextNormal));
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
