package com.example.jjbeck.flixster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjbeck.flixster.R;
import com.example.jjbeck.flixster.models.Movie;

import java.util.ArrayList;

/**
 * Created by jjbeck on 11/8/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public MovieArrayAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data for the movie at 'position' (remember index)
        Movie movie = getItem(position);

        // check to see if existing view is getting reused.
        // http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#row-view-recycling
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        // find the image view
        ImageView lvImage = (ImageView) convertView.findViewById(R.id.idMovieImage);

        // Clear out data from possible reused view (Why not above?)
        lvImage.setImageResource(0);

        // Setup the new data now.
        // TODO: fix the naming of the overviewEditText to more standard like tvOverview!
        TextView tvTitle    = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.overviewEditText);

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
