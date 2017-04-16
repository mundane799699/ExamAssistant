package com.mundane.examassistant.global;

import android.app.Application;

import android.content.Context;
import com.facebook.stetho.Stetho;

/**
 * @author : mundane
 * @time : 2017/4/14 11:01
 * @description :
 * @file : ExamAssistantApplication.java
 */


public class ExamAssistantApplication extends Application {

    private static Context sContext;
	@Override
	public void onCreate() {
		super.onCreate();
        sContext = getApplicationContext();
        //		chrome://inspect/#devices
		Stetho.initializeWithDefaults(this);
	}


    public static Context getContext() {
        return sContext;
    }

}
