package com.mundane.examassistant.db;


import com.mundane.examassistant.db.entity.QuestionDao;

/**
 * @author : mundane
 * @time : 2017/4/14 14:06
 * @description :
 * @file : DbHelper.java
 */

public class DbHelper {

	public static QuestionDao getQuestionDao() {
		return DbManager.getInstance().getQuestionDao();
	}




}
