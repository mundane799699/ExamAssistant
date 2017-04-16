package com.mundane.examassistant.db;

import android.content.Context;
import com.mundane.examassistant.db.entity.DaoMaster;
import com.mundane.examassistant.db.entity.DaoSession;
import com.mundane.examassistant.db.entity.JinDaiShiQuestionDao;
import com.mundane.examassistant.db.entity.MaKeSiQuestionDao;
import com.mundane.examassistant.db.entity.MaoGaiXiaQuestionDao;
import com.mundane.examassistant.db.entity.SiXiuQuestionDao;
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
		DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
		mDaoSession = daoMaster.newSession();
	}

	public SiXiuQuestionDao getSiXiuQuestionDao() {
		return mDaoSession.getSiXiuQuestionDao();
	}

    public MaKeSiQuestionDao getMaKeSiQuestionDao() {
        return mDaoSession.getMaKeSiQuestionDao();
    }

    public MaoGaiXiaQuestionDao getMaoGaiXiaQuestionDao() {
        return mDaoSession.getMaoGaiXiaQuestionDao();
    }


    public JinDaiShiQuestionDao getJinDaiShiQuestionDao() {
        return mDaoSession.getJinDaiShiQuestionDao();
    }



	public static DbManager getInstance() {
		return SingletonHolder.INSTANCE;
	}




}
