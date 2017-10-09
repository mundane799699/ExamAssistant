package com.mundane.examassistant.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.BuildConfig;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.TimeDelayBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.global.Constant;
import com.mundane.examassistant.utils.ResUtil;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.utils.Shares;
import com.mundane.examassistant.widget.AnswerRightDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

	@BindView(R.id.iv_back)
	ImageView mIvBack;
	@BindView(R.id.tv_select_course)
	TextView mTvSelectCourse;
	@BindView(R.id.iv_jump)
	ImageView mIvJump;
	@BindView(R.id.tv_jump)
	TextView mTvJump;
	@BindView(R.id.ll_jump)
	LinearLayout mLlJump;
	@BindView(R.id.iv_mode)
	ImageView mIvMode;
	@BindView(R.id.tv_mode)
	TextView mTvMode;
	@BindView(R.id.ll_mode)
	LinearLayout mLlMode;
	@BindView(R.id.iv_collect)
	ImageView mIvCollect;
	@BindView(R.id.tv_collect)
	TextView mTvCollect;
	@BindView(R.id.ll_collect)
	LinearLayout mLlCollect;
	@BindView(R.id.iv_arrow)
	ImageView mIvArrow;
	@BindView(R.id.iv_setting)
	ImageView mIvSetting;
	@BindView(R.id.rl_title)
	RelativeLayout mRlTitle;
	@BindView(R.id.tv_app_version)
	TextView mTvAppVersion;
	@BindView(R.id.ll_answer_right)
	LinearLayout mLlAnswerRight;
	@BindView(R.id.tv_answer_right_delay_time)
	TextView mTvAnswerRightDelayTime;
	@BindView(R.id.tv_answer_right_remove)
	TextView mTvAnswerRightRemove;
	@BindView(R.id.ll_answer_right_remove)
	LinearLayout mLlAnswerRightRemove;
	@BindView(R.id.tv_flip_page)
	TextView mTvFlipPage;
	@BindView(R.id.ll_flip_page)
	LinearLayout mLlFlipPage;
	@BindView(R.id.ll_clear_all_data)
	LinearLayout mLlClearAllData;
	@BindView(R.id.ll_share)
	LinearLayout mLlShare;
	@BindView(R.id.ll_about_me)
	LinearLayout mLlAboutMe;
	private AnswerRightDialogFragment mAnswerRightDialogFragment;
	private AnswerRightDialogFragment mAnswerRightRemoveDialogFragment;
	private AnswerRightDialogFragment mFlipPageDialogFragment;
	private QuestionDao mQuestionDao;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ButterKnife.bind(this);
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTvSelectCourse.setText("设置");
		mIvArrow.setVisibility(View.GONE);
		mIvSetting.setVisibility(View.GONE);
		mTvAppVersion.setText(String.format("题库助手 v%s", BuildConfig.VERSION_NAME));
		// 答对后自动翻页的延时时间
		long answerRightFlipDelayTime = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME);
		if (answerRightFlipDelayTime == 0) {
			mTvAnswerRightDelayTime.setText("不翻页");
		} else if (answerRightFlipDelayTime == 250) {
			mTvAnswerRightDelayTime.setText("0.25秒后");
		} else if (answerRightFlipDelayTime == 500) {
			mTvAnswerRightDelayTime.setText("0.5秒后");
		} else if (answerRightFlipDelayTime == 750) {
			mTvAnswerRightDelayTime.setText("0.75秒后");
		} else if (answerRightFlipDelayTime == 1000) {
			mTvAnswerRightDelayTime.setText("1秒后");
		} else if (answerRightFlipDelayTime == 1500) {
			mTvAnswerRightDelayTime.setText("1.5秒后");
		} else if (answerRightFlipDelayTime == 2000) {
			mTvAnswerRightDelayTime.setText("2秒后");
		} else if (answerRightFlipDelayTime == 3000) {
			mTvAnswerRightDelayTime.setText("3秒后");
		}

		// 开挂模式下自动翻页的延时时间
		long cheatAutoFlipTime = SPUtils.getLong(Constant.KEY_AUTO_FLIP_TIME);
		if (cheatAutoFlipTime == 0) {
			mTvFlipPage.setText("不翻页");
		} else if (cheatAutoFlipTime == 500) {
			mTvFlipPage.setText("0.5秒后");
		} else if (cheatAutoFlipTime == 1000) {
			mTvFlipPage.setText("1秒后");
		} else if (cheatAutoFlipTime == 1500) {
			mTvFlipPage.setText("1.5秒后");
		} else if (cheatAutoFlipTime == 2000) {
			mTvFlipPage.setText("2秒后");
		} else if (cheatAutoFlipTime == 2500) {
			mTvFlipPage.setText("2.5秒后");
		} else if (cheatAutoFlipTime == 3000) {
			mTvFlipPage.setText("3秒后");
		} else if (cheatAutoFlipTime == 4000) {
			mTvFlipPage.setText("4秒后");
		}

		// 错题答对后自动移除的次数
		long answerRightRemoveTimes = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_REMOVE_TIMES);
		if (answerRightRemoveTimes == 0) {
			mTvAnswerRightRemove.setText("不移除");
		} else if (answerRightRemoveTimes == 1) {
			mTvAnswerRightRemove.setText("1次");
		} else if (answerRightRemoveTimes == 2) {
			mTvAnswerRightRemove.setText("2次");
		} else if (answerRightRemoveTimes == 3) {
			mTvAnswerRightRemove.setText("3次");
		} else if (answerRightRemoveTimes == 4) {
			mTvAnswerRightRemove.setText("4次");
		} else if (answerRightRemoveTimes == 5) {
			mTvAnswerRightRemove.setText("5次");
		} else if (answerRightRemoveTimes == 6) {
			mTvAnswerRightRemove.setText("6次");
		}
		mAnswerRightDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getAnswerRightData());
		mAnswerRightDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvAnswerRightDelayTime.setText(timeDelayBean.text);
				SPUtils.putLong(Constant.KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME, timeDelayBean.time);
			}
		});
		mAnswerRightRemoveDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getAnswerRightRemoveData());
		mAnswerRightRemoveDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvAnswerRightRemove.setText(timeDelayBean.text);
				SPUtils.putLong(Constant.KEY_ANSWER_RIGHT_REMOVE_TIMES, timeDelayBean.time);
			}
		});
		mFlipPageDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getFlipPageDate());
		mFlipPageDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvFlipPage.setText(timeDelayBean.text);
				SPUtils.putLong(Constant.KEY_AUTO_FLIP_TIME, timeDelayBean.time);
			}
		});
		mLlAnswerRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAnswerRightDialogFragment.show(getSupportFragmentManager(), "");
			}
		});
		mLlAnswerRightRemove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAnswerRightRemoveDialogFragment.show(getSupportFragmentManager(), "");
			}
		});
		mLlFlipPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFlipPageDialogFragment.show(getSupportFragmentManager(), "");
			}
		});
		mLlClearAllData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this)
					.setTitle("提示")
					.setMessage("将清空所有答题数据, 包括错题、收藏、答题记录, 确定要清空吗?")
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					})
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 答对后自动翻页的延时时间
							long answerRightFlipDelayTime = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME);
							// 开挂模式下自动翻页的延时时间
							long cheatAutoFlipTime = SPUtils.getLong(Constant.KEY_AUTO_FLIP_TIME);
							// 错题答对后自动移除的次数
							long answerRightRemoveTimes = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_REMOVE_TIMES);
							mQuestionDao = DbHelper.getQuestionDao();
							List<Question> questionList = mQuestionDao.loadAll();
							for (Question question : questionList) {
								question.setIsShowAnswer(false);
								question.setIsCollected(false);
								question.setIsAnsweredWrong(false);
								question.setHaveBeenAnswered(false);
								question.setOptionAStatus(0);
								question.setOptionBStatus(0);
								question.setOptionCStatus(0);
								question.setOptionDStatus(0);
								question.setOptionEStatus(0);
								question.setAnswerRightTimes(0);
							}
							mQuestionDao.updateInTx(questionList);
							SPUtils.clear();
							SPUtils.putLong(Constant.KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME, answerRightFlipDelayTime);
							SPUtils.putLong(Constant.KEY_AUTO_FLIP_TIME, cheatAutoFlipTime);
							SPUtils.putLong(Constant.KEY_ANSWER_RIGHT_REMOVE_TIMES, answerRightRemoveTimes);
						}
					})
					.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.show();
				Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				btnPositive.setTextColor(Color.GRAY);
			}
		});
		mLlShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shares.share(SettingActivity.this, "分享一款十分好用的考试复习App: 题库助手, 妈妈再也不用担心我的学习!");
			}
		});
		mLlAboutMe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, AboutMeActivity.class);
				startActivity(intent);
			}
		});
	}
}
