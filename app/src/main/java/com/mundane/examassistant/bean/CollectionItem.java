package com.mundane.examassistant.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mundane on 2017/8/13.
 */

public class CollectionItem implements Parcelable {
    public String name;// 课程名字， 比如近代史, 思修, 马克思, 毛概下


    public CollectionItem(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }


    protected CollectionItem(Parcel in) {
        this.name = in.readString();
    }


    public static final Parcelable.Creator<CollectionItem> CREATOR = new Parcelable.Creator<CollectionItem>() {
        @Override
        public CollectionItem createFromParcel(Parcel source) {
            return new CollectionItem(source);
        }


        @Override
        public CollectionItem[] newArray(int size) {
            return new CollectionItem[size];
        }
    };
}
