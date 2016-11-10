package com.example.jjbeck.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jjbeck on 11/8/16.
 */

public class Movie {

    private static String EMPTY_OVERVIEW = "N/A";
    String posterPath;
    String originalTitle;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath    = jsonObject.getString("poster_path");
        originalTitle = jsonObject.getString("original_title");
        overview      = jsonObject.getString("overview");
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

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
