package com.example.androidapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontRequest;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MovieAdapter adapter;
    private ArrayList<Movies> movieArticles = new ArrayList<>();
    private int pageNo = 1;
    private MovieViewModel movieViewModel;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar ab = getActionBar();
        if (ab!=null)
            ab.setTitle("He;;p");

        sharedPref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycler.setHasFixedSize(true);
        adapter = new MovieAdapter(movieArticles, movies -> {
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra("movie_id", movies.getId());
            startActivity(intent);
        });
        binding.recycler.setAdapter(adapter);
        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                assert layoutManager != null;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        /*&& totalItemCount >= PAGE_SIZE*/) {
                    int currentPageNo = sharedPref.getInt("PageNo", 0);

                    Log.d("TAG", "\n:visibleItemCount "+visibleItemCount+" firstVisibleItemPosition "
                            +firstVisibleItemPosition+" totalItemCount:" +totalItemCount
                            +"\n currentPageNo:"+currentPageNo);

                    if (currentPageNo!=0)
                        callApi(currentPageNo+1);

                }


            }
        });

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();

        /*movieViewModel.getMovieRepository().observe(this, newsResponse -> {
            if (newsResponse != null) {
                movieArticles = newsResponse.getResults();
                editor.putInt("PageNo", newsResponse.getPage());
                editor.apply();
                adapter.setMovies(movieArticles);
                adapter.notifyDataSetChanged();
            }
        });*/

        callApi(pageNo);
    }

    private void callApi(int pageNo) {
        movieViewModel.callAPI(pageNo).observe(this, newsResponse -> {
            if (newsResponse != null) {
                movieArticles = new ArrayList<>();
                movieArticles = newsResponse.getResults();
                editor.putInt("PageNo", newsResponse.getPage());
                editor.apply();
                Log.d("TAG", "Success-"+newsResponse.getPage()+ " Size: "+movieArticles.size());
                adapter.setMovies(movieArticles);
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("TAG", "landscape");
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            binding.recycler.setLayoutManager(new GridLayoutManager(this, 3));
            adapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("TAG", "portrait");
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "This is a saved message");
        outState.putInt("counter", new Random().nextInt(10));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
        int counter = savedInstanceState.getInt("counter", 0);
        Log.d("TAG", "counter: " + counter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.power_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.power) {
            editor.clear();
            editor.apply();
        }
        return super.onOptionsItemSelected(item);

    }
}
