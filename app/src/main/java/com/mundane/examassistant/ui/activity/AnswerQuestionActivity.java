package com.mundane.examassistant.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.QuestionBean;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.ui.adapter.QuestionAdapter;
import com.mundane.examassistant.utils.FileUtils;
import com.mundane.examassistant.utils.ToastUtils;
import com.mundane.examassistant.widget.SlidingPageTransformer;

import org.greenrobot.greendao.query.Query;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerQuestionActivity extends BaseActivity {

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
	@BindView(R.id.view_pager)
	ViewPager mViewPager;
	@BindView(R.id.iv_shadow)
	ImageView mIvShadow;
	private SectionBean mSection;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dismissProgressDialog();
					ToastUtils.shwoToast("数据导入完成");
					mList.addAll(getConditionList());
					refreshView();
					break;
			}
		}
	};
	private QuestionAdapter mQuestionAdapter;

	private void refreshView() {
		mTvJump.setText(String.format("%d/%d", 1, mList.size()));
		mQuestionAdapter = new QuestionAdapter(mList);
		mViewPager.setAdapter(mQuestionAdapter);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mIvShadow.setTranslationX(-positionOffsetPixels);
			}

			@Override
			public void onPageSelected(int position) {
				mTvJump.setText(String.format("%d/%d", mViewPager.getCurrentItem() + 1, mList.size()));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		mViewPager.setPageTransformer(true, new SlidingPageTransformer());

	}

	private QuestionDao mQuestionDao;
	private List<Question> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_question);
		ButterKnife.bind(this);
		initData();
		initView();
	}

	@Override
	protected void onDestroy() {
		if (mHandler != null) {
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
		super.onDestroy();
	}

	private void initData() {
		mList = new ArrayList<>();
		mSection = getIntent().getParcelableExtra(SectionPracticeActivity.KEY_SECTION_ITEM);
		checkData();
	}

	private void checkData() {
		mList.addAll(getConditionList());
		if (mList.isEmpty()) {        //	数据库中没有数据, 需要导入数据
			showProgressDialog("正在导入数据中, 请稍后");
			new DbThread().start();
		} else {                    //	数据库中已经有数据, 直接用从数据库中取出的数据

		}

	}

	private List<Question> getConditionList() {
		if (mQuestionDao == null) {
			mQuestionDao = DbHelper.getQuestionDao();
		}
		Query<Question> query = mQuestionDao
				.queryBuilder()
				.where(QuestionDao.Properties.Course.eq(mSection.courseName), QuestionDao.Properties.Type.eq(mSection.questionType))
				.build();
		return query.list();
	}

	private class DbThread extends Thread {
		@Override
		public void run() {
			AssetManager assetManager = getAssets();
			try {
				String[] array = assetManager.list("resource");
				for (String fileName : array) {
					if (fileName.startsWith(mSection.courseName + mSection.questionType)) {
						InputStream inputStream = assetManager.open("resource/" + fileName);
						String text = FileUtils.readStringFromInputStream(inputStream);
						QuestionBean questionBean = JSON.parseObject(text, QuestionBean.class);
						List<QuestionBean.PlistBean.ArrayBean.DictBean> dict = questionBean.plist.array.dict;
						for (QuestionBean.PlistBean.ArrayBean.DictBean dictBean : dict) {
							List<String> string = dictBean.string;
							int size = string.size();
							Question question;
							if (size == 6) {
//								question = new Question(null, mSection.courseName, mSection.questionType, string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4), false, false);
								question = new Question();
								question.setCourse(mSection.courseName);
								question.setType(mSection.questionType);
								question.setQuestion(string.get(size - 1));
								question.setOptionA(string.get(0));
								question.setOptionB(string.get(1));
								question.setOptionC(string.get(2));
								question.setOptionD(string.get(3));
								question.setAnswer(string.get(size-2));
							} else if (size == 4) {
//								question = new Question(null, mSection.courseName, mSection.questionType, string.get(3), string.get(0), string.get(1), null, null, string.get(2), false, false);
								question = new Question();
								question.setCourse(mSection.courseName);
								question.setType(mSection.questionType);
								question.setQuestion(string.get(size - 1));
								question.setOptionA(string.get(0));
								question.setOptionB(string.get(1));
								question.setAnswer(string.get(size-2));

							} else {//	size == 7
								question = new Question();
								question.setCourse(mSection.courseName);
								question.setType(mSection.questionType);
								question.setQuestion(string.get(size - 1));
								question.setOptionA(string.get(0));
								question.setOptionB(string.get(1));
								question.setOptionC(string.get(2));
								question.setOptionD(string.get(3));
								question.setOptionE(string.get(4));
								question.setAnswer(string.get(size-2));
							}
							mQuestionDao.insert(question);
						}
						break;
					}
				}
				mHandler.sendEmptyMessage(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private void initView() {
		mIvArrow.setVisibility(View.GONE);
		mTvSelectCourse.setVisibility(View.GONE);
		mIvSetting.setVisibility(View.GONE);

		mIvBack.setVisibility(View.VISIBLE);
		mLlJump.setVisibility(View.VISIBLE);
		mTvJump.setText(String.format("%d/%d", 1, mList.size()));
		mLlMode.setVisibility(View.VISIBLE);
		mLlCollect.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if (!mList.isEmpty()) {
			refreshView();
		}
	}
}
