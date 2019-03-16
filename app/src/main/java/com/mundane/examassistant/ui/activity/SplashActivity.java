package com.mundane.examassistant.ui.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.utils.FileUtils;
import com.mundane.examassistant.utils.ToastUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class SplashActivity extends BaseActivity {

	private final String DB_PATH = "/data/data/com.mundane.examassistant/databases/";
	private final String DB_NAME = "examassistant.db";
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dismissProgressDialog();
					ToastUtils.toast("数据导入完成");
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		checkData();
	}

	@Override
	protected void onDestroy() {
		if (mHandler != null) {
			mHandler.removeCallbacksAndMessages(null);
			mHandler = null;
		}
		super.onDestroy();
	}

	private void checkData() {

		QuestionDao questionDao = DbHelper.getQuestionDao();
		List<Question> list = questionDao.loadAll();
		if (list.isEmpty()) {
			showProgressDialog("正在导入数据中, 请稍后");
			new CopyFileThread().start();
		} else {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	private class CopyFileThread extends Thread {
		@Override
		public void run() {
			File dbFolder = new File(DB_PATH);
			if (!dbFolder.exists()) {
				dbFolder.mkdirs();
			}
			File dbFile = new File(dbFolder, DB_NAME);
			if (dbFile.exists()) {
				dbFile.delete();
			}
			AssetManager assetManager = getAssets();
			try {
				InputStream is = assetManager.open(DB_NAME);
				FileUtils.copy(is, dbFile);
				mHandler.sendEmptyMessage(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
