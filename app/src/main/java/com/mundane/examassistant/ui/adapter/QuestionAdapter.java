package com.mundane.examassistant.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : mundane
 * @time : 2017/4/18 10:00
 * @description :
 * @file : QuestionAdapter.java
 */

public class QuestionAdapter extends PagerAdapter {

	private List<Question> mList;

	public QuestionAdapter(List<Question> list) {
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater inflater = LayoutInflater.from(container.getContext());
		View view = inflater.inflate(R.layout.layout_answer_question_page, container, false);
		setUpView(view, mList.get(position));
		container.addView(view);
		return view;
	}

	private void setUpView(View view, Question question) {
		TextView tvQuestion = (TextView) view.findViewById(R.id.tv_question);
		RecyclerView rvOption = (RecyclerView) view.findViewById(R.id.rv_option);
		rvOption.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
		List<String> optionList = new ArrayList<>();
		String optionA = question.getOptionA();
		String optionB = question.getOptionB();
		String optionC = question.getOptionC();
		String optionD = question.getOptionD();
		String optionE = question.getOptionE();
		if (!TextUtils.isEmpty(optionA)) {
			optionList.add(optionA);
		}
		if (!TextUtils.isEmpty(optionB)) {
			optionList.add(optionB);
		}
		if (!TextUtils.isEmpty(optionC)) {
			optionList.add(optionC);
		}
		if (!TextUtils.isEmpty(optionD)) {
			optionList.add(optionD);
		}
		if (!TextUtils.isEmpty(optionE)) {
			optionList.add(optionE);
		}
		tvQuestion.setText(question.getQuestion());
		OptionRvAdapter optionRvAdapter = new OptionRvAdapter(optionList);
		Context context = view.getContext();
		rvOption.setLayoutManager(new LinearLayoutManager(context));
		RecycleViewDivider divider = new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(context, 1), ContextCompat.getColor(context, R.color.gray_efefef));
		divider.setIsDrawLastDivider(false);
		divider.setLeftOffset(DensityUtils.dp2px(context, 14));
		rvOption.addItemDecoration(divider);
		rvOption.setAdapter(optionRvAdapter);
	}

	private final String TAG = "QuestionAdapter";

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
