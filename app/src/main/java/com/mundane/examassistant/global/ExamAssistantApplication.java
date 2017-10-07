package com.mundane.examassistant.global;

import android.app.Application;
import android.content.Context;
import com.mundane.examassistant.BuildConfig;
import com.mundane.examassistant.utils.LogUtils;
import com.mundane.examassistant.utils.UMengUtil;
import com.umeng.analytics.MobclickAgent;

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
//		if (BuildConfig.DEBUG) {
//			Stetho.initializeWithDefaults(this);
//		}
		if (BuildConfig.DEBUG) {
			LogUtils.d("是debug模式");
			MobclickAgent.setDebugMode(true);
			String deviceInfo = UMengUtil.getDeviceInfo(this);
			LogUtils.d(deviceInfo);
		}
		MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
	}


    public static Context getContext() {
        return sContext;
    }

}
