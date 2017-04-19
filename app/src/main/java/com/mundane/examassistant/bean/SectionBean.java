package com.mundane.examassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author : mundane
 * @time : 2017/4/13 11:17
 * @description :
 * @file : SectionBean.java
 */

public class SectionBean implements Parcelable {
	public String courseName;//		近代史, 思修, 马克思, 毛概下
	public String questionType;//	单选一, 单选二, 多选一, 多选二...
	public int questionNum;
	public int type;
	public boolean isSelected;

	public SectionBean(String courseName, String questionType, int questionNum) {
		this.courseName = courseName;
		this.questionType = questionType;
		this.questionNum = questionNum;
		String lastChar = questionType.substring(questionType.length() - 1, questionType.length());
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
			case "六":
				type = 6;
				break;
			case "七":
				type = 7;
				break;
			default:
				break;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.courseName);
		dest.writeString(this.questionType);
		dest.writeInt(this.questionNum);
		dest.writeInt(this.type);
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
	}

	protected SectionBean(Parcel in) {
		this.courseName = in.readString();
		this.questionType = in.readString();
		this.questionNum = in.readInt();
		this.type = in.readInt();
		this.isSelected = in.readByte() != 0;
	}

	public static final Parcelable.Creator<SectionBean> CREATOR = new Parcelable.Creator<SectionBean>() {
		@Override
		public SectionBean createFromParcel(Parcel source) {
			return new SectionBean(source);
		}

		@Override
		public SectionBean[] newArray(int size) {
			return new SectionBean[size];
		}
	};
}
