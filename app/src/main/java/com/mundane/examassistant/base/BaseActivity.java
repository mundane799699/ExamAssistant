package com.mundane.examassistant.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author : mundane
 * @time : 2017/4/11 14:11
 * @description :
 * @file : BaseActivity.java
 */

public class BaseActivity extends AppCompatActivity {

	public ProgressDialog mProgressDialog;

	public void showProgressDialog(String msg) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
		}
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(msg);
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	public void dismissProgressDialog() {
		if (mProgressDialog == null) {
			return;
		}
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
