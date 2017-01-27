package com.example.bowang.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bowang.flickster.R;
import com.example.bowang.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bowang on 1/23/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverView);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.lvMoviesImage);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageView lvImage = (ImageView) convertView.findViewById(R.id.lvMoviesImage);
        lvImage.setImageResource(0);

        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverView());

        /*
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverView = (TextView) convertView.findViewById(R.id.tvOverView);

        tvTitle.setText(movie.getOriginalTitle());
        tvOverView.setText(movie.getOverView());
        */

        // load different image based on orientation
        String imagePath = null;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imagePath = movie.getBackdropPath();
        }
        if (imagePath != null && !imagePath.isEmpty()) {
            Picasso.with(getContext()).load(imagePath)//.fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.future_studio_launcher_web).into(lvImage);
        }

        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView overview;
    }

}
