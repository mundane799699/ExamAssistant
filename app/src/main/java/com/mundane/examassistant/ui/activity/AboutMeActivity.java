package com.mundane.examassistant.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mundane.examassistant.R;
import com.mundane.examassistant.global.Constant;
import com.mundane.examassistant.utils.AlipayUtil;
import com.mundane.examassistant.utils.SnackbarUtil;
import com.mundane.examassistant.utils.StatusBarUtils;

public class AboutMeActivity extends AppCompatActivity {
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.tv_author)
	TextView mTvAuthor;
	@BindView(R.id.tv_introduce)
	TextView mTvIntroduce;
	@BindView(R.id.cv_about_award)
	CardView mCvAboutAward;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_me);
		ButterKnife.bind(this);
		StatusBarUtils.setTranslucentImageHeader(this, 0, mToolbar);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		mTvAuthor.setText(Html.fromHtml(getString(R.string.author)));
		mTvIntroduce.setText(Html.fromHtml(getString(R.string.introduce)));
		mTvIntroduce.setMovementMethod(LinkMovementMethod.getInstance());
		mCvAboutAward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AlipayUtil.hasInstalledAlipayClient(AboutMeActivity.this)) {
					AlipayUtil.startAlipayClient(AboutMeActivity.this, Constant.KEY_ALIPAY);
				} else {
					SnackbarUtil.showShort(getWindow().getDecorView(), "木有检测到支付宝客户端 T T");
				}
			}
		});
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
