package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.example.androidapp.databinding.ActivityMovieDetailBinding;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding binding;
    private MovieViewModel movieViewModel;
    private Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        int movieId = getIntent().getIntExtra("movie_id", 0);
        Log.d("TAG", "Movie Id: "+movieId);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();

        callApi(movieId);

    }

    private void callApi(int id) {
        movieViewModel.callMovieDetails(id).observe(this, newsResponse -> {
            if (newsResponse != null) {
                movies = newsResponse;
                binding.setMovieDetails(movies);
            }
        });

    }

}
