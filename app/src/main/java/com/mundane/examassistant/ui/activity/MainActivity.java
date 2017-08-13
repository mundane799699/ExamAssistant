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

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.CourseItem;
import com.mundane.examassistant.ui.adapter.SelectCoursePopupWindowRvAdapter;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.widget.GlideImageLoader;
import com.mundane.examassistant.widget.SelectCoursePopupWindow;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mundane.examassistant.global.Constant.KEY_CURRENT_COURSE;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_select_course)
    TextView       mTvSelectCourse;
    @BindView(R.id.iv_arrow)
    ImageView      mIvArrow;
    @BindView(R.id.iv_setting)
    ImageView      mIvSetting;
    @BindView(R.id.fl_bg)
    FrameLayout    mFlBg;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.frameLayout_section_practice)
    FrameLayout    mFlSectionPractice;
    @BindView(R.id.banner)
	Banner mBanner;
    private RecyclerView mRv;
    private List<CourseItem> mCourseList = new ArrayList<>();
    private SelectCoursePopupWindowRvAdapter mAdapter;
    private SelectCoursePopupWindow          mCoursePopupWindow;
    private SelectCoursePopupWindow          mCustomPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private final       String TAG        = "MainActivity";
    public static final String PARCELABLE = "parcelable";


    private void init() {
		List<Integer> list=new ArrayList<>();
		list.add(R.drawable.home_scroll1);
		list.add(R.drawable.home_scroll2);

		mBanner.setImages(list)
				.setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
				.start();
        mFlSectionPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SectionPracticeActivity.class);
                intent.putExtra(PARCELABLE, mCurrentCourseItem);
                startActivity(intent);
            }
        });

        if (mCourseList.isEmpty()) {
            mCourseList.add(new CourseItem("近代史", false));
            mCourseList.add(new CourseItem("思修", false));
            mCourseList.add(new CourseItem("马克思", false));
            mCourseList.add(new CourseItem("毛概下", false));
        }
        String currentCourse = SPUtils.getString(KEY_CURRENT_COURSE);
        if (TextUtils.isEmpty(currentCourse)) {
            mCurrentCourseItem = mCourseList.get(0);
        } else {
            for (CourseItem courseItem : mCourseList) {
                if (TextUtils.equals(currentCourse, courseItem.name)) {
                    mCurrentCourseItem = courseItem;
                    break;
                }
            }
        }
        mCurrentCourseItem.isSelected = true;
        mTvSelectCourse.setText(mCurrentCourseItem.name);
        SPUtils.putString(KEY_CURRENT_COURSE, mCurrentCourseItem.name);
    }


    @OnClick({ R.id.tv_select_course, R.id.iv_arrow, R.id.iv_setting })
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
        mCustomPopupWindow = new SelectCoursePopupWindow(this, view);
        mCustomPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mFlBg.setVisibility(View.GONE);
                rotateArrow(180, 0);
            }
        });
        return mCustomPopupWindow;
    }


    private CourseItem mCurrentCourseItem;


    private void initView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(this, 4));

        mAdapter = new SelectCoursePopupWindowRvAdapter(mCourseList);
        mAdapter.setOnItemClickListener(new SelectCoursePopupWindowRvAdapter.OnItemClickListener() {

            @Override
            public void onItemClicked(CourseItem item) {
                item.isSelected = true;
                mAdapter.notifyDataSetChanged();
                mCurrentCourseItem = item;
                mTvSelectCourse.setText(item.name);
                SPUtils.putString(KEY_CURRENT_COURSE, item.name);
                mCustomPopupWindow.dismiss();
            }
        });
        mRv.setAdapter(mAdapter);
    }

}
