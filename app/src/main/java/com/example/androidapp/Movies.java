package com.example.androidapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;

    private float popularity;
    @SerializedName("vote_count")
    private int voteCount;
    private boolean video;
    private int id;
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("release_date")
    private String releaseDate;

    private ArrayList<IdNameModel> genres = null;
    private Integer budget;
    private String homepage;
    @SerializedName("imdb_id")
    private String imdbId;
    private Integer revenue;
    private Integer runtime;
    private String status;
    private String tagline;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<IdNameModel> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<IdNameModel> genres) {
        this.genres = genres;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    // important code for loading image here
    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String imageURL) {
        if (imageURL!=null)
            Glide.with(imageView.getContext())
                    /*.setDefaultRequestOptions(new RequestOptions()
                            .circleCrop())*/
                    .load(BuildConfig.IMAGE_URL + imageURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
    }

    @BindingAdapter("cardViewClick")
    public static void cardClickListener(CardView cardView, int id) {
        cardView.setOnClickListener(view -> {
                Context context = cardView.getContext();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie_id", id);
                context.startActivity(intent);
        });
    }

    @BindingAdapter("items")
    public static void loadGenres(ChipGroup chipGroup, List<IdNameModel> genresList) {
        if (genresList!=null && genresList.size()>0)
        for (IdNameModel genres : genresList) {

            Chip chip = new Chip(chipGroup.getContext());
            chip.setId(genres.getId());
            chip.setTag(genres.getId());
            chip.setText(genres.getName());
            chip.setCheckable(false);

            chipGroup.addView(chip);

        }
    }

    @BindingAdapter("webViewClick")
    public static void webClickListener(TextView textView, String url) {
        if (url!=null && !url.isEmpty())
        textView.setOnClickListener(view -> {
            Context context = textView.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });
    }


}
