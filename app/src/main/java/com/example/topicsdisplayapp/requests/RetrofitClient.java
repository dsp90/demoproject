package com.example.topicsdisplayapp.requests;

import com.example.topicsdisplayapp.utils.Constants;
import com.example.topicsdisplayapp.utils.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static GetStoriesApi storiesapi = retrofit.create(GetStoriesApi.class);

    public static GetStoriesApi getStoriesapi() {
        return storiesapi;
    }
}
