package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MovieRepository {

    private static MovieRepository newsRepository;
    private ApiInterface movieApi;

    static MovieRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new MovieRepository();
        }
        return newsRepository;
    }

    private MovieRepository() {
        movieApi = RetrofitApiInstance.getRetrofitInstance().create(ApiInterface.class);
    }

    MutableLiveData<MovieResult> getMovies() {
        MutableLiveData<MovieResult> newsData = new MutableLiveData<>();
        movieApi.getAllUpcomingMovies(BuildConfig.API_KEY).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

}
