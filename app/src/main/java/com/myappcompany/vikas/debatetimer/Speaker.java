package com.myappcompany.vikas.debatetimer;

import android.os.Parcel;
import android.os.Parcelable;

public class Speaker implements Parcelable {
    private String name;
    private int score;

    public Speaker(String sName, int sSscore) {
        name = sName;
        score = sSscore;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    protected Speaker(Parcel in) {
        name = in.readString();
        score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Speaker> CREATOR = new Parcelable.Creator<Speaker>() {
        @Override
        public Speaker createFromParcel(Parcel in) {
            return new Speaker(in);
        }

        @Override
        public Speaker[] newArray(int size) {
            return new Speaker[size];
        }
    };
}