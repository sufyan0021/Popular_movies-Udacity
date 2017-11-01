package com.example.android.popular_movies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by sufya on 30-10-2017.
 */

public class Movie_Det_Loader extends AsyncTaskLoader<List<Movie_Data>>{
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link Movie_Det_Loader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public Movie_Det_Loader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v("onStartLoading","Data Loading started");
    }

    /**
     * This is on a background thread.
     */

    @Override
    public List<Movie_Data> loadInBackground() {
        Log.v("loadInBackground","Data Request in background thread");

        if (mUrl == null) {
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        List<Movie_Data> movies = QueryUtils.fetchBooksData(mUrl);
        return movies;
    }

}
