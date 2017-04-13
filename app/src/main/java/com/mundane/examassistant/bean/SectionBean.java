package com.mundane.examassistant.bean;

/**
 * @author : mundane
 * @time : 2017/4/13 11:17
 * @description :
 * @file : SectionBean.java
 */

public class SectionBean {
	public String chooseItem;
	public String questionNum;
	public int type;

	public SectionBean(String chooseItem, String questionNum) {
		this.chooseItem = chooseItem;
		this.questionNum = questionNum;
		String lastChar = chooseItem.substring(chooseItem.length() - 1, chooseItem.length());
		switch (lastChar) {
			case "一":
				type = 1;
				break;
			case "二":
				type = 2;
				break;
			case "三":
				type = 3;
				break;
			case "四":
				type = 4;
				break;
			case "五":
				type = 5;
				break;
			default:
				break;
		}
	}
}
