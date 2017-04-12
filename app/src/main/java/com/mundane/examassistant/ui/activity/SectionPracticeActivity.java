package com.mundane.examassistant.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.ProblemBean;
import com.mundane.examassistant.utils.FileUtils;
import com.mundane.examassistant.utils.GsonUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SectionPracticeActivity extends BaseActivity {

	@BindView(R.id.tv)
	TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_practice);
		ButterKnife.bind(this);
		init();
	}

	private final String TAG = "SectionPracticeActivity";

	private void init() {

	}

	@OnClick(R.id.tv)
	public void onViewClicked() {
//		long t1 = System.nanoTime();//纳秒是10的-9次方秒
		AssetManager assetManager = getAssets();
		try {
			InputStream inputStream = assetManager.open("思修单选1.txt");
			String text = FileUtils.readStringFromInputStream2(inputStream);
//			long t2 = System.nanoTime();
//			LogUtils.d("时间：" + (t2 - t1));
			ProblemBean problemBean = GsonUtil.parseJsonToBean(text, ProblemBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
