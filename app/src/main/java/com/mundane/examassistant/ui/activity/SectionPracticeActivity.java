package com.mundane.examassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.ui.adapter.SectionAdapter;
import com.mundane.examassistant.utils.DensityUtils;
import com.mundane.examassistant.utils.ResUtil;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.widget.RecycleViewDivider;

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
	private CourseItem          mCourseItem;
	private List<SectionBean>   mSectionList;
	private SectionAdapter      mSectionAdapter;
	private final String KEY_POSTFIX = "lastSectionPosition";

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
	public static final String KEY_SECTION_ITEM = "key_section_item";

	private void init() {
		mIvBack.setVisibility(View.VISIBLE);
		mIvArrow.setVisibility(View.GONE);
		mTvSelectCourse.setText(String.format("%s章节练习", mCourseItem.name));

		mSectionList = new ArrayList<>();

		ResUtil.importData(mSectionList, mCourseItem.name);
		int lastSectionPosition = SPUtils.getInt(mCourseItem.name + KEY_POSTFIX, -1);
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
				SPUtils.putInt(mCourseItem.name + KEY_POSTFIX, position);
				Intent intent = new Intent(SectionPracticeActivity.this, AnswerQuestionActivity.class);
				intent.putExtra(KEY_SECTION_ITEM, section);
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
