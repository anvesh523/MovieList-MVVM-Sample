package com.example.androidapp;

import android.app.Application;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieViewModel extends AndroidViewModel {

    private MutableLiveData<MovieResult> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Movies> movieLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieCast>> movieCastLiveData = new MutableLiveData<>();
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
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                Log.d("TAG", "Result: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Error: " + t.getMessage());
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
                Log.d("TAG", "Result: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    movieLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Error: " + t.getMessage());
                movieLiveData.setValue(null);
            }
        });

        return movieLiveData;
    }

    LiveData<List<MovieCast>> callMovieCastDetails(int id) {
        ApiInterface service = RetrofitApiInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<MovieResult> call = service.getMovieCredits(id, BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                Log.d("TAG", "Result: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieCastLiveData.setValue(response.body().getCasts());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Error: " + t.getMessage());
                movieCastLiveData.setValue(null);
            }
        });

        return movieCastLiveData;
    }

    @BindingAdapter("setRating")
    public static void postMovieRating(RatingBar ratingBarView, int id) {
        ratingBarView.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            JsonObject object = new JsonObject();
            object.addProperty("value", (rating * 2));
            ApiInterface service = RetrofitApiInstance.getRetrofitInstance().create(ApiInterface.class);
            Call<JsonObject> call = service.postMovieRating(id, BuildConfig.API_KEY, object);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    Toast.makeText(ratingBarView.getContext(), "Thanks for rating the movie..", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "Result: " + new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.d("TAG", "Error: " + t.getMessage());
                }
            });
        });
    }

}
