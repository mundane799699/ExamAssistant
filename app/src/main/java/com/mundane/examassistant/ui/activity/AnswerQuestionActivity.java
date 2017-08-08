package com.mundane.examassistant.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
import com.mundane.examassistant.utils.SPUtils;
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
					ToastUtils.toast("数据导入完成");
					mList.addAll(getConditionList());
					refreshView();
					break;
			}
		}
	};
	private QuestionAdapter mQuestionAdapter;

	private void refreshView() {
		mTvJump.setText(String.format("%d/%d", 1, mList.size()));
		mQuestionAdapter = new QuestionAdapter(mList, mQuestionDao);
		mViewPager.setAdapter(mQuestionAdapter);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mIvShadow.setTranslationX(-positionOffsetPixels);
			}

			@Override
			public void onPageSelected(int position) {
				mTvJump.setText(String.format("%d/%d", mViewPager.getCurrentItem() + 1, mList.size()));
				SPUtils.putInt(mSection.courseName + mSection.questionType, position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		mViewPager.setPageTransformer(true, new SlidingPageTransformer());
		mViewPager.setCurrentItem(SPUtils.getInt(mSection.courseName + mSection.questionType));
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
		mQuestionDao = DbHelper.getQuestionDao();
		Query<Question> query = mQuestionDao
				.queryBuilder()
				.where(QuestionDao.Properties.Course.eq(mSection.courseName), QuestionDao.Properties.Type.eq(mSection.questionType))
				.build();
		List<Question> list = query.list();
		return list;
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
							Question question = new Question();
							question.setCourse(mSection.courseName);
							question.setType(mSection.questionType);
							question.setQuestion(string.get(size - 1));
							question.setAnswer(string.get(size - 2));
							String answer = question.getAnswer();

							question.setOptionA(string.get(0));
							question.setIsOptionACorrect(answer.contains("A"));

							question.setOptionB(string.get(1));
							question.setIsOptionBCorrect(answer.contains("B"));
							if (size == 6) {        //	ABCD

								question.setOptionC(string.get(2));
								question.setIsOptionCCorrect(answer.contains("C"));

								question.setOptionD(string.get(3));
								question.setIsOptionDCorrect(answer.contains("D"));
							} else if (size == 4) {        //	AB

							} else {//	size == 7 ABCDE
								question.setOptionC(string.get(2));
								question.setIsOptionCCorrect(answer.contains("C"));

								question.setOptionD(string.get(3));
								question.setIsOptionDCorrect(answer.contains("D"));

								question.setOptionE(string.get(4));
								question.setIsOptionECorrect(answer.contains("E"));
							}

							try {
								//因为我在实体类的里给queston添加了唯一约束,
								// 重复添加相同的question时会报错, 所以这里要try...catch掉, 防止插入重复的question
								mQuestionDao.insert(question);
							} catch (Exception e) {
								e.printStackTrace();
							}
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
		mLlJump.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showBottomSheetDialog();
			}
		});
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

	private void showBottomSheetDialog() {
		BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, mLlJump, false);
		bottomSheetDialog.setContentView(view);
		bottomSheetDialog.show();
	}
}
