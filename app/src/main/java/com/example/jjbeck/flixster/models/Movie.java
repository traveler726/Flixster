package com.example.jjbeck.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jjbeck on 11/8/16.
 *
 * Example of a movie summary in now_playing output:
 *      {   
 *          "adult": false, 
 *          "backdrop_path": "/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg", 
 *          "genre_ids": [ 28, 12, 14, 878 ],  
 *          "id": 284052, 
 *          "original_language": "en", 
 *          "original_title": "Doctor Strange", 
 *          "overview": "After his career is destroyed, a brilliant ...", 
 *          "popularity": 52.834047, 
 *          "poster_path": "/xfWac8MTYDxujaxgPVcRD9yZaul.jpg", 
 *          "release_date": "2016-10-25", 
 *          "title": "Doctor Strange", 
 *          "video": false, 
 *          "vote_average": 6.97, 
 *          "vote_count": 688 
 *      },
 * 
 * Image URL: 
 *  Poster path/size:
 *    template: https://image.tmdb.org/t/p/w342/[movie_id]
 *    example:  https://image.tmdb.org/t/p/w342/xfWac8MTYDxujaxgPVcRD9yZaul.jpg
 *  backdrop path/size:
 *    template: https://image.tmdb.org/t/p/w342/[movie_id]
 *    example:  https://image.tmdb.org/t/p/w342/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg
 *
 */

public class Movie implements Serializable {

    private static final long serialVersionUID    = 1234567890L;
    private static String EMPTY_OVERVIEW          = "N/A";
    private static String IMAGE_URL               = "https://image.tmdb.org/t/p/";
    private static String IMAGE_POSTER_SIZE       = "w342";
    private static double POPULAR_RATING_TO_COUNT = 50.0;
    private static double STAR_RATING_TO_COUNT    = 5.0;
    private static int    VOTES_NEEDED_TO_COUNT   = 5;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String title;
    private String overview;
    private String releaseDate;
    private String movieId;
    private double popularity;
    private double stars;
    private int    votes;

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath  = jsonObject.getString("backdrop_path");
        posterPath    = jsonObject.getString("poster_path");
        title         = jsonObject.getString("title");
        originalTitle = jsonObject.getString("original_title");
        overview      = jsonObject.getString("overview");
        releaseDate   = jsonObject.getString("release_date");
        movieId       = jsonObject.getString("id");
        popularity    = Double.valueOf(jsonObject.getString("popularity"));
        stars         = jsonObject.getDouble("vote_average");
        votes         = jsonObject.getInt("vote_count");
    }

    public String getBackdropPath() {
        return makePosterImageURL(backdropPath);
    }

    public String getPosterPath() {
        return makePosterImageURL(posterPath);
    }

    private String makePosterImageURL(String path) {
        return String.format("%s/%s/%s", IMAGE_URL, IMAGE_POSTER_SIZE, path);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() { return title; }

    public String getReleaseDate() { return releaseDate; }

    public String getMovieId() { return movieId; }

    public String getOverview() {
        if (overview == null || overview.isEmpty()) {
            return EMPTY_OVERVIEW;
        }
        return overview;
    }

    public double getPopularity() { return popularity; }
    public double getStars()      { return stars; }
    public int    getVotes()      { return votes; }

    public boolean isPopular () {
        return  (getVotes()      >= VOTES_NEEDED_TO_COUNT)  &&
                (getPopularity() >= POPULAR_RATING_TO_COUNT) &&
                (getStars()      >= STAR_RATING_TO_COUNT);
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
