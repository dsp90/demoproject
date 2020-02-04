package com.example.topicsdisplayapp.requests.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.topicsdisplayapp.models.Hits;

import java.util.List;

public class HitsResponse implements Parcelable {

    private List<Hits> hits;
    private String page;
    private String nbPages;

    public List<Hits> getHits() {
        return hits;
    }

    public void setHits(List<Hits> hits) {
        this.hits = hits;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNbPages() {
        return nbPages;
    }

    public void setNbPages(String nbPages) {
        this.nbPages = nbPages;
    }

    public HitsResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.hits);
        dest.writeString(this.page);
        dest.writeString(this.nbPages);
    }

    protected HitsResponse(Parcel in) {
        this.hits = in.createTypedArrayList(Hits.CREATOR);
        this.page = in.readString();
        this.nbPages = in.readString();
    }

    public static final Creator<HitsResponse> CREATOR = new Creator<HitsResponse>() {
        @Override
        public HitsResponse createFromParcel(Parcel source) {
            return new HitsResponse(source);
        }

        @Override
        public HitsResponse[] newArray(int size) {
            return new HitsResponse[size];
        }
    };
}
