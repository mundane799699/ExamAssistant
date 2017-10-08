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
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.utils.LogUtils;
import com.mundane.examassistant.widget.RecycleViewDivider;
import java.util.List;

/**
 * @author : mundane
 * @time : 2017/4/18 10:00
 * @description :
 * @file : PracticeViewpagerAdapter.java
 */

public class PracticeViewpagerAdapter extends PagerAdapter {

	private QuestionDao mQuestionDao;
	private List<Question> mList;

	public PracticeViewpagerAdapter(List<Question> list, QuestionDao questionDao) {
		mList = list;
//        mQuestionDao = DbHelper.getQuestionDao();
		mQuestionDao = questionDao;
	}

	public interface OnAnswerRight {
		void answerRight();
	}

	private OnAnswerRight mOnAnswerRightListener;


	public void setOnAnswerRightListener(OnAnswerRight onAnswerRightListener) {
		mOnAnswerRightListener = onAnswerRightListener;
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
		setUpView(view, mList.get(position), position);
		container.addView(view);
		return view;
	}

	private void setUpView(View view, final Question question, final int position) {
		LogUtils.d("instantiateItem position = " + position);
		TextView tvQuestion = (TextView) view.findViewById(R.id.tv_question);
		tvQuestion.setText(question.getQuestion());
		final RecyclerView rvOption = (RecyclerView) view.findViewById(R.id.rv_option);
		rvOption.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
		if (question.getType().startsWith("单选")) {        //	单选
			final OptionSingleRvAdapter optionSingleRvAdapter = new OptionSingleRvAdapter(question);
			optionSingleRvAdapter.setOnItemClickListener(new OptionSingleRvAdapter.OnItemClickListener() {
				@Override
				public void onItemClicked(int pos) {
					//	如果该问题已经被回答过, 不产生任何反应
					if (question.getHaveBeenAnswered()) {
						return;
					}
					//	如果该问题还没有被回答过
					question.setHaveBeenAnswered(true);    //	将该问题标记为已经被回答过

					setOptionStatus(pos, question);        //	显示被选中的条目是正确答案还是错误答案
					showCorrectAnswer(question);                //	显示正确答案
					optionSingleRvAdapter.notifyDataSetChanged();
//				optionSingleRvAdapter.setOnItemClickListener(null);    //	其实这句代码可以去掉了
				}
			});
			Context context = view.getContext();
			rvOption.setLayoutManager(new LinearLayoutManager(context));
			RecycleViewDivider divider = new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(context, 1), ContextCompat.getColor(context, R.color.gray_efefef));
			divider.setIsDrawLastDivider(false);
			divider.setLeftOffset(DensityUtils.dp2px(context, 14));
			rvOption.addItemDecoration(divider);
			rvOption.setAdapter(optionSingleRvAdapter);
		} else {    //	多选
			final OptionMultiRvAdapter optionMultiRvAdapter = new OptionMultiRvAdapter(question);
			optionMultiRvAdapter.setOnItemClickListener(new OptionMultiRvAdapter.OnItemClickListener() {
				@Override
				public void onItemClicked(int pos) {
					//	如果该问题已经被回答过, 不产生任何反应
					if (question.getHaveBeenAnswered()) {
						return;
					}
					setMultiOptionStatus(pos, question);
					optionMultiRvAdapter.notifyDataSetChanged();
				}

				@Override
				public void onSubmitButtonClicked() {
					//	如果该问题已经被回答过, 不产生任何反应
					if (question.getHaveBeenAnswered()) {
						return;
					}
					//	如果该问题还没有被回答过
					question.setHaveBeenAnswered(true);
					submitAnswer(question);
					optionMultiRvAdapter.notifyDataSetChanged();
				}
			});
			Context context = view.getContext();
			rvOption.setLayoutManager(new LinearLayoutManager(context));
			RecycleViewDivider divider = new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(context, 1), ContextCompat.getColor(context, R.color.gray_efefef));
			divider.setIsDrawLastDivider(false);
			divider.setLeftOffset(DensityUtils.dp2px(context, 14));
			rvOption.addItemDecoration(divider);
			rvOption.setAdapter(optionMultiRvAdapter);
		}

	}

	private void submitAnswer(Question question) {
		question.setIsAnsweredWrong(false);
		if (!TextUtils.isEmpty(question.getOptionA())) {
			if (question.getOptionAStatus() == 0 && question.getIsOptionACorrect()) {
				question.setOptionAStatus(4);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			} else if (question.getOptionAStatus() == 0 && !question.getIsOptionACorrect()) {
				question.setOptionAStatus(0);
			} else if (question.getOptionAStatus() == 3 && question.getIsOptionACorrect()) {
				question.setOptionAStatus(1);
			} else if (question.getOptionAStatus() == 3 && !question.getIsOptionACorrect()) {
				question.setOptionAStatus(2);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			}
		}

		if (!TextUtils.isEmpty(question.getOptionB())) {
			if (question.getOptionBStatus() == 0 && question.getIsOptionBCorrect()) {
				question.setOptionBStatus(4);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			} else if (question.getOptionBStatus() == 0 && !question.getIsOptionBCorrect()) {
				question.setOptionBStatus(0);
			} else if (question.getOptionBStatus() == 3 && question.getIsOptionBCorrect()) {
				question.setOptionBStatus(1);
			} else if (question.getOptionBStatus() == 3 && !question.getIsOptionBCorrect()) {
				question.setOptionBStatus(2);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			}
		}

		if (!TextUtils.isEmpty(question.getOptionC())) {
			if (question.getOptionCStatus() == 0 && question.getIsOptionCCorrect()) {
				question.setOptionCStatus(4);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			} else if (question.getOptionCStatus() == 0 && !question.getIsOptionCCorrect()) {
				question.setOptionCStatus(0);
			} else if (question.getOptionCStatus() == 3 && question.getIsOptionCCorrect()) {
				question.setOptionCStatus(1);
			} else if (question.getOptionCStatus() == 3 && !question.getIsOptionCCorrect()) {
				question.setOptionCStatus(2);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			}
		}

		if (!TextUtils.isEmpty(question.getOptionD())) {
			if (question.getOptionDStatus() == 0 && question.getIsOptionDCorrect()) {
				question.setOptionDStatus(4);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			} else if (question.getOptionDStatus() == 0 && !question.getIsOptionDCorrect()) {
				question.setOptionDStatus(0);
			} else if (question.getOptionDStatus() == 3 && question.getIsOptionDCorrect()) {
				question.setOptionDStatus(1);
			} else if (question.getOptionDStatus() == 3 && !question.getIsOptionDCorrect()) {
				question.setOptionDStatus(2);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			}
		}

		if (!TextUtils.isEmpty(question.getOptionE())) {
			if (question.getOptionEStatus() == 0 && question.getIsOptionECorrect()) {
				question.setOptionEStatus(4);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			} else if (question.getOptionEStatus() == 0 && !question.getIsOptionECorrect()) {
				question.setOptionEStatus(0);
			} else if (question.getOptionEStatus() == 3 && question.getIsOptionECorrect()) {
				question.setOptionEStatus(1);
			} else if (question.getOptionEStatus() == 3 && !question.getIsOptionECorrect()) {
				question.setOptionEStatus(2);
				// 标记为回答错误
				question.setIsAnsweredWrong(true);
			}
		}

		// 更新
		mQuestionDao.update(question);

	}

	private void setMultiOptionStatus(int position, Question question) {
		switch (position) {
			case 0:
				if (question.getOptionAStatus() == 0) {
					question.setOptionAStatus(3);
				} else if (question.getOptionAStatus() == 3) {
					question.setOptionAStatus(0);
				}
				break;
			case 1:
				if (question.getOptionBStatus() == 0) {
					question.setOptionBStatus(3);
				} else if (question.getOptionBStatus() == 3) {
					question.setOptionBStatus(0);
				}
				break;
			case 2:
				if (question.getOptionCStatus() == 0) {
					question.setOptionCStatus(3);
				} else if (question.getOptionCStatus() == 3) {
					question.setOptionCStatus(0);
				}
				break;
			case 3:
				if (question.getOptionDStatus() == 0) {
					question.setOptionDStatus(3);
				} else if (question.getOptionDStatus() == 3) {
					question.setOptionDStatus(0);
				}
				break;
			case 4:
				if (question.getOptionEStatus() == 0) {
					question.setOptionEStatus(3);
				} else if (question.getOptionEStatus() == 3) {
					question.setOptionEStatus(0);
				}
				break;
		}
	}

	private void setOptionStatus(int position, Question question) {
		switch (position) {
			case 0:
				question.setOptionAStatus(question.getIsOptionACorrect() ? 1 : 2);
				// 是否回答错误, 需要被收录到错题集中
				question.setIsAnsweredWrong(!question.getIsOptionACorrect());
				if (question.getIsOptionACorrect()) {
					if (mOnAnswerRightListener != null) {
						mOnAnswerRightListener.answerRight();
					}
				} else {
					//ToastUtils.toast("回答错误");
				}
				break;
			case 1:
				question.setOptionBStatus(question.getIsOptionBCorrect() ? 1 : 2);
				// 是否回答错误, 需要被收录到错题集中
				question.setIsAnsweredWrong(!question.getIsOptionBCorrect());
				if (question.getIsOptionBCorrect()) {
					if (mOnAnswerRightListener != null) {
						mOnAnswerRightListener.answerRight();
					}
				} else {
					//ToastUtils.toast("回答错误");
				}
				break;
			case 2:
				question.setOptionCStatus(question.getIsOptionCCorrect() ? 1 : 2);
				// 是否回答错误, 需要被收录到错题集中
				question.setIsAnsweredWrong(!question.getIsOptionCCorrect());
				if (question.getIsOptionCCorrect()) {
					if (mOnAnswerRightListener != null) {
						mOnAnswerRightListener.answerRight();
					}
				} else {
					//ToastUtils.toast("回答错误");
				}
				break;
			case 3:
				question.setOptionDStatus(question.getIsOptionDCorrect() ? 1 : 2);
				// 是否回答错误, 需要被收录到错题集中
				question.setIsAnsweredWrong(!question.getIsOptionDCorrect());
				if (question.getIsOptionDCorrect()) {
					if (mOnAnswerRightListener != null) {
						mOnAnswerRightListener.answerRight();
					}
				} else {
					//ToastUtils.toast("回答错误");
				}
				break;
			case 4:
				question.setOptionEStatus(question.getIsOptionECorrect() ? 1 : 2);
				// 是否回答错误, 需要被收录到错题集中
				question.setIsAnsweredWrong(!question.getIsOptionECorrect());
				if (question.getIsOptionECorrect()) {
					if (mOnAnswerRightListener != null) {
						mOnAnswerRightListener.answerRight();
					}
				} else {
					//ToastUtils.toast("回答错误");
				}
				break;
		}
		// 更新
		mQuestionDao.update(question);
	}

	private void showCorrectAnswer(Question question) {
		if (!TextUtils.isEmpty(question.getOptionA()) && question.getIsOptionACorrect()) {
			question.setOptionAStatus(1);
		}
		if (!TextUtils.isEmpty(question.getOptionB()) && question.getIsOptionBCorrect()) {
			question.setOptionBStatus(1);
		}
		if (!TextUtils.isEmpty(question.getOptionC()) && question.getIsOptionCCorrect()) {
			question.setOptionCStatus(1);
		}
		if (!TextUtils.isEmpty(question.getOptionD()) && question.getIsOptionDCorrect()) {
			question.setOptionDStatus(1);
		}
		if (!TextUtils.isEmpty(question.getOptionE()) && question.getIsOptionECorrect()) {
			question.setOptionEStatus(1);
		}
		// 更新
		mQuestionDao.update(question);
	}

	private final String TAG = "PracticeViewpagerAdapter";

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
