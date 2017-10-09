package com.mundane.examassistant.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.SectionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.global.Constant;
import com.mundane.examassistant.ui.adapter.BottomSheetRvAdapter;
import com.mundane.examassistant.ui.adapter.MistakeViewpagerAdapter;
import com.mundane.examassistant.utils.SPUtils;
import com.mundane.examassistant.widget.BottomSheetItemDecoration;
import com.mundane.examassistant.widget.SlidingPageTransformer;
import com.mundane.examassistant.widget.view.ScrollerViewPager;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMistakeAnswerActivity extends BaseActivity {

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
    private QuestionDao              mQuestionDao;
    private List<Question> mDataList;
    private SectionBean              mSection;
    private MistakeViewpagerAdapter mViewPagerAdapter;
	private long mAnswerRightRemoveTimes; // 错题答对后自动移除的次数
	private List<Question> mOriginalList;
	private long mDelayTime;
	private long mAnswerRightFlipTime;
	private Handler mHandler = new Handler();

	@Override
	protected void onDestroy() {
		mHandler.removeCallbacksAndMessages(null);
		mHandler = null;
		super.onDestroy();
	}


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mistake_answer);
        ButterKnife.bind(this);

        mQuestionDao = DbHelper.getQuestionDao();
        mDataList = new ArrayList<>();
        mSection = getIntent().getParcelableExtra(MyMistakeSelectActivity.KEY_MYMISTAKE_SELECT);
        Query<Question> query = mQuestionDao
            .queryBuilder()
            .where(QuestionDao.Properties.Course.eq(mSection.courseName), QuestionDao.Properties.Type.eq(mSection.questionType), QuestionDao.Properties.IsAnsweredWrong.eq(true))
            .build();
		mOriginalList = query.list();
        mDataList.addAll(mOriginalList);
        clearHistory();

		mDelayTime = SPUtils.getLong(Constant.KEY_AUTO_FLIP_TIME);
		mAnswerRightFlipTime = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME);

		mAnswerRightRemoveTimes = SPUtils.getLong(Constant.KEY_ANSWER_RIGHT_REMOVE_TIMES);

		mIvArrow.setVisibility(View.GONE);
        mTvSelectCourse.setVisibility(View.GONE);
        mIvSetting.setVisibility(View.GONE);
        mIvBack.setVisibility(View.VISIBLE);
        mLlJump.setVisibility(View.VISIBLE);
        mLlCollect.setVisibility(View.VISIBLE);
        mLlMode.setVisibility(View.VISIBLE);
        mTvJump.setText(String.format("%d/%d", 1, mDataList.size()));
        mLlJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
		mViewPager.setOnActionListener(new ScrollerViewPager.OnActionListener() {
			@Override
			public void onActionUp() {
				if (isCheatMode()) {
					if (mDelayTime > 0) {
						startAutoPlay();
					}
				}
			}

			@Override
			public void onActionDown() {
				if (isCheatMode()) {
					stopAutoPlay();
				}
			}
		});
        mLlMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheatMode()) {
                    mTvMode.setText("开挂模式");
                    mIvMode.setImageResource(R.drawable.answer_mode_show);
                    for (Question question : mDataList) {
                        question.setIsShowAnswer(true);
                    }
					if (mDelayTime > 0) {
						startAutoPlay();
					}
                } else {
                    mTvMode.setText("答题模式");
                    mIvMode.setImageResource(R.drawable.answer_mode_hide);
                    for (Question question : mDataList) {
                        question.setIsShowAnswer(false);
                    }
					stopAutoPlay();
                }
                // 刷新adapter
                refreshRvAdapter();
            }
        });

        mLlCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectText = mTvCollect.getText().toString();
                Question question = mDataList.get(mViewPager.getCurrentItem());
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

        mViewPagerAdapter = new MistakeViewpagerAdapter(mDataList, mQuestionDao);
		mViewPagerAdapter.setOnAnswerRightListener(new MistakeViewpagerAdapter.OnAnswerRight() {
			@Override
			public void answerRight(Question question, int position) {
				if (mAnswerRightFlipTime > 0) {
					mHandler.postDelayed(mAnswerRightFlipTask, mAnswerRightFlipTime);
				}
				int currentQuestionAnswerRightTimes = question.getAnswerRightTimes();
				currentQuestionAnswerRightTimes++;
				if (mAnswerRightRemoveTimes > 0) {
					if (currentQuestionAnswerRightTimes >= mAnswerRightRemoveTimes) { // 如果已经达到了移除的次数
						question.setAnswerRightTimes(0);
						question.setIsAnsweredWrong(false); // 从错题中移除
					} else { // 还没有达到规定的移除次数, 就只是将答对的次数+1, 然后更新数据库
						question.setAnswerRightTimes(currentQuestionAnswerRightTimes);
						question.setIsAnsweredWrong(true);
					}
					Question updateQuestion = mOriginalList.get(position);
					// 只是更新答对的次数以及是否标记为错题
					updateQuestion.setAnswerRightTimes(question.getAnswerRightTimes());
					updateQuestion.setIsAnsweredWrong(question.getIsAnsweredWrong());
					mQuestionDao.update(updateQuestion);
				}
			}
		});
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIvShadow.setTranslationX(-positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                int max = mDataList.size();
                String text = String.format("%d/%d", mViewPager.getCurrentItem() + 1, max);
                mTvJump.setText(text);
                //SPUtils.putInt(mSection.courseName + mSection.questionType + POSTFIX, position);
                Question question = mDataList.get(position);
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
        //int lastPosition = SPUtils.getInt(mSection.courseName + mSection.questionType + POSTFIX);
        mViewPager.setCurrentItem(0);
        Question firstQuestion = mDataList.get(0);
        if (firstQuestion.getIsCollected()) {
            mTvCollect.setText("已收藏");
            mIvCollect.setImageResource(R.drawable.answer_collection_true);
        } else {
            mTvCollect.setText("收藏");
            mIvCollect.setImageResource(R.drawable.answer_collection_false);
        }
    }

	public void startAutoPlay() {
		mHandler.removeCallbacks(mCheatAutoFlipTask);
		mHandler.postDelayed(mCheatAutoFlipTask, mDelayTime);
	}

	public void stopAutoPlay() {
		mHandler.removeCallbacks(mCheatAutoFlipTask);
	}

	private final Runnable mAnswerRightFlipTask = new Runnable() {
		@Override
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			if (currentItem < mDataList.size() - 1) {
				mViewPager.setCurrentItem(currentItem + 1, true);
			}
		}
	};

	private final Runnable mCheatAutoFlipTask = new Runnable() {
		@Override
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			if (currentItem < mDataList.size() - 1) {
				mViewPager.setCurrentItem(currentItem + 1, true);
				mHandler.postDelayed(mCheatAutoFlipTask, mDelayTime);
			}
		}
	};

	// 是否是开挂模式
	private boolean isCheatMode() {
		String modeText = mTvMode.getText().toString();
		if (TextUtils.equals("开挂模式", modeText)) { // 只有开挂模式下才有自动轮播
			return true;
		} else {
			return false;
		}
	}


	@Override
	public void finish() {
		setResult(RESULT_OK);
		super.finish();
	}


	private void refreshRvAdapter() {
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


    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
		bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// 关闭bottomSheetDialog的时候开始轮播
				if (isCheatMode()) {
					if (mDelayTime > 0) {
						startAutoPlay();
					}
				}
			}
		});
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, mLlJump, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        TextView tvRight = (TextView) view.findViewById(R.id.tv_right);
        TextView tvWrong = (TextView) view.findViewById(R.id.tv_wrong);
        TextView tvUndo = (TextView) view.findViewById(R.id.tv_undo);
        Button btnClear = (Button) view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyMistakeAnswerActivity.this);
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
                        mViewPager.setCurrentItem(mDataList.size() - 1, false);
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
        for (Question question : mDataList) {
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
        BottomSheetRvAdapter adapter = new BottomSheetRvAdapter(mDataList);
        adapter.setOnItemClickListener(new BottomSheetRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                mViewPager.setCurrentItem(position, true);
                bottomSheetDialog.dismiss();
            }
        });
        rv.setAdapter(adapter);
        bottomSheetDialog.setContentView(view);
		// 打开bottomSheetDialog的时候停止轮播
		bottomSheetDialog.show();
		if (isCheatMode()) {
			stopAutoPlay();
		}
    }

    private void clearHistory() {
        for (Question question : mDataList) {
            //question.setIsAnsweredWrong(false);
            question.setHaveBeenAnswered(false);
            question.setOptionAStatus(0);
            question.setOptionBStatus(0);
            question.setOptionCStatus(0);
            question.setOptionDStatus(0);
            question.setOptionEStatus(0);
        }
        //mQuestionDao.updateInTx(mDataList);
    }
}
