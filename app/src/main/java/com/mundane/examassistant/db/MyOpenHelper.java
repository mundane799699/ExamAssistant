package com.mundane.examassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mundane.examassistant.db.entity.DaoMaster;


/**
 * @author : mundane
 * @time : 2017/4/14 11:43
 * @description :
 * @file : MyOpenHelper.java
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
	public MyOpenHelper(Context context, String name) {
		super(context, name);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
	}
}
