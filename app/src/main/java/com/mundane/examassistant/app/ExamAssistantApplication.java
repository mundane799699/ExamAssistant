package com.mundane.examassistant.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author : mundane
 * @time : 2017/4/14 11:01
 * @description :
 * @file : ExamAssistantApplication.java
 */


public class ExamAssistantApplication extends Application {

	private static Application sApplication;
	@Override
	public void onCreate() {
		super.onCreate();
		sApplication = this;
		//		chrome://inspect/#devices
		Stetho.initializeWithDefaults(this);
	}

	public static Application getApplication() {
		return sApplication;
	}
}
