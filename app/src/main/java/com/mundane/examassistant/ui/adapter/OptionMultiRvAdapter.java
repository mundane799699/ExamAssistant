package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.db.entity.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/21 11:00
 * @description :
 * @file : OptionMultiRvAdapter.java
 */

public class OptionMultiRvAdapter extends RecyclerView.Adapter<TypeAbstractViewHolder> {

	private Question mQuestion;

	public OptionMultiRvAdapter(Question question) {
		mQuestion = question;
	}

	public interface OnItemClickListener{
		void onItemClicked(int position);

		void onSubmitButtonClicked(int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	@Override
	public int getItemViewType(int position) {
		if (!TextUtils.isEmpty(mQuestion.getOptionB()) && TextUtils.isEmpty(mQuestion.getOptionC()) && position == 2) {
			return 0;	//	footer
		} else if (!TextUtils.isEmpty(mQuestion.getOptionD()) && TextUtils.isEmpty(mQuestion.getOptionE()) && position == 4) {
			return 0;
		} else if (!TextUtils.isEmpty(mQuestion.getOptionE()) && position == 5) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public TypeAbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case 0:		//	footer
				return new FootViewHolder(inflater.inflate(R.layout.item_foot, parent, false));
			case 1:		//	normal
				return new NormalViewHolder(inflater.inflate(R.layout.item_rv_option, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(TypeAbstractViewHolder holder, int position) {
		holder.bindHolder(mQuestion, position);
	}

	@Override
	public int getItemCount() {
		if (mQuestion == null) {
			return 0;
		} else if (!TextUtils.isEmpty(mQuestion.getOptionE())) {
			return 6;
		} else if (!TextUtils.isEmpty(mQuestion.getOptionD())) {
			return 5;
		} else {
			return 3;
		}
	}

	static class NormalViewHolder extends TypeAbstractViewHolder<Question> {

		@BindView(R.id.iv_option)
		ImageView mIvOption;
		@BindView(R.id.tv_option)
		TextView mTvOption;
		@BindView(R.id.tv_abcde)
		TextView mTvAbcde;
		public NormalViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void bindHolder(Question question, int position) {
			switch (position) {
				case 0:
					mTvOption.setText(question.getOptionA());
					if (question.getShowOptionA()) {
						if (question.getIsOptionACorrect()) {
							showCorrectItem("A");
						} else {
							showNormalItem("A");
						}

					} else {
						showNormalItem("A");
					}
					break;
				case 1:
					mTvOption.setText(question.getOptionB());
					if (question.getShowOptionB()) {
						if (question.getIsOptionACorrect()) {
							showCorrectItem("B");
						} else {
							showNormalItem("B");
						}
					} else {
						showNormalItem("B");
					}
					break;
				case 2:
					mTvOption.setText(question.getOptionC());
					if (question.getShowOptionC()) {
						if (question.getIsOptionACorrect()) {
							showCorrectItem("C");
						} else {
							showNormalItem("C");
						}
					} else {
						showNormalItem("C");
					}
					break;
				case 3:
					mTvOption.setText(question.getOptionD());
					if (question.getShowOptionD()) {
						if (question.getIsOptionACorrect()) {
							showCorrectItem("D");
						} else {
							showNormalItem("D");
						}
					} else {
						showNormalItem("D");
					}
					break;
				case 4:
					mTvOption.setText(question.getOptionE());
					if (question.getShowOptionE()) {
						if (question.getIsOptionACorrect()) {
							showCorrectItem("E");
						} else {
							showNormalItem("E");
						}
					} else {
						showNormalItem("E");
					}
					break;
				default:
					break;
			}
		}

		private void showCorrectItem(String letter) {
			mTvAbcde.setText(letter);
			mIvOption.setVisibility(View.GONE);
			mTvAbcde.setVisibility(View.VISIBLE);
			mTvAbcde.setBackgroundResource(R.drawable.shape_tv_bg_green);
			mTvOption.setTextColor(mTvOption.getResources().getColor(R.color.answerTextCorrect));
			mTvAbcde.setTextColor(mTvOption.getResources().getColor(R.color.white));
		}

		private void showNormalItem(String letter) {
			mTvAbcde.setText(letter);
			mIvOption.setVisibility(View.GONE);
			mTvAbcde.setVisibility(View.VISIBLE);
			mTvOption.setTextColor(mTvOption.getResources().getColor(R.color.answerTextNormal));
			mTvAbcde.setTextColor(mTvOption.getResources().getColor(R.color.answerTextNormal));
		}
	}

	static class FootViewHolder extends TypeAbstractViewHolder<Question> {

		@BindView(R.id.btn_submit)
		Button mBtnSubmit;
		public FootViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void bindHolder(Question question, int position) {

		}
	}
}
