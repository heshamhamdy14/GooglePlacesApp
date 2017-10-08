package com.example.islamiccenter.googleplacesapp.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.islamiccenter.googleplacesapp.PlacesModel.PlacesModel;
import com.example.islamiccenter.googleplacesapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by islamic center on 07/10/2017.
 */

public class PlacesAdapter extends ArrayAdapter<PlacesModel> {

    public PlacesAdapter(@NonNull Context context, @NonNull PlacesModel[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.place_row,parent,false);

        }
        PlacesModel placesModel=getItem(position);
        ImageView imageView= (ImageView)convertView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500/"+placesModel.getPhotos()).into(imageView);
        TextView textViewTitle=(TextView)convertView.findViewById(R.id.textView_name);
        RatingBar ratingBar =(RatingBar)convertView.findViewById(R.id.ratingBar);
        textViewTitle.setText(placesModel.getName());
        ratingBar.getRating();

        return convertView;
    }
}
