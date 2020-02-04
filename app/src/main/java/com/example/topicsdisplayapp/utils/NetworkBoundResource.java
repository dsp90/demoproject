package com.example.topicsdisplayapp.utils;

import com.example.topicsdisplayapp.requests.ApiResponse;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResource <ResultType, RequestType>{

    private AppExecutors appExecutors;
    private MediatorLiveData<Resource<ResultType>> results = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        init();
    }

    private void init(){
        results.setValue(Resource.loading(null));

        final LiveData<ResultType> dbSource = loadFromDb();

        results.addSource(dbSource, resultType -> {
            results.removeSource(dbSource);

            if (shouldFetch(resultType)){
                fetchFromNetwork(dbSource);
            } else {
                results.addSource(dbSource, resultType1 -> setValue(Resource.success(resultType1)));
            }
        });
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        results.addSource(dbSource, resultType -> {
           setValue(Resource.loading(resultType));
        });

        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        results.addSource(apiResponse, requestTypeApiResponse -> {
            results.removeSource(dbSource);
            results.removeSource(apiResponse);

            if (requestTypeApiResponse instanceof ApiResponse.ApiSuccessResponse){
                appExecutors.getmDiskIO().execute(() -> {
                    saveCallResult((RequestType) processResponse((ApiResponse.ApiSuccessResponse) requestTypeApiResponse));
                });

                appExecutors.getmMainThredExecutor().execute(() -> results.addSource(loadFromDb(), resultType -> setValue(Resource.success(resultType))));

            } else if (requestTypeApiResponse instanceof ApiResponse.ApiEmptyResponse){
                appExecutors.getmMainThredExecutor().execute(() -> results.addSource(loadFromDb(), resultType -> setValue(Resource.success(resultType))));
            }else if (requestTypeApiResponse instanceof ApiResponse.ApiErrorResponse){
                results.addSource(dbSource, resultType -> setValue(
                        Resource.error(
                                ((ApiResponse.ApiErrorResponse) requestTypeApiResponse).getErrorMessage(),
                                resultType
                        )
                ));
            }
        });
    }

    private ResultType processResponse(ApiResponse.ApiSuccessResponse response){
        return (ResultType) response.getData();
    }

    private void setValue(Resource<ResultType> newValue) {
        if (results.getValue() != newValue){
            results.setValue(newValue);
        }
    }

    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    public final LiveData<Resource<ResultType>> getAsLiveData(){
        return results;
    }

}
