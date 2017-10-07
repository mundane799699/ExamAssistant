package com.mundane.examassistant.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.ui.adapter.SelectCoursePopupWindowRvAdapter;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.utils.ToastUtils;
import com.mundane.examassistant.widget.GlideImageLoader;
import com.mundane.examassistant.widget.SelectCoursePopupWindow;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.List;

import static com.mundane.examassistant.global.Constant.KEY_CURRENT_COURSE;

public class MainActivity extends BaseActivity {

	@BindView(R.id.tv_select_course)
	TextView mTvSelectCourse;
	@BindView(R.id.iv_arrow)
	ImageView mIvArrow;
	@BindView(R.id.iv_setting)
	ImageView mIvSetting;
	@BindView(R.id.fl_bg)
	FrameLayout mFlBg;
	@BindView(R.id.rl_title)
	RelativeLayout mRlTitle;
	@BindView(R.id.frameLayout_section_practice)
	FrameLayout mFlSectionPractice;
	@BindView(R.id.banner)
	Banner mBanner;
	@BindView(R.id.fl_my_collect)
	FrameLayout mFlMyCollect;
	@BindView(R.id.fl_practice_history)
	FrameLayout mFlPracticeHistory;
	@BindView(R.id.fl_exam)
	FrameLayout mFlExam;
	@BindView(R.id.fl_my_mistakes)
	FrameLayout mFlMyMistakes;
	private RecyclerView mRv;
	private List<CourseItem> mCourseList = new ArrayList<>();
	private SelectCoursePopupWindowRvAdapter mAdapter;
	private SelectCoursePopupWindow mCoursePopupWindow;
	private CourseItem mCurrentCourse;
	private final String TAG = "MainActivity";
	public static final String KEY_COURSE_NAME = "key_course_name";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
	}

	private void init() {
		// 顶部的四个菜单
		mCourseList.add(new CourseItem("近代史", false));
		mCourseList.add(new CourseItem("思修", false));
		mCourseList.add(new CourseItem("马克思", false));
		mCourseList.add(new CourseItem("毛概下", false));
		String currentCourse = SPUtils.getString(KEY_CURRENT_COURSE);
		if (TextUtils.isEmpty(currentCourse)) {
			mCurrentCourse = mCourseList.get(0);
			SPUtils.putString(KEY_CURRENT_COURSE, mCurrentCourse.name);
		} else {
			for (CourseItem courseItem : mCourseList) {
				if (TextUtils.equals(currentCourse, courseItem.name)) {
					mCurrentCourse = courseItem;
					break;
				}
			}
		}
		mCurrentCourse.isSelected = true;
		mTvSelectCourse.setText(mCurrentCourse.name);
		// 点击顶部按钮弹出的popupWindow
		View view = View.inflate(this, R.layout.popupwindow_select_course, null);
		mCoursePopupWindow = new SelectCoursePopupWindow(this, view);
		mRv = (RecyclerView) view.findViewById(R.id.rv);
		mRv.setLayoutManager(new GridLayoutManager(this, 4));
		mAdapter = new SelectCoursePopupWindowRvAdapter(mCourseList);
		mAdapter.setOnItemClickListener(new SelectCoursePopupWindowRvAdapter.OnItemClickListener() {
			@Override
			public void onItemClicked(CourseItem item) {
				item.isSelected = true;
				mCurrentCourse = item;
				mTvSelectCourse.setText(item.name);
				SPUtils.putString(KEY_CURRENT_COURSE, item.name);
				mCoursePopupWindow.dismiss();
				mAdapter.notifyDataSetChanged();
			}
		});
		mRv.setAdapter(mAdapter);
		mCoursePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				mFlBg.setVisibility(View.GONE);
				rotateArrow(180, 0);
			}
		});
		// 点击章节练习
		mFlSectionPractice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PracticeSelectActivity.class);
				intent.putExtra(KEY_COURSE_NAME, mCurrentCourse.name);
				startActivity(intent);
			}
		});
		// 点击我的收藏
		mFlMyCollect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyCollectionSelectActivity.class);
				intent.putExtra(KEY_COURSE_NAME, mCurrentCourse.name);
				startActivity(intent);
			}
		});
		// 点击我的错题
		mFlMyMistakes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyMistakeSelectActivity.class);
				intent.putExtra(KEY_COURSE_NAME, mCurrentCourse.name);
				startActivity(intent);
			}
		});


		// 轮播图
		List<Integer> list = new ArrayList<>();
		list.add(R.drawable.home_scroll1);
		list.add(R.drawable.home_scroll2);
		mBanner.setImages(list).setImageLoader(new GlideImageLoader()).setDelayTime(5000).start();
		// 练习记录和模拟考试还没有做
		mFlPracticeHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ToastUtils.toast("练习记录还没有做");
			}
		});
		mFlExam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ToastUtils.toast("模拟考试也还没有做");
			}
		});
	}


	@OnClick({R.id.tv_select_course, R.id.iv_arrow, R.id.iv_setting})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_select_course:
			case R.id.iv_arrow:
				if (mCoursePopupWindow.isShowing()) {
					mCoursePopupWindow.dismiss();
				} else {
					mCoursePopupWindow.showAsDropDown(mRlTitle);
					mFlBg.setVisibility(View.VISIBLE);
					rotateArrow(0, 180);
				}
				break;
			case R.id.iv_setting:
				Intent intent = new Intent(this, SettingActivity.class);
				startActivity(intent);
				break;
		}
	}


	private void rotateArrow(float from, float to) {
		ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvArrow, "rotation", from, to).setDuration(200);
		rotate.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				mTvSelectCourse.setClickable(false);
				mIvArrow.setClickable(false);
			}


			@Override
			public void onAnimationEnd(Animator animation) {
				mTvSelectCourse.setClickable(true);
				mIvArrow.setClickable(true);
			}
		});
		rotate.start();
	}

}
