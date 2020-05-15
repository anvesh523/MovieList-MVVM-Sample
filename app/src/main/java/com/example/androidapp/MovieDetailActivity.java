package com.example.androidapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.databinding.ActivityMovieDetailBinding;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding binding;
    private MovieViewModel movieViewModel;
    private Movies movies;
    private List<MovieCast> movieCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        int movieId = getIntent().getIntExtra("movie_id", 0);
        Log.d("TAG", "Movie Id: "+movieId);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();

        callApi(movieId);
        callApi2(movieId);

    }

    private void callApi(int id) {
        movieViewModel.callMovieDetails(id).observe(this, newsResponse -> {
            if (newsResponse != null) {
                movies = newsResponse;
                binding.setMovieDetails(movies);
            }
        });

    }

    private void callApi2(int id) {
        movieViewModel.callMovieCastDetails(id).observe(this, newsResponse -> {
            if (newsResponse != null) {
                movieCast = newsResponse;
                RecyclerView recyclerView = binding.movieDetailsInfo.recyclerViewCastMovieDetail;
                recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                MovieCastsAdapter mCastAdapter = new MovieCastsAdapter(MovieDetailActivity.this, movieCast);
                recyclerView.setAdapter(mCastAdapter);
            }
        });

    }

}
