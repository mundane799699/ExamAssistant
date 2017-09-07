package com.mundane.examassistant.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.ui.adapter.SectionAdapter;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.utils.ResUtil;
import com.mundane.examassistant.widget.RecycleViewDivider;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMistakesSelectActivity extends AppCompatActivity {

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
	@BindView(R.id.rv)
	RecyclerView mRv;
	private String mCourseName;
	private ArrayList<SectionBean> mSectionList;
	private SectionAdapter mSectionAdapter;
	private QuestionDao mQuestionDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mistakes_select);
		ButterKnife.bind(this);
		mCourseName = getIntent().getStringExtra(MainActivity.KEY_COURSE_NAME);
		init();
	}

	private void init() {
		mIvBack.setVisibility(View.VISIBLE);
		mIvArrow.setVisibility(View.GONE);
		mTvSelectCourse.setText(String.format("%s收藏题目", mCourseName));

		mSectionList = new ArrayList<>();
		refreshData();
		mSectionAdapter = new SectionAdapter(mSectionList);
		mSectionAdapter.setOnItemClickListener(new SectionAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(SectionBean section, int position) {

			}
		});
		mRv.setLayoutManager(new LinearLayoutManager(this));
		mRv.setAdapter(mSectionAdapter);
		RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(this, 1), ContextCompat.getColor(this, R.color.commonGray));
		divider.setLeftOffset(DensityUtils.dp2px(this, 14));
		mRv.addItemDecoration(divider);
	}

	private void refreshData() {

		List<SectionBean> allSectionList = ResUtil.initData(mCourseName);

		mQuestionDao = DbHelper.getQuestionDao();

		Query<Question> query = mQuestionDao
				.queryBuilder()
				.where(QuestionDao.Properties.Course.eq(mCourseName), QuestionDao.Properties.IsAnsweredWrong.eq(true))
				.build();

		List<Question> questionList = query.list();
		// 对查询出来的所有的马克思的题目进行检查, 然后分类
		for (Question question : questionList) {
			for (SectionBean sectionBean : allSectionList) {
				// 如果属于该类
				if (TextUtils.equals(question.getType(), sectionBean.questionType)) {
					sectionBean.questionNum++;
				}
			}
		}
		Iterator<SectionBean> iterator = allSectionList.iterator();
		while(iterator.hasNext()){
			SectionBean sectionBean = iterator.next();
			if (sectionBean.questionNum == 0) {
				iterator.remove();
			}
		}
		mSectionList.clear();
		mSectionList.addAll(allSectionList);
		//int lastSectionPosition = SPUtils.getInt(mCourseName + KEY_POSTFIX, -1);
		//if (lastSectionPosition > -1) {
		//	for (int i = 0; i < mSectionList.size(); i++) {
		//		if (i == lastSectionPosition) {
		//			mSectionList.get(i).isSelected = true;
		//			break;
		//		}
		//	}
		//}
	}
}
