package com.mundane.examassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CollectionItem;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.ui.adapter.SectionAdapter;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.utils.ResUtil;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.widget.RecycleViewDivider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.greendao.query.Query;

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
	private CourseItem          mCourseItem;
    private CollectionItem mCollectionItem;
	private List<SectionBean>   mSectionList;
	private SectionAdapter      mSectionAdapter;
    private String mCourseName;
	private final String KEY_POSTFIX = "lastSectionPosition";
	private QuestionDao mQuestionDao;
    private int mShowType; // 展示类型:0表示章节练习, 1表示我的收藏, 2表示我的错题

//	@BindView(R.id.tv)
//	TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_practice);
		ButterKnife.bind(this);
        Parcelable parcelable = getIntent().getParcelableExtra(MainActivity.PARCELABLE);
        if (parcelable instanceof CourseItem) {
            mShowType = 0;
            mCourseItem = getIntent().getParcelableExtra(MainActivity.PARCELABLE);
            mCourseName = mCourseItem.name;
        } else if (parcelable instanceof CollectionItem) {
            mShowType = 1;
            mCollectionItem = getIntent().getParcelableExtra(MainActivity.PARCELABLE);
            mCourseName = mCollectionItem.name;
        }
        init();
    }

	private final String TAG = "SectionPracticeActivity";
	public static final String KEY_SECTION_ITEM = "key_section_item";
    public static final String KEY_SHOW_TYPE = "key_show_type";

	private void init() {
		mIvBack.setVisibility(View.VISIBLE);
		mIvArrow.setVisibility(View.GONE);

        mSectionList = new ArrayList<>();

        //		ResUtil.importData(mSectionList, mCourseItem.name);

        ResUtil.initData(mSectionList, mCourseName);
        mQuestionDao = DbHelper.getQuestionDao();
        Query<Question> query = null;
        if (mShowType == 0) {
            mTvSelectCourse.setText(String.format("%s章节练习", mCourseName));
            query = mQuestionDao
                .queryBuilder()
                .where(QuestionDao.Properties.Course.eq(mCourseName))
                .build();
        } else if (mShowType == 1) {
            mTvSelectCourse.setText(String.format("%s收藏题目", mCourseName));
            query = mQuestionDao
                .queryBuilder()
                .where(QuestionDao.Properties.Course.eq(mCourseName), QuestionDao.Properties.IsCollected.eq(true))
                .build();
        }


		// 符合course的集合， 比如所有马克思的题目
		List<Question> questionList = query.list();

		for (Question question : questionList) {
			for (SectionBean sectionBean : mSectionList) {
				if (TextUtils.equals(question.getType(), sectionBean.questionType)) {
					sectionBean.questionNum++;
				}
			}
		}
		Iterator<SectionBean> iterator = mSectionList.iterator();
		while(iterator.hasNext()){
			SectionBean sectionBean = iterator.next();
			if (sectionBean.questionNum == 0) {
				iterator.remove();
			}
		}
		int lastSectionPosition = SPUtils.getInt(mCourseName + KEY_POSTFIX, -1);
		if (lastSectionPosition > -1) {
			for (int i = 0; i < mSectionList.size(); i++) {
				if (i == lastSectionPosition) {
					mSectionList.get(i).isSelected = true;
					break;
				}
			}
		}
		mSectionAdapter = new SectionAdapter(mSectionList);
		mSectionAdapter.setOnItemClickListener(new SectionAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(SectionBean section, int position) {
				section.isSelected = true;
				mSectionAdapter.notifyDataSetChanged();
				SPUtils.putInt(mCourseName + KEY_POSTFIX, position);
				Intent intent = new Intent(SectionPracticeActivity.this, AnswerQuestionActivity.class);
				intent.putExtra(KEY_SECTION_ITEM, section);
                intent.putExtra(KEY_SHOW_TYPE, mShowType);
                startActivity(intent);
			}
		});
		mRv.setLayoutManager(new LinearLayoutManager(this));
		mRv.setAdapter(mSectionAdapter);
		RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(this, 1), ContextCompat.getColor(this, R.color.commonGray));
		divider.setLeftOffset(DensityUtils.dp2px(this, 14));
		mRv.addItemDecoration(divider);
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
