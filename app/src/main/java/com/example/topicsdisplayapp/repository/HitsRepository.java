package com.example.topicsdisplayapp.repository;

import android.content.Context;

import com.example.topicsdisplayapp.models.Hits;
import com.example.topicsdisplayapp.persistance.HitsDao;
import com.example.topicsdisplayapp.persistance.HitsDatabase;
import com.example.topicsdisplayapp.requests.ApiResponse;
import com.example.topicsdisplayapp.requests.RetrofitClient;
import com.example.topicsdisplayapp.requests.response.HitsResponse;
import com.example.topicsdisplayapp.utils.AppExecutors;
import com.example.topicsdisplayapp.utils.Constants;
import com.example.topicsdisplayapp.utils.NetworkBoundResource;
import com.example.topicsdisplayapp.utils.Resource;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class HitsRepository {

    HitsDao hitsDao;
    private static HitsRepository instance;

    private HitsRepository(Context context) {
        hitsDao = HitsDatabase.getInstance(context).getHitsDao();
    }

    public static HitsRepository getInstance(Context context) {
        if (instance == null) {
            instance = new HitsRepository(context);
        }
        return instance;
    }

    public LiveData<Resource<List<Hits>>> getHits(final int page) {
        return new NetworkBoundResource<List<Hits>, HitsResponse>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(HitsResponse item) {
                if (item.getHits() != null) {
                    Hits[] hits = new Hits[item.getHits().size()];

                    int index = 0;
                    for (long rowId : hitsDao.insertHits(item.getHits().toArray(hits))) {
                        if (rowId == -1) {
                            hitsDao.update(
                                    hits[index].getObjectID(),
                                    hits[index].getTitle(),
                                    hits[index].getCreated_at(),
                                    hits[index].getIsSelected()
                            );
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Hits> data) {
                return true;
            }

            @Override
            protected LiveData<List<Hits>> loadFromDb() {
                return hitsDao.getList(page);
            }

            @Override
            protected LiveData<ApiResponse<HitsResponse>> createCall() {
                return RetrofitClient.getStoriesapi()
                        .getListHits(
                                Constants.DEFAULT_TAG,
                                String.valueOf(page)
                        );
            }
        }.getAsLiveData();
    }

//    public long getCount(boolean ofSelected) {
//        AppExecutors.getInstance().getmDiskIO().execute(
//                () -> hitsDao.getNumberOfStories(ofSelected)
//        );
//    }

    public void update(Hits hits) {
        AppExecutors.getInstance().getmDiskIO().execute(() ->
                hitsDao.update(hits.getObjectID(), hits.getTitle(), hits.getCreated_at(),
                hits.getIsSelected()));
    }

}
