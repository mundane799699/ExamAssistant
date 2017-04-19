package com.mundane.examassistant.bean;

/**
 * @author : mundane
 * @time : 2017/4/19 11:24
 * @description :
 * @file : OptionBean.java
 */

public class OptionBean {
	public String option;
	public boolean isAnswer;
	public boolean showOption;

	public OptionBean(String option, boolean isAnswer, boolean showOption) {
		this.option = option;
		this.isAnswer = isAnswer;
		this.showOption = showOption;
	}
}
