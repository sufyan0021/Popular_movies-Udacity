package com.example.android.popular_movies;

import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks <List<Movie_Data>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private MovieAdapter mAdapter;
    private static final int Movie_LOADER_ID = 1;
    private static final String PMovies_REQUEST_URL ="https://api.themoviedb.org/3/movie/popular?api_key=7e4a997bc25a4d1dfff18001c3730f60&language=en-US";
    ArrayList<Movie_Data> movie_List=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(Movie_LOADER_ID,null,MainActivity.this);

        // Lookup the recyclerview in activity layout
        RecyclerView rvBooks = (RecyclerView) findViewById(R.id.rvMovies);

        mAdapter=new MovieAdapter(MainActivity.this,movie_List);

        rvBooks.setAdapter(mAdapter);

        rvBooks.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

    }

    @Override
    public Loader<List<Movie_Data>> onCreateLoader(int id, Bundle args) {
        return new Movie_Det_Loader(this,PMovies_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie_Data>> loader, List<Movie_Data> data) {
        // Clear the adapter of previous earthquake data
         movie_List.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            Log.v("Parsed","results");
            movie_List.addAll(data);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie_Data>> loader) {

    }
}
