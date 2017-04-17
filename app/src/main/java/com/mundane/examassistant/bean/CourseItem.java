package com.mundane.examassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author : mundane
 * @time : 2017/4/11 14:28
 * @description :
 * @file : CourseItem.java
 */

public class CourseItem implements Parcelable {
	public String name;//	课程名字, 比如近代史, 思修, 马克思, 毛概下
	public boolean isSelected;

	public CourseItem(String name, boolean isSelected) {
		this.name = name;
		this.isSelected = isSelected;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
	}

	protected CourseItem(Parcel in) {
		this.name = in.readString();
		this.isSelected = in.readByte() != 0;
	}

	public static final Parcelable.Creator<CourseItem> CREATOR = new Parcelable.Creator<CourseItem>() {
		@Override
		public CourseItem createFromParcel(Parcel source) {
			return new CourseItem(source);
		}

		@Override
		public CourseItem[] newArray(int size) {
			return new CourseItem[size];
		}
	};
}
