package com.mundane.examassistant.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.ui.adapter.ContentAdapter;
import com.mundane.examassistant.ui.adapter.SelectCoursePopupWindowRvAdapter;
import com.mundane.examassistant.widget.SelectCoursePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
	@BindView(R.id.rv_content)
	RecyclerView mRvContent;
	private RecyclerView mRv;
	private List<CourseItem> mList = new ArrayList<>();
	private SelectCoursePopupWindowRvAdapter mAdapter;
	private SelectCoursePopupWindow mCoursePopupWindow;
	private ContentAdapter mContentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
	}

	private final String TAG = "MainActivity";

	private void init() {
		StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		mRvContent.setLayoutManager(manager);
		mContentAdapter = new ContentAdapter();
		mRvContent.setAdapter(mContentAdapter);
	}

	@OnClick({R.id.tv_select_course, R.id.iv_arrow, R.id.iv_setting})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_select_course:
			case R.id.iv_arrow:
				if (mCoursePopupWindow == null) {
					mCoursePopupWindow = createPopupWindow();
				}
				if (mCoursePopupWindow.isShowing()) {
					mCoursePopupWindow.dismiss();
				} else {
					mCoursePopupWindow.showAsDropDown(mRlTitle);
					mFlBg.setVisibility(View.VISIBLE);
					rotateArrow(0, 180);
				}

				break;
			case R.id.iv_setting:
				break;
		}
	}

	private void rotateArrow(float from, float to) {
		ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvArrow, "rotation", from, to).setDuration(200);
		rotate.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				setSelectCourse(false);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				setSelectCourse(true);
			}
		});
		rotate.start();
	}

	private void setSelectCourse(boolean isEnable) {
		mTvSelectCourse.setClickable(isEnable);
		mIvArrow.setClickable(isEnable);
	}

	private SelectCoursePopupWindow createPopupWindow() {
		View view = View.inflate(this, R.layout.popupwindow_select_course, null);
		initView(view);
		SelectCoursePopupWindow customPopupWindow = new SelectCoursePopupWindow(this, view);
		customPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				mFlBg.setVisibility(View.GONE);
				rotateArrow(180, 0);
			}
		});
		return customPopupWindow;
	}

	private void initView(View view) {
		mRv = (RecyclerView) view.findViewById(R.id.rv);
		mRv.setLayoutManager(new GridLayoutManager(this, 4));
		if (mList.isEmpty()) {
			mList.add(new CourseItem("近代史"));
			mList.add(new CourseItem("思修"));
			mList.add(new CourseItem("马克思"));
			mList.add(new CourseItem("毛概下"));
		}
		mAdapter = new SelectCoursePopupWindowRvAdapter(mList);
		mRv.setAdapter(mAdapter);
	}
}
