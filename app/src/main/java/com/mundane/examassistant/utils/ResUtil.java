package com.mundane.examassistant.utils;

import com.mundane.examassistant.bean.SectionBean;

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
}
