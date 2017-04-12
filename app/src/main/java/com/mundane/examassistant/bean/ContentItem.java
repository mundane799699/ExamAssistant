package com.mundane.examassistant.bean;

import android.support.annotation.DrawableRes;

/**
 * @author : mundane
 * @time : 2017/4/11 19:44
 * @description :
 * @file : ContentItem.java
 */

public class ContentItem {
	public int type;
	public String name;
	@DrawableRes
	public int resId;

	public ContentItem(int type, String name, @DrawableRes int resId) {
		this.type = type;
		this.name = name;
		this.resId = resId;
	}
}
