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

	private void setUpView(View view, final Question question) {
		TextView tvQuestion = (TextView) view.findViewById(R.id.tv_question);
		tvQuestion.setText(question.getQuestion());
		final RecyclerView rvOption = (RecyclerView) view.findViewById(R.id.rv_option);
		rvOption.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//		final List<OptionBean> optionList = new ArrayList<>();
//		String optionA = question.getOptionA();
//		String optionB = question.getOptionB();
//		String optionC = question.getOptionC();
//		String optionD = question.getOptionD();
//		String optionE = question.getOptionE();
//		String answer = question.getAnswer();
//		if (!TextUtils.isEmpty(optionA)) {
//			optionList.add(new OptionBean(optionA, answer.contains("A"), question.getShowOptionA()));
//		}
//		if (!TextUtils.isEmpty(optionB)) {
//			optionList.add(new OptionBean(optionB, answer.contains("B"), question.getShowOptionB()));
//		}
//		if (!TextUtils.isEmpty(optionC)) {
//			optionList.add(new OptionBean(optionC, answer.contains("C"), question.getShowOptionC()));
//		}
//		if (!TextUtils.isEmpty(optionD)) {
//			optionList.add(new OptionBean(optionD, answer.contains("D"), question.getShowOptionD()));
//		}
//		if (!TextUtils.isEmpty(optionE)) {
//			optionList.add(new OptionBean(optionD, answer.contains("E"), question.getShowOptionE()));
//		}


		final OptionRvAdapter optionRvAdapter = new OptionRvAdapter(question);
		optionRvAdapter.setOnItemClickListener(new OptionRvAdapter.OnItemClickListener() {
			@Override
			public void onItemClicked(int position) {
				//	如果该问题已经被回答过, 不产生任何反应
				if (question.getHaveBeenAnswered()) {
					return;
				}
				//	如果该问题还没有被回答过
				question.setHaveBeenAnswered(true);    //	将该问题标记为已经被回答过

				if (question.getType().startsWith("单选")) {    //	单选
					showSelectedOption(position, question);        //	显示被选中的条目是正确答案还是错误答案
					showCorrectAnswer(question);                //	显示正确答案
					optionRvAdapter.notifyDataSetChanged();
					optionRvAdapter.setOnItemClickListener(null);    //	其实这句代码可以去掉了
				} else {//	多选

				}


			}
		});
		Context context = view.getContext();
		rvOption.setLayoutManager(new LinearLayoutManager(context));
		RecycleViewDivider divider = new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(context, 1), ContextCompat.getColor(context, R.color.gray_efefef));
		divider.setIsDrawLastDivider(false);
		divider.setLeftOffset(DensityUtils.dp2px(context, 14));
		rvOption.addItemDecoration(divider);
		rvOption.setAdapter(optionRvAdapter);
	}

	private void showSelectedOption(int position, Question question) {
		switch (position) {
			case 0:
				question.setShowOptionA(true);
				break;
			case 1:
				question.setShowOptionB(true);
				break;
			case 2:
				question.setShowOptionC(true);
				break;
			case 3:
				question.setShowOptionD(true);
				break;
			case 4:
				question.setShowOptionE(true);
				break;
		}
	}

	private void showCorrectAnswer(Question question) {
		if (!TextUtils.isEmpty(question.getOptionA()) && question.getIsOptionACorrect()) {
			question.setShowOptionA(true);
		}
		if (!TextUtils.isEmpty(question.getOptionB()) && question.getIsOptionBCorrect()) {
			question.setShowOptionB(true);
		}
		if (!TextUtils.isEmpty(question.getOptionC()) && question.getIsOptionCCorrect()) {
			question.setShowOptionC(true);
		}
		if (!TextUtils.isEmpty(question.getOptionD()) && question.getIsOptionDCorrect()) {
			question.setShowOptionD(true);
		}
		if (!TextUtils.isEmpty(question.getOptionE()) && question.getIsOptionECorrect()) {
			question.setShowOptionE(true);
		}
	}

	private final String TAG = "QuestionAdapter";

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
