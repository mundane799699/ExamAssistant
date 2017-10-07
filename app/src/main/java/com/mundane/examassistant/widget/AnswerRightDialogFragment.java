package com.mundane.examassistant.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.TimeDelayBean;
import com.mundane.examassistant.ui.adapter.RvAnserRightAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mundane on 2017/10/4 下午6:22
 */

public class AnswerRightDialogFragment extends DialogFragment {
	@BindView(R.id.rv)
	RecyclerView mRv;
	Unbinder unbinder;

	private List<String> mStringList;
	private RvAnserRightAdapter mRvAnserRightAdapter;
	private static final String KEY_BUNDLE = "key_bundle";
	private ArrayList<TimeDelayBean> mDataList;

	public interface OnItemClickListener{
		void onItemClicked(TimeDelayBean timeDelayBean);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mDataList = bundle.getParcelableArrayList(KEY_BUNDLE);
		}

	}


	public static AnswerRightDialogFragment newInstance(ArrayList<TimeDelayBean> list) {
		AnswerRightDialogFragment fragment = new AnswerRightDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(KEY_BUNDLE, list);
		fragment.setArguments(bundle);
		return fragment;
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pop_answer_right, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		//String[] array = getResources().getStringArray(R.array.answerright_right_time);
		//mStringList = Arrays.asList(array);
		mRvAnserRightAdapter = new RvAnserRightAdapter(mDataList);
		mRvAnserRightAdapter.setOnItemClickListener(new RvAnserRightAdapter.OnItemClickListener() {
			@Override
			public void onItemClicked(int position) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClicked(mDataList.get(position));
				}
				AnswerRightDialogFragment.this.dismiss();
			}
		});
		mRv.setLayoutManager(new LinearLayoutManager(getContext()));
		mRv.addItemDecoration(new AnswerRightRvDecoration(getContext()));
		mRv.setAdapter(mRvAnserRightAdapter);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
