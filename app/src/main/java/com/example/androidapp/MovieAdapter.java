package com.example.androidapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.databinding.AdapterMovieBinding;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movies> movies;
    private MovieClickListener clickListener;

    MovieAdapter(ArrayList<Movies> movies, MovieClickListener clickListener) {
        this.movies = movies;
        // this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterMovieBinding movieListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.adapter_movie, parent, false);
        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieItemBinding.setMovie(movies.get(position));
        /*holder.movieItemBinding.setItemClickListener(new MovieClickListener() {
            @Override
            public void cardClicked(Movies movies) {
                clickListener.cardClicked(movies);
            }
        });*/
    }

    void setMovies(ArrayList<Movies> newMovies) {
        // movies = new ArrayList<>();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private AdapterMovieBinding movieItemBinding;

        MovieViewHolder(@NonNull AdapterMovieBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            this.movieItemBinding = movieListItemBinding;
        }
    }

}
