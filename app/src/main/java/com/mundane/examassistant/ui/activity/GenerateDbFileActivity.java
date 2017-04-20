package com.mundane.examassistant.ui.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mundane.examassistant.R;
import com.mundane.examassistant.base.BaseActivity;
import com.mundane.examassistant.bean.QuestionBean;
import com.mundane.examassistant.db.DbHelper;
import com.mundane.examassistant.db.entity.Question;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.utils.FileUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GenerateDbFileActivity extends BaseActivity {

	private List<Question> mList;
	private QuestionDao mQuestionDao;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dismissProgressDialog();
					Toast.makeText(GenerateDbFileActivity.this, "数据导入完成", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	//
//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_db);

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
		mList = new ArrayList<>();
		mList.addAll(getConditionList());
		if (mList.isEmpty()) {        //	数据库中没有数据, 需要导入数据
			showProgressDialog("正在导入数据中, 请稍后");
			new DbThread().start();
		} else {                    //	数据库中已经有数据, 直接用从数据库中取出的数据

		}
	}

	private List<Question> getConditionList() {
		if (mQuestionDao == null) {
			mQuestionDao = DbHelper.getQuestionDao();
		}
		mQuestionDao = DbHelper.getQuestionDao();
		List<Question> list = mQuestionDao.loadAll();
		return list;
	}


	private class DbThread extends Thread {
		@Override
		public void run() {
			AssetManager assetManager = getAssets();
			try {
				String[] array = assetManager.list("resource");
				for (String fileName : array) {
					InputStream inputStream = assetManager.open("resource/" + fileName);
					String text = FileUtils.readStringFromInputStream(inputStream);
					QuestionBean questionBean = JSON.parseObject(text, QuestionBean.class);
					List<QuestionBean.PlistBean.ArrayBean.DictBean> dict = questionBean.plist.array.dict;
					for (QuestionBean.PlistBean.ArrayBean.DictBean dictBean : dict) {
						List<String> string = dictBean.string;
						int size = string.size();
						Question question = new Question();
						question.setCourse(getCourse(fileName));
						question.setType(getType(fileName));
						question.setQuestion(string.get(size - 1));
						question.setAnswer(string.get(size - 2));
						String answer = question.getAnswer();

						question.setOptionA(string.get(0));
						question.setIsOptionACorrect(answer.contains("A"));

						question.setOptionB(string.get(1));
						question.setIsOptionBCorrect(answer.contains("B"));
						if (size == 6) {        //	ABCD

							question.setOptionC(string.get(2));
							question.setIsOptionCCorrect(answer.contains("C"));

							question.setOptionD(string.get(3));
							question.setIsOptionDCorrect(answer.contains("D"));
						} else if (size == 4) {        //	AB

						} else {//	size == 7 ABCDE
							question.setOptionC(string.get(2));
							question.setIsOptionCCorrect(answer.contains("C"));

							question.setOptionD(string.get(3));
							question.setIsOptionDCorrect(answer.contains("D"));

							question.setOptionE(string.get(4));
							question.setIsOptionECorrect(answer.contains("E"));
						}

						try {
							//因为我在实体类的里给queston添加了唯一约束,
							// 重复添加相同的question时会报错, 所以这里要try...catch掉, 防止插入重复的question
							mQuestionDao.insert(question);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				mHandler.sendEmptyMessage(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@NonNull
	private String getType(String fileName) {
		return fileName.substring(fileName.length() - 7, fileName.length() - 4);
	}

	@NonNull
	private String getCourse(String fileName) {
		return fileName.substring(0, fileName.length() - 7);
	}
}
