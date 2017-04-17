package com.mundane.examassistant.db;


import com.mundane.examassistant.db.entity.QuestionDao;

/**
 * @author : mundane
 * @time : 2017/4/14 14:06
 * @description :
 * @file : DbHelper.java
 */

public class DbHelper {

//	public static SiXiuQuestionDao getSiXiuQuestionDao() {
//		return DbManager.getInstance().getSiXiuQuestionDao();
//	}
//
//
//    public static JinDaiShiQuestionDao getJinDaiShiQuestionDao() {
//        return DbManager.getInstance().getJinDaiShiQuestionDao();
//    }
//
//
//    public static MaKeSiQuestionDao getMaKeSiQuestionDao() {
//        return DbManager.getInstance().getMaKeSiQuestionDao();
//    }
//
//
//    public static MaoGaiXiaQuestionDao getMaoGaiXiaQuestionDao() {
//        return DbManager.getInstance().getMaoGaiXiaQuestionDao();
//    }

	public static QuestionDao getQuestionDao() {
		return DbManager.getInstance().getQuestionDao();
	}




}
