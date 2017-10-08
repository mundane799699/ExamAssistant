package com.mundane.examassistant.utils;

import com.mundane.examassistant.bean.SectionBean;

import com.mundane.examassistant.bean.TimeDelayBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : mundane
 * @time : 2017/4/17 14:01
 * @description :
 * @file : ResUtil.java
 */

public class ResUtil {
	public static void importData(List<SectionBean> list, String courseName) {
		switch (courseName) {
			case "近代史":
				list.add(new SectionBean(courseName, "单选一", 100));
				list.add(new SectionBean(courseName, "单选二", 100));
				list.add(new SectionBean(courseName, "单选三", 100));
				list.add(new SectionBean(courseName, "单选四", 104));
				list.add(new SectionBean(courseName, "单选五", 46));
				list.add(new SectionBean(courseName, "多选一", 60));
				list.add(new SectionBean(courseName, "多选二", 60));
				list.add(new SectionBean(courseName, "多选三", 58));
				break;
			case "思修":
				list.add(new SectionBean(courseName, "单选一", 120));
				list.add(new SectionBean(courseName, "多选一", 80));
				break;
			case "马克思":
				list.add(new SectionBean(courseName, "单选一", 37));
				list.add(new SectionBean(courseName, "单选二", 75));
				list.add(new SectionBean(courseName, "单选三", 74));
				list.add(new SectionBean(courseName, "单选四", 74));
				list.add(new SectionBean(courseName, "单选五", 77));
				list.add(new SectionBean(courseName, "单选六", 66));
				list.add(new SectionBean(courseName, "单选七", 75));
				list.add(new SectionBean(courseName, "多选一", 13));
				list.add(new SectionBean(courseName, "多选二", 23));
				list.add(new SectionBean(courseName, "多选三", 30));
				list.add(new SectionBean(courseName, "多选四", 30));
				list.add(new SectionBean(courseName, "多选五", 30));
				list.add(new SectionBean(courseName, "多选六", 30));
				list.add(new SectionBean(courseName, "多选七", 30));
				break;
			case "毛概下":
				list.add(new SectionBean(courseName, "单选一", 15));
				list.add(new SectionBean(courseName, "单选二", 63));
				list.add(new SectionBean(courseName, "单选三", 243));
				list.add(new SectionBean(courseName, "单选四", 69));
				list.add(new SectionBean(courseName, "单选五", 59));
				list.add(new SectionBean(courseName, "多选一", 15));
				list.add(new SectionBean(courseName, "多选二", 27));
				list.add(new SectionBean(courseName, "多选三", 131));
				list.add(new SectionBean(courseName, "多选四", 33));
				list.add(new SectionBean(courseName, "多选五", 31));
				break;
			default:
				break;
		}
	}

	public static List<SectionBean> initData(String courseName) {
        List<SectionBean> list = new ArrayList<>();
        list.add(new SectionBean(courseName, "单选一", 0));
		list.add(new SectionBean(courseName, "单选二", 0));
		list.add(new SectionBean(courseName, "单选三", 0));
		list.add(new SectionBean(courseName, "单选四", 0));
		list.add(new SectionBean(courseName, "单选五", 0));
		list.add(new SectionBean(courseName, "单选六", 0));
		list.add(new SectionBean(courseName, "单选七", 0));
		list.add(new SectionBean(courseName, "多选一", 0));
		list.add(new SectionBean(courseName, "多选二", 0));
		list.add(new SectionBean(courseName, "多选三", 0));
		list.add(new SectionBean(courseName, "多选四", 0));
		list.add(new SectionBean(courseName, "多选五", 0));
		list.add(new SectionBean(courseName, "多选六", 0));
		list.add(new SectionBean(courseName, "多选七", 0));
        return list;
	}


	public static ArrayList<TimeDelayBean> getAnswerRightData() {
		ArrayList<TimeDelayBean> list = new ArrayList<>();
		list.add(new TimeDelayBean("不翻页", 0));
		list.add(new TimeDelayBean("0.25秒后", 250));
		list.add(new TimeDelayBean("0.5秒后", 500));
		list.add(new TimeDelayBean("0.75秒后", 750));
		list.add(new TimeDelayBean("1秒后", 1000));
		list.add(new TimeDelayBean("1.5秒后", 1500));
		list.add(new TimeDelayBean("2秒后", 2000));
		list.add(new TimeDelayBean("3秒后", 3000));
		return list;
	}


	public static ArrayList<TimeDelayBean> getAnswerRightRemoveData() {
		ArrayList<TimeDelayBean> list = new ArrayList<>();
		list.add(new TimeDelayBean("不移除", 0));
		list.add(new TimeDelayBean("1次", 1));
		list.add(new TimeDelayBean("2次", 2));
		list.add(new TimeDelayBean("3次", 3));
		list.add(new TimeDelayBean("4次", 4));
		list.add(new TimeDelayBean("5次", 5));
		list.add(new TimeDelayBean("6次", 6));
		return list;
	}


	public static ArrayList<TimeDelayBean> getFlipPageDate() {
		ArrayList<TimeDelayBean> list = new ArrayList<>();
		list.add(new TimeDelayBean("不翻页", 0));
		list.add(new TimeDelayBean("0.5秒后", 500));
		list.add(new TimeDelayBean("1秒后", 1000));
		list.add(new TimeDelayBean("1.5秒后", 1500));
		list.add(new TimeDelayBean("2秒后", 2000));
		list.add(new TimeDelayBean("2.5秒后", 2500));
		list.add(new TimeDelayBean("3秒后", 3000));
		list.add(new TimeDelayBean("4秒后", 4000));
		return list;
	}
}
