package com.example.android.popular_movies;

import java.io.Serializable;

/**
 * Created by sufya on 30-10-2017.
 */

public class Movie_Data implements Serializable{

    public  String baseimgurl= "http://image.tmdb.org/t/p/",imgsize="w500/";
    public  String tle, rlse_date, muvie_pos_path, avg_vote, synop_plot;

    public Movie_Data(String movie_post_path) {
        this.muvie_pos_path = baseimgurl+imgsize+movie_post_path;
    }

    public Movie_Data(String title, String release_date, String movie_post_path, String vote_average, String plot_synopsis) {
        this.tle = title;
        this.rlse_date = release_date;
        this.muvie_pos_path = baseimgurl+imgsize+movie_post_path;
        this.avg_vote = vote_average;
        this.synop_plot = plot_synopsis;
    }

    public String getTitle(){return tle;}

    public  String getAvg_vote() {
        return avg_vote;
    }

    public String getRlse_date() {
        return rlse_date;
    }

    public String getMuvie_pos_path() {
        return muvie_pos_path;
    }

    public String getSynop_plot() {
        return synop_plot;
    }

}
