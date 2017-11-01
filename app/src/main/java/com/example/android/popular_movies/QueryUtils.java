package com.example.android.popular_movies;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.popular_movies.MainActivity.LOG_TAG;

/**
 * Created by sufya on 30-10-2017.
 */

public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private static String tle,rel_date,pos_path,average_votes,plot_synop;
    private QueryUtils() {
    }

    public static List<Movie_Data> fetchBooksData(String requestUrl) {

        Log.v("fetchMovieData","url received");

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Movie_Data>movie = extractMovies(jsonResponse);

        // Return the {@link Event}
        return movie;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {

        Log.v("createUrl","url created");

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {

        Log.v("makeHttpRequest","Http Request made");

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movies JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.v("readFromStream","input stream object data being read");

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movie_Data} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Movie_Data> extractMovies(String movielistJSON) {

        Log.v("extractMovies","JsonResponse extracted");

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movielistJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Movie_Data> movies = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Books objects with the corresponding data.
            JSONObject baseJsonResponse=new JSONObject(movielistJSON);
            JSONArray ab=baseJsonResponse.getJSONArray("results");

            for(int i=0;i<ab.length();i++)
            {
                JSONObject moviedet=ab.getJSONObject(i);
                 tle=moviedet.getString("title");
                 rel_date=moviedet.getString("release_date");
                 pos_path=moviedet.getString("poster_path");
                 average_votes=moviedet.getString("vote_average");
                 plot_synop=moviedet.getString("overview");
                Log.v("ArrayListResult",tle);
                Log.v("ArrayListResult",rel_date);
                Log.v("ArrayListResult",pos_path);
                Log.v("ArrayListResult",average_votes);
                Log.v("ArrayListResult",plot_synop);
                Movie_Data movie=new Movie_Data(tle,rel_date,pos_path,average_votes,plot_synop);
                Movie_Data movie_frontpg=new Movie_Data(pos_path);
                movies.add(movie_frontpg);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movies JSON results", e);
        }

        // Return the list of earthquakes
        return movies;
    }
}