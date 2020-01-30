package com.example.topicsdisplayapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Hits implements Parcelable {
    private String created_at;

    private String title;

    private boolean isSelected = false;

    protected Hits(Parcel in) {
        created_at = in.readString();
        title = in.readString();
        isSelected = in.readByte()!=0;
    }

    public static final Creator<Hits> CREATOR = new Creator<Hits>() {
        @Override
        public Hits createFromParcel(Parcel in) {
            return new Hits(in);
        }

        @Override
        public Hits[] newArray(int size) {
            return new Hits[size];
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeString(title);
    }
}
