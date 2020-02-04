package com.example.topicsdisplayapp.requests;

import com.example.topicsdisplayapp.requests.response.HitsResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetStoriesApi {
    @GET("search_by_date")
    LiveData<ApiResponse<HitsResponse>> getListHits(
        @Query("tags") String tag,
        @Query("page") String page
    );

}
