package com.mundane.examassistant.db;

import android.content.Context;

import com.mundane.examassistant.app.ExamAssistantApplication;
import com.mundane.examassistant.db.entity.DaoMaster;
import com.mundane.examassistant.db.entity.DaoSession;
import com.mundane.examassistant.db.entity.QuestionDao;

/**
 * @author : mundane
 * @time : 2017/4/14 13:12
 * @description :
 * @file : DbManager.java
 */

public class DbManager {

	private static Context context;
	private static final String DB_NAME = "examassistant.db";
	private DaoSession mDaoSession;

	private static class SingletonHolder {
		private static final DbManager INSTANCE = new DbManager();
	}

	//mDao.insert(item);
	//userDao = daoSession.getUserDao()
	//daoSession = daoMaster.newSession()
	//daoMaster = new DaoMaster(helper.getWritableDb());
	//helper = new MyOpenHelper(mContext, DB_NAME);
	private DbManager() {
		context = ExamAssistantApplication.getApplication();
		MyOpenHelper openHelper = new MyOpenHelper(context, DB_NAME);
		DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
		mDaoSession = daoMaster.newSession();
	}

	public QuestionDao getQuestionDao() {
		return mDaoSession.getQuestionDao();
	}



	public static DbManager getInstance() {
		return SingletonHolder.INSTANCE;
	}




}
