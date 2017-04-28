package com.mundane.examassistant.db;

import android.content.Context;

import com.mundane.examassistant.db.entity.DaoMaster;
import com.mundane.examassistant.db.entity.QuestionDao;
import com.mundane.examassistant.global.CommonUtils;

/**
 * @author : mundane
 * @time : 2017/4/14 13:12
 * @description :
 * @file : DbManager.java
 */

public class DbManager {

	private static Context context;
	private static final String DB_NAME = "examassistant.db";
	private QuestionDao mQuestionDao;

	private static class SingletonHolder {
		private static final DbManager INSTANCE = new DbManager();
	}

	//	mDao.insert(item);
	//	userDao = daoSession.getUserDao()
	//	daoSession = daoMaster.newSession()
	//	daoMaster = new DaoMaster(helper.getWritableDb());
	//	helper = new MyOpenHelper(mContext, DB_NAME);
	private DbManager() {
		context = CommonUtils.getContext();
	}

	public QuestionDao getQuestionDao() {
		return new DaoMaster(new MyOpenHelper(context, DB_NAME).getWritableDb()).newSession().getQuestionDao();
	}

	public static DbManager getInstance() {
		return SingletonHolder.INSTANCE;
	}


}
