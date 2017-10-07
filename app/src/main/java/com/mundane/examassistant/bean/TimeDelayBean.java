package com.mundane.examassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mundane on 2017/10/5 下午4:24
 */

public class TimeDelayBean implements Parcelable {
	public String text;
	public long time;


	public TimeDelayBean(String text, long time) {
		this.text = text;
		this.time = time;
	}


	@Override
	public int describeContents() { return 0; }


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.text);
		dest.writeLong(this.time);
	}


	protected TimeDelayBean(Parcel in) {
		this.text = in.readString();
		this.time = in.readLong();
	}


	public static final Parcelable.Creator<TimeDelayBean> CREATOR = new Parcelable.Creator<TimeDelayBean>() {
		@Override
		public TimeDelayBean createFromParcel(Parcel source) {return new TimeDelayBean(source);}


		@Override
		public TimeDelayBean[] newArray(int size) {return new TimeDelayBean[size];}
	};
}
