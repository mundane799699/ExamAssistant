package com.mundane.examassistant.bean;

import java.util.List;

/**
 * @author : mundane
 * @time : 2017/4/12 20:54
 * @description :
 * @file : ProblemBean.java
 */

public class QuestionBean {

	public PlistBean plist;

	public static class PlistBean {

		public ArrayBean array;

		public static class ArrayBean {
			public List<DictBean> dict;

			public static class DictBean {
				public List<String> key;
				public List<String> string;
			}
		}
	}
}
