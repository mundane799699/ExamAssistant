package com.mundane.examassistant.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.bean.QuestionBean;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.ui.adapter.SectionAdapter;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.utils.FileUtils;
import com.mundane.examassistant.utils.GsonUtil;
import com.mundane.examassistant.utils.LogUtils;
import com.mundane.examassistant.widget.RecycleViewDivider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SectionPracticeActivity extends BaseActivity {
	@BindView(R.id.iv_back)
	ImageView mIvBack;
	@BindView(R.id.tv_select_course)
	TextView mTvSelectCourse;
	@BindView(R.id.iv_arrow)
	ImageView mIvArrow;
	@BindView(R.id.iv_setting)
	ImageView mIvSetting;
	@BindView(R.id.rl_title)
	RelativeLayout mRlTitle;
	@BindView(R.id.rv)
	RecyclerView mRv;
	private CourseItem mCourseItem;
	private List<SectionBean> mSectionList;
	private SectionAdapter mSectionAdapter;
	private QuestionDao mQuestionDao;
	private List<Question> mQuestionList;

//	@BindView(R.id.tv)
//	TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_practice);
		ButterKnife.bind(this);
		mCourseItem = getIntent().getParcelableExtra(MainActivity.PARCELABLE);
		init();
	}

	private final String TAG = "SectionPracticeActivity";

	//以思修为一张表， 近代史为一张表， 马克思为一张表， 毛概下为一张表

	private void init() {
		checkData();

		mIvBack.setVisibility(View.VISIBLE);
		mIvArrow.setVisibility(View.GONE);
		mTvSelectCourse.setText(String.format("%s章节练习", mCourseItem.name));

		mSectionList = new ArrayList<>();
		mSectionList.add(new SectionBean("单选一", "1~100题"));
		mSectionList.add(new SectionBean("单选二", "101~200题"));
		mSectionList.add(new SectionBean("单选三", "201~300题"));
		mSectionList.add(new SectionBean("单选四", "301~400题"));
		mSectionList.add(new SectionBean("单选五", "401~450题"));
		mSectionList.add(new SectionBean("多选一", "1~60题"));
		mSectionList.add(new SectionBean("多选二", "61~120题"));
		mSectionList.add(new SectionBean("多选三", "121~178题"));

		mSectionAdapter = new SectionAdapter(mSectionList);
		mRv.setLayoutManager(new LinearLayoutManager(this));
		mRv.setAdapter(mSectionAdapter);
		RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(this, 1), ContextCompat.getColor(this, R.color.commonGray));
		divider.setLeftOffset(DensityUtils.dp2px(this, 14));
		mRv.addItemDecoration(divider);
	}

	private void checkData() {
		mQuestionDao = DbHelper.getQuestionDao();
		mQuestionList = mQuestionDao.loadAll();
		if (mQuestionList.isEmpty()) {
			loadData();
		}

	}

	private void loadData() {
		long t1 = System.nanoTime();//纳秒是10的-9次方秒
		AssetManager assetManager = getAssets();
//		try {
//			String[] list = assetManager.list("texts");
//			for (String s : list) {
//				LogUtils.d(s);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String[] locales = assetManager.getLocales();
//		for (String locale : locales) {
//			LogUtils.d(locale);
//		}
		try {
			InputStream inputStream = assetManager.open("resource/思修单选1.txt");
			String text = FileUtils.readStringFromInputStream(inputStream);
			long t2 = System.nanoTime();
			QuestionBean questionBean = GsonUtil.parseJsonToBean(text, QuestionBean.class);
			LogUtils.d("时间：" + (t2 - t1));
//			public static class ArrayBean {
//				public List<DictBean> dict;
//
//				public static class DictBean {
//					public List<String> key;//	{ "A", "B", "C", "D", "answer", "subject"}
//					public List<String> string;//	{"行为关系", "思想关系", "利益关系", "各种社会资源", "A", "1.法的调整对象是（ ）。"}
//				}
//			}
			List<QuestionBean.PlistBean.ArrayBean.DictBean> dictBeanList = questionBean.plist.array.dict;
			for (int i = 0; i < dictBeanList.size(); i++) {
				QuestionBean.PlistBean.ArrayBean.DictBean dictBean = dictBeanList.get(i);
//				List<String> key = dictBean.key;
				List<String> string = dictBean.string;
				Question question = new Question((long) (i+1), string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4));
				mQuestionDao.insert(question);
			}
			Toast.makeText(this, "数据导入完成", Toast.LENGTH_SHORT).show();
//			mQuestionList = mQuestionDao.loadAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClick({R.id.iv_back, R.id.tv_select_course, R.id.iv_setting, R.id.rl_title})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.iv_back:
				finish();
				break;
			case R.id.tv_select_course:
				break;
			case R.id.iv_setting:
				break;
			case R.id.rl_title:
				break;
		}
	}
}
