package com.example.jjbeck.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jjbeck on 11/8/16.
 */

public class Movie {

    private static String EMPTY_OVERVIEW    = "N/A";
    private static String IMAGE_URL         = "https://image.tmdb.org/t/p/";
    private static String IMAGE_POSTER_SIZE = "w342";
    String posterPath;
    String originalTitle;
    String title;
    String overview;
    String movieId;

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath    = jsonObject.getString("poster_path");
        title         = jsonObject.getString("title");
        originalTitle = jsonObject.getString("original_title");
        overview      = jsonObject.getString("overview");
        movieId       = jsonObject.getString("id");
    }

    public String getPosterPath() {
        return String.format("%s/%s/%s", IMAGE_URL, IMAGE_POSTER_SIZE, posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() { return title; }

    public String getMovieId() { return movieId; }

    public String getOverview() {
        if (overview == null || overview.isEmpty()) {
            return EMPTY_OVERVIEW;
        }
        return overview;
    }

    // -----------------------------------------------------------------
    // Factory for creating a list of movies from the json results.
    // -----------------------------------------------------------------

    public static ArrayList<Movie> createMovieListfromJsonArray(JSONArray jsonArray)  {
        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());

        for (int movieCount = 0; movieCount < jsonArray.length() ; movieCount++) {
            try {
                Movie movie = new Movie(jsonArray.getJSONObject(movieCount));
                movies.add(movie);
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return movies;
    }
}
