package com.mundane.examassistant.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.ui.adapter.BottomSheetRvAdapter;
import com.mundane.examassistant.ui.adapter.QuestionAdapter;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.widget.BottomSheetItemDecoration;
import com.mundane.examassistant.widget.SlidingPageTransformer;
import com.mundane.examassistant.widget.view.ScrollerViewPager;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.Query;

public class AnswerQuestionActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView         mIvBack;
    @BindView(R.id.tv_select_course)
    TextView          mTvSelectCourse;
    @BindView(R.id.iv_jump)
    ImageView         mIvJump;
    @BindView(R.id.tv_jump)
    TextView          mTvJump;
    @BindView(R.id.ll_jump)
    LinearLayout      mLlJump;
    @BindView(R.id.iv_mode)
    ImageView         mIvMode;
    @BindView(R.id.tv_mode)
    TextView          mTvMode;
    @BindView(R.id.ll_mode)
    LinearLayout      mLlMode;
    @BindView(R.id.iv_collect)
    ImageView         mIvCollect;
    @BindView(R.id.tv_collect)
    TextView          mTvCollect;
    @BindView(R.id.ll_collect)
    LinearLayout      mLlCollect;
    @BindView(R.id.iv_arrow)
    ImageView         mIvArrow;
    @BindView(R.id.iv_setting)
    ImageView         mIvSetting;
    @BindView(R.id.rl_title)
    RelativeLayout    mRlTitle;
    @BindView(R.id.view_pager)
    ScrollerViewPager mViewPager;
    @BindView(R.id.iv_shadow)
    ImageView         mIvShadow;
    private SectionBean     mSection;
    private QuestionAdapter mViewPagerAdapter;
    private final String TAG = "AnswerQuestionActivity";
    private int mShowType;


    private void refreshView() {
        mTvJump.setText(String.format("%d/%d", 1, mList.size()));
        mViewPagerAdapter = new QuestionAdapter(mList, mQuestionDao);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIvShadow.setTranslationX(-positionOffsetPixels);
            }


            @Override
            public void onPageSelected(int position) {
                mTvJump.setText(String.format("%d/%d", mViewPager.getCurrentItem() + 1, mList.size()));
                Log.d(TAG, "position = " + position);
                SPUtils.putInt(mSection.courseName + mSection.questionType, position);
                Question question = mList.get(position);
                if (question.getIsCollected()) {
                    mTvCollect.setText("已收藏");
                    mIvCollect.setImageResource(R.drawable.answer_collection_true);
                } else {
                    mTvCollect.setText("收藏");
                    mIvCollect.setImageResource(R.drawable.answer_collection_false);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setPageTransformer(true, new SlidingPageTransformer());
        int lastPosition = SPUtils.getInt(mSection.courseName + mSection.questionType);
        if (mShowType == 0) {
            mViewPager.setCurrentItem(lastPosition);
        } else if (mShowType == 1) {

        }
        Question lastQuestion = mList.get(lastPosition);
        if (lastQuestion.getIsCollected()) {
            mTvCollect.setText("已收藏");
            mIvCollect.setImageResource(R.drawable.answer_collection_true);
        } else {
            mTvCollect.setText("收藏");
            mIvCollect.setImageResource(R.drawable.answer_collection_false);
        }
    }


    private QuestionDao    mQuestionDao;
    private List<Question> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        initData();
        if (mList.isEmpty()) {
            mIvArrow.setVisibility(View.GONE);
            mIvSetting.setVisibility(View.GONE);
            return;
        }
        initView();
    }


    private void initData() {
        mQuestionDao = DbHelper.getQuestionDao();
        mList = new ArrayList<>();
        mSection = getIntent().getParcelableExtra(SectionPracticeActivity.KEY_SECTION_ITEM);
        mShowType = getIntent().getIntExtra(SectionPracticeActivity.KEY_SHOW_TYPE, 0);
        mList.addAll(getConditionList());
    }


    private List<Question> getConditionList() {
        Query<Question> query = null;
        if (mShowType == 0) {
            query = mQuestionDao.queryBuilder()
                .where(QuestionDao.Properties.Course.eq(mSection.courseName), QuestionDao.Properties.Type.eq(mSection.questionType))
                .build();
        } else if (mShowType == 1) {
            query = mQuestionDao.queryBuilder()
                .where(QuestionDao.Properties.Course.eq(mSection.courseName), QuestionDao.Properties.Type.eq(mSection.questionType), QuestionDao.Properties.IsCollected.eq(true))
                .build();
        }

        List<Question> list = query.list();
        return list;
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
        mLlMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modeText = mTvMode.getText().toString();
                if (TextUtils.equals("答题模式", modeText)) {
                    mTvMode.setText("开挂模式");
                    mIvMode.setImageResource(R.drawable.answer_mode_show);
                    for (Question question : mList) {
                        question.setIsShowAnswer(true);
                    }
                } else {
                    mTvMode.setText("答题模式");
                    mIvMode.setImageResource(R.drawable.answer_mode_hide);
                    for (Question question : mList) {
                        question.setIsShowAnswer(false);
                    }
                }
                // 刷新adapter
                int linearLayoutCount = mViewPager.getChildCount();
                for (int i = 0; i < linearLayoutCount; i++) {
                    if (mViewPager.getChildAt(i) instanceof LinearLayout) {
                        LinearLayout linearLayout = (LinearLayout) mViewPager.getChildAt(i);
                        int viewCount = linearLayout.getChildCount();
                        for (int j = 0; j < viewCount; j++) {
                            if (linearLayout.getChildAt(j) instanceof RecyclerView) {
                                RecyclerView rv = (RecyclerView) linearLayout.getChildAt(j);
                                rv.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
        mLlCollect.setVisibility(View.VISIBLE);
        mLlCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectText = mTvCollect.getText().toString();
                Question question = mList.get(mViewPager.getCurrentItem());
                if (TextUtils.equals("收藏", collectText)) {
                    mTvCollect.setText("已收藏");
                    mIvCollect.setImageResource(R.drawable.answer_collection_true);
                    question.setIsCollected(true);
                } else {
                    mTvCollect.setText("收藏");
                    mIvCollect.setImageResource(R.drawable.answer_collection_false);
                    question.setIsCollected(false);
                }
                mQuestionDao.update(question);
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshView();
    }


    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, mLlJump, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        TextView tvRight = (TextView) view.findViewById(R.id.tv_right);
        TextView tvWrong = (TextView) view.findViewById(R.id.tv_wrong);
        TextView tvUndo = (TextView) view.findViewById(R.id.tv_undo);
        Button btnClear = (Button) view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnswerQuestionActivity.this);
                builder.setTitle("确定要删除历史记录吗?").setMessage("将清空该章节的答题记录").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 清除历史记录
                        clearHistory();
                        mViewPager.setCurrentItem(mList.size() - 1, false);
                        // 第二个参数为false时有空白页的bug
                        mViewPager.setNoDuration(true);
                        mViewPager.setCurrentItem(0, true);
                        mViewPager.setNoDuration(false);
                        bottomSheetDialog.dismiss();
                    }
                }).create().show();
            }
        });
        int rightQuestionCount = 0;
        int wrongQuestionCount = 0;
        int undoQuestionCount = 0;
        for (Question question : mList) {
            if (question.getHaveBeenAnswered()) {
                if (question.getIsAnsweredWrong()) {
                    wrongQuestionCount++;
                } else {
                    rightQuestionCount++;
                }
            } else {
                undoQuestionCount++;
            }
        }
        tvRight.setText(String.format("%d", rightQuestionCount));
        tvWrong.setText(String.format("%d", wrongQuestionCount));
        tvUndo.setText(String.format("%d", undoQuestionCount));
        rv.setLayoutManager(new GridLayoutManager(this, 6));
        rv.addItemDecoration(new BottomSheetItemDecoration(this));
        BottomSheetRvAdapter adapter = new BottomSheetRvAdapter(mList);
        adapter.setOnItemClickListener(new BottomSheetRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                mViewPager.setCurrentItem(position, true);
                bottomSheetDialog.dismiss();
            }
        });
        rv.setAdapter(adapter);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }


    private void clearHistory() {
        for (Question question : mList) {
            question.setIsAnsweredWrong(false);
            question.setHaveBeenAnswered(false);
            question.setOptionAStatus(0);
            question.setOptionBStatus(0);
            question.setOptionCStatus(0);
            question.setOptionDStatus(0);
            question.setOptionEStatus(0);
        }
        mQuestionDao.updateInTx(mList);
    }
}
