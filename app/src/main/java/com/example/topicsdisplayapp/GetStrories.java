package com.example.topicsdisplayapp;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetStrories {
    @GET("search_by_date")
    List<Hits> getListHits(
        @Query("tags") String tag,
        @Query("page") Integer page
    );

}
