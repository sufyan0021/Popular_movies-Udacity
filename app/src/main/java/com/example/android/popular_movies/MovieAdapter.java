package com.example.android.popular_movies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

/**
 * Created by sufya on 27-10-2017.
 */

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Movie_Data> mMovies;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public MovieAdapter(Context context, List<Movie_Data> mvieposters) {
        mMovies = mvieposters;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView moviePosterView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            moviePosterView = (ImageView) itemView.findViewById(R.id.posters);
        }
    }
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.frontpage_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {

        // Get the data model based on position
        Movie_Data movie = mMovies.get(position);
        // Set item views based on your views and data model
        ImageView imgView=holder.moviePosterView;
        Log.v("Posterurl",movie.getMuvie_pos_path());
        Picasso.with(mContext).load(movie.getMuvie_pos_path()).into(imgView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


}
