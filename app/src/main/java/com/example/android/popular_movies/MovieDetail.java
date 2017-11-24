package com.example.android.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MovieDetail extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail);
        Intent i = getIntent();
        Movie_Data currentMovie=(Movie_Data)i.getSerializableExtra("MovieData");
        TextView title=(TextView)findViewById(R.id.mov_tit);
        title.setText(currentMovie.getTitle());
        ImageView pos_det_view=(ImageView)findViewById(R.id.poster_det);
        Picasso.with(this).load(currentMovie.getMuvie_pos_path()).into(pos_det_view);
        TextView year=(TextView)findViewById(R.id.Year);
        year.setText(currentMovie.getRlse_date());
        TextView rating=(TextView)findViewById(R.id.rating);
        rating.setText(currentMovie.avg_vote);
        TextView overview=(TextView)findViewById(R.id.overview_film);
        overview.setText(currentMovie.getSynop_plot());
    }
}
