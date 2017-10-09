package com.mundane.examassistant.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
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
import butterknife.OnClick;

public class MyMistakeSelectActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView      mIvBack;
    @BindView(R.id.tv_select_course)
    TextView       mTvSelectCourse;
    @BindView(R.id.iv_clear)
    ImageView      mIvClear;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.rv)
    RecyclerView   mRv;
	private String mCourseName;
	private ArrayList<SectionBean> mSectionList;
	private SectionAdapter mSectionAdapter;
	private QuestionDao mQuestionDao;
    public static final String KEY_MYMISTAKE_SELECT = "key_mymistake_select";
    private final int REQUEST_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mistakes_select);
		ButterKnife.bind(this);
		mCourseName = getIntent().getStringExtra(MainActivity.KEY_COURSE_NAME);
		mQuestionDao = DbHelper.getQuestionDao();
		init();
	}

	private void init() {
		mIvBack.setVisibility(View.VISIBLE);
		mTvSelectCourse.setText(String.format("%s错题", mCourseName));
		mIvClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MyMistakeSelectActivity.this);
				builder
						.setTitle(String.format("确定要删除%s的所有错题吗?", mCourseName))
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Query<Question> query = mQuestionDao
								.queryBuilder()
								.where(QuestionDao.Properties.Course.eq(mCourseName))
								.build();
						List<Question> list = query.list();
						for (Question question : list) {
							question.setIsAnsweredWrong(false);
						}
						mQuestionDao.updateInTx(list);
						finish();
					}
				}).create().show();
			}
		});

		mSectionList = new ArrayList<>();
		refreshData();
		mSectionAdapter = new SectionAdapter(mSectionList);
		mSectionAdapter.setOnItemClickListener(new SectionAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(SectionBean section, int position) {
                Intent intent = new Intent(MyMistakeSelectActivity.this, MyMistakeAnswerActivity.class);
                intent.putExtra(KEY_MYMISTAKE_SELECT, section);
                startActivityForResult(intent, REQUEST_CODE);
			}
		});
		mRv.setLayoutManager(new LinearLayoutManager(this));
		mRv.setAdapter(mSectionAdapter);
		RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(this, 1), ContextCompat.getColor(this, R.color.commonGray));
		divider.setLeftOffset(DensityUtils.dp2px(this, 14));
		mRv.addItemDecoration(divider);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
                //Toast.makeText(this, "该刷新视图数据了", Toast.LENGTH_SHORT).show();
                refreshData();
                mSectionAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

	private void refreshData() {
		List<SectionBean> allSectionList = ResUtil.initData(mCourseName);
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

    @OnClick({R.id.iv_back, R.id.tv_select_course, R.id.iv_clear, R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_course:
                break;
            case R.id.iv_clear:
                break;
            case R.id.rl_title:
                break;
        }
    }
}
