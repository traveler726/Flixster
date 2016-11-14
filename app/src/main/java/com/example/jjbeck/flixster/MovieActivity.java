package com.example.jjbeck.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jjbeck.flixster.adapters.MovieArrayAdapter;
import com.example.jjbeck.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.jjbeck.flixster.R.id.lvMovies;

public class MovieActivity extends AppCompatActivity {

    public  static String MOVIE_DETAIL_KEY = "movie_detail";
    private static String nowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    SwipeRefreshLayout swipeContainer;

    // ----------------------------------------------------
    // MainActivity entry point - think 'main' for app.
    // ----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();

        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_movie);

        setupActionBar();

        lvItems = (ListView) findViewById(lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        // Get the movies from the service and setup for the app.
        getNSetupMovies();

        // Setup the swipe for the main view
        setupSwipe();

        // Setup handling the click on the movie
        setupMovieSelectedListener();
    }

    private void setupActionBar() {

        // Original
        getSupportActionBar().show();  // can show() or hide();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        // NOTE: Do one of the other!

//        // Manually Setup the action bar
//        getSupportActionBar().show();  // can show() or hide();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.movie_reels_n_popcorn);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

//        // Setup action bar with layout resource.
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
//        getSupportActionBar().setLogo(R.mipmap.movie_reels_n_popcorn);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.actionbar_title);
    }

    // ----------------------------------------------------
    // Let's call the downstream service to get the movies.
    // ----------------------------------------------------
    private void getNSetupMovies() {

        // Setup the Http Client and get the data from the DB.
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(nowPlayingUrl, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Got the movie data - let's setup them up now.
                setupMoviesFromDB(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MovieActivity.this, "Unable to get movies at this time.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // ----------------------------------------------------
    // Now that I have the movies let's setup and update the app.
    // ----------------------------------------------------
    private void setupMoviesFromDB(JSONObject response) {
        // Make sure to clear out any old data
        // Overkill for 1st request - but not too much!
        movieAdapter.clear();
        try {
            JSONArray movieJsonResults = response.getJSONArray("results");
            movies.addAll(Movie.createMovieListfromJsonArray(movieJsonResults));
            movieAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupSwipe() {
        // Only ever call `setContentView` once right at the top
        // In this case it was already done in onCreate(...) above
        // setContentView(R.layout.activity_movie);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(MovieActivity.this, "Refreshing Data ...", Toast.LENGTH_SHORT).show();

                // Your code to refresh the list here.
                getNSetupMovies();

                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

                Toast.makeText(MovieActivity.this, "... data all up to date now!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setupMovieSelectedListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                Intent clickIntent = new Intent(MovieActivity.this, MovieDetailActivity.class);
                clickIntent.putExtra(MOVIE_DETAIL_KEY, movieAdapter.getItem(position));
                startActivity(clickIntent);
            }
        });
    }
}
