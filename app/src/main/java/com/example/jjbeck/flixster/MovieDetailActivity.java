package com.example.jjbeck.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jjbeck.flixster.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView ivDetailImage;
    private TextView  tvTitle;
    private TextView  tvReleaseDate;
    private TextView  tvVoteScore;
    private TextView  tvVoteCount;
    private TextView  tvPopularity;
    private TextView  tvOverview;
    private RatingBar rbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Fetch views
        ivDetailImage  = (ImageView) findViewById(R.id.ivDetailImage);
        tvTitle        = (TextView)  findViewById(R.id.tvTitle);
        tvReleaseDate  = (TextView)  findViewById(R.id.tvReleaseDate);
        tvVoteScore    = (TextView)  findViewById(R.id.tvVoteScore);
        tvVoteCount    = (TextView)  findViewById(R.id.tvVoteCount);
        tvPopularity   = (TextView)  findViewById(R.id.tvPopularity);
        tvOverview     = (TextView)  findViewById(R.id.tvOverview);
        rbRating       = (RatingBar) findViewById(R.id.rbStars);
        
        // Use the movie to populate the data into our views
        Movie movie = (Movie) getIntent().getSerializableExtra(MovieActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    // Populate the data for the movie
    private void loadMovie(Movie movie) {

        // Populate data
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        tvReleaseDate.setText(Html.fromHtml("<b>Release Date:</b> " + movie.getReleaseDate()));
        tvVoteScore.setText  (Html.fromHtml("<b>Voted:</b> "        + (int) Math.round(movie.getStars())));
        tvVoteCount.setText  (Html.fromHtml("<b>Count:</b> "        + (int) Math.round(movie.getVotes())));
        tvPopularity.setText (Html.fromHtml("<b>Popularity:</b> "   + (int) Math.round(movie.getPopularity()) + "%"));

        rbRating.setRating((float) (0.5f * movie.getStars()));

        Picasso.with(this).load(movie.getBackdropPath()).
                placeholder(R.drawable.poster_placeholder_large).
                into(ivDetailImage);

    }
}
