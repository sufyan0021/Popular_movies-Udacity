package com.example.android.popular_movies;

import android.content.Intent;
import android.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Movie_Data>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private MovieAdapter mAdapter;
    private static final int Movie_LOADER_ID = 1;
    private static final String PMovies_REQUEST_URL = "https://api.themoviedb.org/3/movie/popular?api_key=7e4a997bc25a4d1dfff18001c3730f60&language=en-US&page=";
    ArrayList<Movie_Data> movie_List = new ArrayList<>();
    RecyclerView rvMovie;
    GridLayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
    int currentPge = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(Movie_LOADER_ID, null, MainActivity.this);

        // Lookup the recyclerview in activity layout
        rvMovie = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovie.setLayoutManager(mLayoutManager);
        rvMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                 if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            currentPge++;
                            getLoaderManager().restartLoader(Movie_LOADER_ID, null, MainActivity.this);
                        }
                    }
                }
            }
        });

    }

    @Override
    public Loader<List<Movie_Data>> onCreateLoader(int id, Bundle args) {
        loading = true;
        return new Movie_Det_Loader(getApplicationContext(), PMovies_REQUEST_URL + currentPge);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie_Data>> loader, List<Movie_Data> data) {
        // Clear the adapter of previous earthquake data
        // movie_List.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            Log.v("Parsed", "results");
            mAdapter = new MovieAdapter(MainActivity.this, data);

            rvMovie.setAdapter(mAdapter);
            movie_List.addAll(data);

            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie_Data>> loader) {

    }

}
