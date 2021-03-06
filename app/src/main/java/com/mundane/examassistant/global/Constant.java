package com.mundane.examassistant.global;

import com.mundane.examassistant.BuildConfig;

/**
 * @author : mundane
 * @time : 2017/4/11 19:41
 * @description :
 * @file : Constant.java
 */

public interface Constant {
	int TYPE_HEADER = 1;
	int TYPE_SECTION_PRACTICE = 2;
	int TYPE_PRACTICE_HISTORY = 3;
	int TYPE_EXAM = 4;
	int TYPE_MY_FAVORITATE = 5;
	int TYPE_MY_MISTAKE = 6;
	boolean isDebug = BuildConfig.DEBUG;
//	boolean isDebug = true;
	String LOG_TAG = "mundane";
	String KEY_CURRENT_COURSE = "current_course";

	String KEY_ALIPAY = "FKX01515RUUEZHPM79QY52";

	String KEY_ANSWER_RIGHT_AUTO_FLIP_PAGE_TIME = "key_answer_right_auto_flip_page_time";

	String KEY_PRACTICE_SELECT = "key_practice_select";

	String KEY_AUTO_FLIP_TIME = "key_auto_flip_time";

	String KEY_ANSWER_RIGHT_REMOVE_TIMES = "key_answer_right_remove_times";
}
