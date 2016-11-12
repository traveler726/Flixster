package com.example.jjbeck.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjbeck.flixster.R;
import com.example.jjbeck.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by jjbeck on 11/8/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    // View lookup cache

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
    }

    public MovieArrayAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String toastMsg = null;

        // Get the data for the movie at 'position' (remember index)
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        // check to see if existing view is getting reused.
        // http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#row-view-recycling
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            // then cache the view holder onto the view for later use!
            viewHolder = new ViewHolder();
            viewHolder.tvTitle    = (TextView)  convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView)  convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage    = (ImageView) convertView.findViewById(R.id.idMovieImage);
            convertView.setTag(viewHolder);

            // Toast the name to display temporarily on screen
            // I like this for debugging.  Pretty neat.
            toastMsg = "getView("+position+") inflated new view.";
        } else {
            // Re-using the view - so just get the view holder that it has cached.
            viewHolder = (ViewHolder) convertView.getTag();

            toastMsg = "getView("+position+") recycling old view.";
        }
        //Toast.makeText(getContext(), toastMsg, Toast.LENGTH_SHORT).show();

        // retrieve the image view from the view holder cached for the row.
        // ImageView ivImage = (ImageView) convertView.findViewById(R.id.idMovieImage);
        ImageView ivImage = viewHolder.ivImage;

        // Setup the listener for when clicked.  Pass the listener the position to cache!
        ivImage.setTag(position);

        // Clear out data from possible reused view (Why not above?)
        ivImage.setImageResource(0);

        // Attach the click event handler
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();

                // Access the row position here to get the correct data item
                Movie movie = getItem(position);

                // What happens when movie clicked on... 
                String id    = movie.getMovieId();
                String title = movie.getTitle();

                String toastMsg = String.format("Processing click for movie %s with id=%s at position=%d", title, id, position);
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_SHORT).show();
            }
        });

        // Just use the views cached in the holder for the row.
        viewHolder.tvTitle.setText(Html.fromHtml("<i>"+movie.getTitle()+"</i>"));
        viewHolder.tvOverview.setText(movie.getOverview());

        String imagePath = null;
        switch (getContext().getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                imagePath = movie.getPosterPath();
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                imagePath = movie.getBackdropPath();
                break;
            default:
                break;
        }

        // Use the Picasso library to get the URL and load the
        // image into the Layouts ImageView.
        Picasso.with(getContext())
                .load(imagePath)
                .transform(new RoundedCornersTransformation(10, 10))
                .fit()
                .centerInside()
                .placeholder(R.drawable.hour_glass_loading)
                .error(R.drawable.oh_no)
                .into(ivImage);

        return convertView;
    }
}
