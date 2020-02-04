package com.example.topicsdisplayapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hits")
public class Hits implements Parcelable {

    @NonNull
    @PrimaryKey
    private String objectID;

    @ColumnInfo(name = "created_at")
    private String created_at;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "isSelected")
    private boolean isSelected = false;

    @NonNull
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(@NonNull String objectID) {
        this.objectID = objectID;
    }

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
        dest.writeString(this.objectID);
        dest.writeString(this.created_at);
        dest.writeString(this.title);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public Hits() {
    }

    protected Hits(Parcel in) {
        this.objectID = in.readString();
        this.created_at = in.readString();
        this.title = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<Hits> CREATOR = new Creator<Hits>() {
        @Override
        public Hits createFromParcel(Parcel source) {
            return new Hits(source);
        }

        @Override
        public Hits[] newArray(int size) {
            return new Hits[size];
        }
    };
}
