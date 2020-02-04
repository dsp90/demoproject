package com.example.topicsdisplayapp.viewmodels;

import android.app.Application;

import com.example.topicsdisplayapp.models.Hits;
import com.example.topicsdisplayapp.repository.HitsRepository;
import com.example.topicsdisplayapp.utils.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class StoriesViewModel extends AndroidViewModel {

    public static final String QUERY_EXHAUSTED = "No more results";

    private MediatorLiveData<Resource<List<Hits>>> hits = new MediatorLiveData<>();
    private HitsRepository hitsRepository;

    public StoriesViewModel(@NonNull Application application) {
        super(application);
        hitsRepository = HitsRepository.getInstance(application);
    }

    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private int pageNumber;
    private boolean cancelRequest;

    public LiveData<Resource<List<Hits>>> getHits() {
        return hits;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void getStoryHits(int pageNumber) {
        if (!isPerformingQuery) {
            if (pageNumber == 0) {
                pageNumber = 1;
            }

            this.pageNumber = pageNumber;
            isQueryExhausted = false;
            getStoryHitsImpl();
        }
    }

    public void getNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            pageNumber++;
            getStoryHitsImpl();
        }
    }

    private void getStoryHitsImpl() {
        cancelRequest = false;

        final LiveData<Resource<List<Hits>>> hitsSource =
                hitsRepository.getHits(pageNumber);
        hits.addSource(hitsSource, listResource -> {
            if (!cancelRequest) {
                if (listResource != null) {
                    if (listResource.status == Resource.Status.SUCCESS) {
                        isPerformingQuery = false;
                        if (listResource.data != null) {
                            if (listResource.data.size() == 0) {
                                hits.setValue(
                                        new Resource<>(
                                                Resource.Status.ERROR,
                                                listResource.data,
                                                QUERY_EXHAUSTED
                                        )
                                );
                                isQueryExhausted = true;
                            }
                        }
                        hits.removeSource(hitsSource);
                    } else if (listResource.status == Resource.Status.ERROR) {
                        if (listResource.message.equals(QUERY_EXHAUSTED)) {
                            isQueryExhausted = true;
                        }
                        hits.removeSource(hitsSource);
                    }
                    hits.setValue(listResource);
                }
            } else {
                hits.removeSource(hitsSource);
            }
        });
    }

//    public long getSelectedStories(boolean isSelected){
//        return hitsRepository.getCount(isSelected);
//    }

    public void updateModel(Hits hits){
        hitsRepository.update(hits);
    }

    public void cancelRequest(){
        if (isPerformingQuery){
            cancelRequest = true;
            isPerformingQuery = false;
            pageNumber = 1;
        }
    }
}
