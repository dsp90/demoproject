package com.example.topicsdisplayapp.requests;

import com.example.topicsdisplayapp.requests.response.HitsResponse;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    public ApiResponse<T> create(Throwable error){
        return new ApiErrorResponse<>(error.getMessage().equals("") ? error.getMessage() :
                "Something wrong occurred");
    }

    public ApiResponse<T> create(Response<T> response){
        T body = response.body();

        if (body instanceof HitsResponse){

            if (body == null || response.code() == 204){
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        } else {
            String errorMessage = "";
            try {
                errorMessage = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = response.message();
            }
            return new ApiErrorResponse<>(errorMessage);
        }
    }

     public class ApiSuccessResponse<T> extends ApiResponse<T>{

        private T data;

        public ApiSuccessResponse(T data){
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public class ApiErrorResponse<T> extends ApiResponse<T>{

        private String errorMessage;

        public ApiErrorResponse(String errorMessage){
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public class ApiEmptyResponse<T> extends ApiResponse<T>{}

}
