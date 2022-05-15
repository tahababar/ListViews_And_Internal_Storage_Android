package com.depauw.restaurantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviews;

    public CustomAdapter(Context context, List<Review> reviews){
        this.context  = context;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_reviews_row, viewGroup, false);
        }
        Review thisReview = reviews.get(i);
        RatingBar restaurantRating = view.findViewById(R.id.ratingbar_restaurant_rating);
        TextView name = view.findViewById(R.id.textView_name);
        RadioButton breakfast = view.findViewById(R.id.radiobutton_breakfast);
        RadioButton lunch = view.findViewById(R.id.radiobutton_lunch);
        RadioButton dinner = view.findViewById(R.id.radiobutton_dinner);
        ProgressBar rating = view.findViewById(R.id.progressbar_rating);

        if (thisReview.getIsFavorite()) {
            restaurantRating.setProgress(1);
        }
        else{
            restaurantRating.setProgress(0);
        }
        name.setText(thisReview.getRestaurantName());
        if (thisReview.getMeal().equals("Breakfast")) {
            breakfast.setChecked(true);
            lunch.setChecked(false);
            dinner.setChecked(false);
        }
        if (thisReview.getMeal().equals("Lunch")) {
            breakfast.setChecked(false);
            lunch.setChecked(true);
            dinner.setChecked(false);
        }
        if (thisReview.getMeal().equals("Dinner")){
            breakfast.setChecked(false);
            lunch.setChecked(false);
            dinner.setChecked(true);
        }
        rating.setProgress(thisReview.getRating());

        return view;
    }
}
