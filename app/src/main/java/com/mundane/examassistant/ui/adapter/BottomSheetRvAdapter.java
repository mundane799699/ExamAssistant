package com.mundane.examassistant.ui.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.db.entity.Question;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/8/9 13:29
 * @description :
 * @file : BottomSheetRvAdapter.java
 */
public class BottomSheetRvAdapter extends RecyclerView.Adapter<BottomSheetRvAdapter.BottomSheetRvAdapterHolder> {

	private List<Question> mDataList;

	public BottomSheetRvAdapter(List<Question> dataList) {
		this.mDataList = dataList;
	}

	public interface OnItemClickListener{
		void onItemClicked(int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	@Override
	public BottomSheetRvAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottomsheet_rv, parent, false);
		return new BottomSheetRvAdapterHolder(view);
	}

	@Override
	public void onBindViewHolder(BottomSheetRvAdapterHolder holder, final int position) {
		Question question = mDataList.get(position);
		Resources resources = holder.tv.getResources();
		holder.tv.setText(String.format("%d", position + 1));
		if (!question.getHaveBeenAnswered()) {
			// 如果该问题还没有被回答过
			setUndoStatus(holder, resources);
		} else {
			// 如果该问题已经被回答过
			// 回答错误的
			if (question.getIsAnsweredWrong()) {
                setWrongStatus(holder, resources);
			} else {
				// 回答正确的
				setRightStatus(holder, resources);
			}
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

	private void setUndoStatus(BottomSheetRvAdapterHolder holder, Resources resources) {
		holder.tv.setBackgroundResource(R.drawable.shape_tv_bg_abcde);
		holder.tv.setTextColor(resources.getColor(R.color.answerTextNormal));
	}

	private void setWrongStatus(BottomSheetRvAdapterHolder holder, Resources resources) {
		holder.tv.setBackgroundResource(R.drawable.shape_tv_wrong);
		holder.tv.setTextColor(resources.getColor(R.color.answerTextWrong));
	}

	private void setRightStatus(BottomSheetRvAdapterHolder holder, Resources resources) {
		holder.tv.setBackgroundResource(R.drawable.shape_tv_right);
		holder.tv.setTextColor(resources.getColor(R.color.answerTextCorrect));
	}

	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}

	static class BottomSheetRvAdapterHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv)
		TextView tv;
		public BottomSheetRvAdapterHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
