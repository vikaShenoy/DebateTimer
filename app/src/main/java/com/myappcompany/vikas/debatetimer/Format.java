package com.myappcompany.vikas.debatetimer;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Format implements Parcelable {
    private String name;
    private int numSpeakers;
    private int speakingTime;
    private boolean hasLR;
    private ArrayList<String> speakerNames;

    public Format(String fName, int fNum, int fTime, boolean fHasLR) {
        name = fName;
        numSpeakers = fNum;
        speakingTime = fTime;
        hasLR = fHasLR;
        speakerNames = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public int getSpeakingTime() {
        return speakingTime;
    }

    public int getNumSpeakers() {
        return numSpeakers;
    }

    public ArrayList<String> getSpeakerNames() {
        return speakerNames;
    }

    public void setSpeakerNames(ArrayList<String> speakers) {
        speakerNames = speakers;

    }

    public boolean getHasLR() {
        return hasLR;
    }



    protected Format(Parcel in) {
            name = in.readString();
            numSpeakers = in.readInt();
            speakingTime = in.readInt();
            hasLR = in.readByte() != 0x00;
            if (in.readByte() == 0x01) {
                speakerNames = new ArrayList<String>();
                in.readList(speakerNames, String.class.getClassLoader());
            } else {
                speakerNames = null;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(numSpeakers);
            dest.writeInt(speakingTime);
            dest.writeByte((byte) (hasLR ? 0x01 : 0x00));
            if (speakerNames == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(speakerNames);
            }
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Format> CREATOR = new Parcelable.Creator<Format>() {
            @Override
            public Format createFromParcel(Parcel in) {
                return new Format(in);
            }

            @Override
            public Format[] newArray(int size) {
                return new Format[size];
            }
        };
    }