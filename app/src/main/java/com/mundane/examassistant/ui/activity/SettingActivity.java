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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mundane.examassistant.BuildConfig;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.TimeDelayBean;
import com.mundane.examassistant.utils.ResUtil;
import com.mundane.examassistant.utils.Shares;
import com.mundane.examassistant.widget.AnswerRightDialogFragment;

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
		mAnswerRightDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getAnswerRightData());
		mAnswerRightDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvAnswerRightDelayTime.setText(timeDelayBean.text);
			}
		});
		mAnswerRightRemoveDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getAnswerRightRemoveData());
		mAnswerRightRemoveDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvAnswerRightRemove.setText(timeDelayBean.text);
			}
		});
		mFlipPageDialogFragment = AnswerRightDialogFragment.newInstance(ResUtil.getFlipPageDate());
		mFlipPageDialogFragment.setOnItemClickListener(new AnswerRightDialogFragment.OnItemClickListener() {
			@Override
			public void onItemClicked(TimeDelayBean timeDelayBean) {
				mTvFlipPage.setText(timeDelayBean.text);
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
