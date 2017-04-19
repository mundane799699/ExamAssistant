package com.mundane.examassistant.db;

import android.content.Context;

import com.mundane.examassistant.db.entity.DaoMaster;
import com.mundane.examassistant.db.entity.DaoSession;
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
	private DaoSession mDaoSession;
	private final DaoMaster mDaoMaster;

	private static class SingletonHolder {
		private static final DbManager INSTANCE = new DbManager();
	}

	//mDao.insert(item);
	//userDao = daoSession.getUserDao()
	//daoSession = daoMaster.newSession()
	//daoMaster = new DaoMaster(helper.getWritableDb());
	//helper = new MyOpenHelper(mContext, DB_NAME);
	private DbManager() {
		context = CommonUtils.getContext();
		MyOpenHelper openHelper = new MyOpenHelper(context, DB_NAME);
		mDaoMaster = new DaoMaster(openHelper.getWritableDb());
//		mDaoSession = mDaoMaster.newSession();//	不能这么写, 每次查询session都要new出来, 否则还是上次的数据
	}

	public QuestionDao getQuestionDao() {
		return mDaoMaster.newSession().getQuestionDao();
	}

	public static DbManager getInstance() {
		return SingletonHolder.INSTANCE;
	}




}
