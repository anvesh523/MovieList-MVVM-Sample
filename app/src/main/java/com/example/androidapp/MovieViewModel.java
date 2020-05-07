package com.example.androidapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieViewModel extends AndroidViewModel {

    private MutableLiveData<MovieResult> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Movies> movieLiveData = new MutableLiveData<>();
    private MovieRepository moviesRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    void init() {
        if (mutableLiveData != null) {
            return;
        }
        moviesRepository = MovieRepository.getInstance();
        mutableLiveData = moviesRepository.getMovies();
    }

    LiveData<MovieResult> getMovieRepository() {
        return mutableLiveData;
    }

    LiveData<MovieResult> callAPI(int page) {
     // Create handle for the RetrofitInstance interface
        ApiInterface service = RetrofitApiInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<MovieResult> call = service.getAllMovies(BuildConfig.API_KEY, page);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,@NonNull Response<MovieResult> response) {
                Log.d("TAG", "Result: "+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call,@NonNull Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Error: "+t.getMessage());
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    LiveData<Movies> callMovieDetails(int id) {
        ApiInterface service = RetrofitApiInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<Movies> call = service.getSelectedMovie(id, BuildConfig.API_KEY);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                Log.d("TAG", "Result: "+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    movieLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Error: "+t.getMessage());
                movieLiveData.setValue(null);
            }
        });

        return movieLiveData;
    }

}