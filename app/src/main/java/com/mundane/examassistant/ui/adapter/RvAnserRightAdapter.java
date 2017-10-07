package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.TimeDelayBean;
import java.util.List;

/**
 * Created by mundane on 2017/10/4 下午1:03
 */

public class RvAnserRightAdapter extends RecyclerView.Adapter<RvAnserRightAdapter.AnswerRightViewHolder> {

	private List<TimeDelayBean> mDataList;


	public RvAnserRightAdapter(List<TimeDelayBean> dataList) {
		mDataList = dataList;
	}

	public interface OnItemClickListener{
		void onItemClicked(int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}


	@Override
	public AnswerRightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_right_pop_rv, parent, false);
		return new AnswerRightViewHolder(view);
	}


	@Override
	public void onBindViewHolder(final AnswerRightViewHolder holder, final int position) {
		TimeDelayBean timeDelayBean = mDataList.get(position);
		holder.tv.setText(timeDelayBean.text);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClicked(position);
				}
			}
		});
	}


	@Override
	public int getItemCount() {
		return mDataList == null || mDataList.isEmpty() ? 0 : mDataList.size();
	}

	static class AnswerRightViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv)
		TextView tv;

		public AnswerRightViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}


}
