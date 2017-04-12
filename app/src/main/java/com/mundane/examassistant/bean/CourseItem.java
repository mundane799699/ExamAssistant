package com.mundane.examassistant.bean;

/**
 * @author : mundane
 * @time : 2017/4/11 14:28
 * @description :
 * @file : CourseItem.java
 */

public class CourseItem {
	public String name;
	public boolean isSelected;

	public CourseItem(String name, boolean isSelected) {
		this.name = name;
		this.isSelected = isSelected;
	}
}
