package com.example.topicsdisplayapp;

import android.app.Application;

import retrofit2.Retrofit;

public class TopicsDisplayApplication extends Application {
    private static Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        getRetrofitInstance();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null){
            return getRetrofitInstanceImpl();
        }
        return retrofit;
    }

    private static Retrofit getRetrofitInstanceImpl(){
        return new Retrofit.Builder()
                .baseUrl("https://hn.algolia.com/api/v1/")
                .build();
    }
}
