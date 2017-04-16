package com.mundane.examassistant.db;


import com.mundane.examassistant.db.entity.JinDaiShiQuestionDao;
import com.mundane.examassistant.db.entity.MaKeSiQuestionDao;
import com.mundane.examassistant.db.entity.MaoGaiXiaQuestionDao;
import com.mundane.examassistant.db.entity.SiXiuQuestionDao;

/**
 * @author : mundane
 * @time : 2017/4/14 14:06
 * @description :
 * @file : DbHelper.java
 */

public class DbHelper {

	public static SiXiuQuestionDao getSiXiuQuestionDao() {
		return DbManager.getInstance().getSiXiuQuestionDao();
	}


    public static JinDaiShiQuestionDao getJinDaiShiQuestionDao() {
        return DbManager.getInstance().getJinDaiShiQuestionDao();
    }


    public static MaKeSiQuestionDao getMaKeSiQuestionDao() {
        return DbManager.getInstance().getMaKeSiQuestionDao();
    }


    public static MaoGaiXiaQuestionDao getMaoGaiXiaQuestion() {
        return DbManager.getInstance().getMaoGaiXiaQuestionDao();
    }




}
