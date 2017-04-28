package com.mundane.examassistant.ui.adapter;

import android.content.res.Resources;
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

public class OptionMultiRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Question mQuestion;

	public OptionMultiRvAdapter(Question question) {
		mQuestion = question;
	}

	public interface OnItemClickListener{
		void onItemClicked(int position);

		void onSubmitButtonClicked();
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
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof FootViewHolder) {    //	footer
			FootViewHolder footViewHolder = (FootViewHolder) holder;
			if (mQuestion.getHaveBeenAnswered()) {
				footViewHolder.mBtnSubmit.setVisibility(View.GONE);
			}
			footViewHolder.mBtnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onSubmitButtonClicked();
					}
				}
			});
		} else {	//	normal
			NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
			switch (position) {
				case 0:
					normalViewHolder.mTvOption.setText(mQuestion.getOptionA());
					setStatus(mQuestion.getOptionAStatus(), normalViewHolder, position);
					break;
				case 1:
					normalViewHolder.mTvOption.setText(mQuestion.getOptionB());
					setStatus(mQuestion.getOptionBStatus(), normalViewHolder, position);
					break;
				case 2:
					normalViewHolder.mTvOption.setText(mQuestion.getOptionC());
					setStatus(mQuestion.getOptionCStatus(), normalViewHolder, position);
					break;
				case 3:
					normalViewHolder.mTvOption.setText(mQuestion.getOptionD());
					setStatus(mQuestion.getOptionDStatus(), normalViewHolder, position);
					break;
				case 4:
					normalViewHolder.mTvOption.setText(mQuestion.getOptionE());
					setStatus(mQuestion.getOptionEStatus(), normalViewHolder, position);
					break;
			}
			normalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClicked(position);
					}
				}
			});
		}

	}

	private void setStatus(int status, NormalViewHolder holder, int position) {
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
			case 3:
				setSelectingStatus(holder, position);
				break;
			case 4:
				setHaveNotSelectedStatus(holder, position);
				break;

		}
	}

	private void setHaveNotSelectedStatus(NormalViewHolder holder, int position) {
		setLetterByPosition(holder, position);
		Resources resources = holder.mTvAbcde.getResources();
		holder.mIvOption.setVisibility(View.GONE);
		holder.mTvAbcde.setVisibility(View.VISIBLE);
		holder.mTvAbcde.setBackgroundResource(R.drawable.shape_tv_bg_green);
		holder.mTvAbcde.setTextColor(resources.getColor(R.color.white));
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextCorrect));
	}

	private void setSelectingStatus(NormalViewHolder holder, int position) {
		setLetterByPosition(holder, position);
		Resources resources = holder.mTvAbcde.getResources();
		holder.mIvOption.setVisibility(View.GONE);
		holder.mTvAbcde.setVisibility(View.VISIBLE);
		holder.mTvAbcde.setBackgroundResource(R.drawable.shape_tv_bg_blue);
		holder.mTvAbcde.setTextColor(resources.getColor(R.color.white));
		holder.mTvOption.setTextColor(resources.getColor(R.color.blue0094f6));
	}

	private void setWrongStatus(NormalViewHolder holder) {
		Resources resources = holder.mIvOption.getResources();
		holder.mTvAbcde.setVisibility(View.GONE);
		holder.mIvOption.setVisibility(View.VISIBLE);
		holder.mIvOption.setImageResource(R.drawable.icon_choice_bg_wrong);
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextWrong));
	}

	private void setCorrectStatus(NormalViewHolder holder) {
		Resources resources = holder.mIvOption.getResources();
		holder.mTvAbcde.setVisibility(View.GONE);
		holder.mIvOption.setVisibility(View.VISIBLE);
		holder.mIvOption.setImageResource(R.drawable.icon_choice_bg_right);
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextCorrect));
	}

	private void setNormalStatus(NormalViewHolder holder, int position) {
		setLetterByPosition(holder, position);
		Resources resources = holder.mTvOption.getResources();
		holder.mIvOption.setVisibility(View.GONE);
		holder.mTvAbcde.setVisibility(View.VISIBLE);
		holder.mTvAbcde.setBackgroundResource(R.drawable.shape_tv_bg_abcde);
		holder.mTvAbcde.setTextColor(resources.getColor(R.color.answerTextNormal));
		holder.mTvOption.setTextColor(resources.getColor(R.color.answerTextNormal));
	}

	private void setLetterByPosition(NormalViewHolder holder, int position) {
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

	static class NormalViewHolder extends RecyclerView.ViewHolder {

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

	}



	static class FootViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.btn_submit)
		Button mBtnSubmit;
		public FootViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
