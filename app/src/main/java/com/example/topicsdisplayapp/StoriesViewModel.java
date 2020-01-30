package com.example.topicsdisplayapp;

import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoriesViewModel extends ViewModel {
    public StoriesViewModel(){
    }

    public void getHits(int page){
         Call<List<Hits>> call = (Call<List<Hits>>) TopicsDisplayApplication.getRetrofitInstance()
                .create(GetStrories.class)
                .getListHits("story",page);
         call.enqueue(new Callback<List<Hits>>() {
             @Override
             public void onResponse(Call<List<Hits>> call, Response<List<Hits>> response) {
                 if (response.isSuccessful()){
                     List<Hits> hits = response.body();

                 }
             }

             @Override
             public void onFailure(Call<List<Hits>> call, Throwable t) {
             }
         });
    }
}
