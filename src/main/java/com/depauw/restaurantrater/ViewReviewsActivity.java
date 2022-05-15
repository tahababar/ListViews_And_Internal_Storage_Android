package com.depauw.restaurantrater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.depauw.restaurantrater.databinding.ActivityViewReviewsBinding;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ViewReviewsActivity extends AppCompatActivity {

    private ActivityViewReviewsBinding binding;
    private List<Review> list = new ArrayList<Review>();
    private CustomAdapter myAdapter;

    public static final int FROM_ADD_REVIEW_ACTIVITY = 1;

    private AdapterView.OnItemClickListener listviewReviews_onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ViewReviewsActivity.this);
            Review thisReview = (Review) adapterView.getItemAtPosition(i);
            DateFormat formatterDate = new SimpleDateFormat("M/d/yyyy");
            String d = formatterDate.format(thisReview.getDate());
            DateFormat formatterTime = new SimpleDateFormat("h:mm a", Locale.US);
            String t = formatterTime.format(thisReview.getTime());
            myBuilder.setTitle(getResources().getString(R.string.dialog_title))
                     .setMessage("This review was created on " + d + " at " + t);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();

        }
    };

    private View.OnClickListener addReviewBtn_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ViewReviewsActivity.this, AddReviewActivity.class);
            startActivityForResult(intent, FROM_ADD_REVIEW_ACTIVITY);
        }
    };

    private void loadData(){
            File myFile = new File(getFilesDir(), getResources().getString(R.string.internal_file_name));
            try (Scanner sc = new Scanner(myFile)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] split = line.split(",");
                    DateFormat formatterTime = new SimpleDateFormat("h:mm a", Locale.US);
                    DateFormat formatterDate = new SimpleDateFormat("M/d/yyyy");
                    boolean isFavorite = false;
                    if (split[5].equals(getResources().getString(R.string.is_fav))) {
                        isFavorite = true;
                    }
                    else{
                        isFavorite = false;
                    }
                    Date date = null;
                    try {
                        date = formatterDate.parse(split[1]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Review review = new Review(split[0], date, new java.sql.Time(formatterTime.parse(split[2]).getTime()), split[3], Integer.valueOf(split[4]), isFavorite);
                    list.add(review);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        myAdapter = new CustomAdapter(this, list);
        binding.listviewReviews.setAdapter(myAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        binding.listviewReviews.setOnItemClickListener(listviewReviews_onItemClickListener);
        binding.buttonAddReview.setOnClickListener(addReviewBtn_onClickListener);
    }
//    WITHOUT EXTRA CREDIT
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode, @NonNull Intent data) {
//        super.onActivityResult(requestCode, responseCode, data);
//        if(requestCode == FROM_ADD_REVIEW_ACTIVITY && responseCode == Activity.RESULT_OK){
//            list.clear();
//            binding.listviewReviews.setAdapter(null);
//            loadData();
//        }
//    }

//   WITH EXTRA CREDIT
    @Override
    protected void onActivityResult(int requestCode, int responseCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if(requestCode == FROM_ADD_REVIEW_ACTIVITY && responseCode == Activity.RESULT_OK){
           String newRestaurantName = data.getStringExtra(AddReviewActivity.EXTRA_RESTAURANT_NAME);
           String newDate = data.getStringExtra(AddReviewActivity.EXTRA_DATE);
           String newTime = data.getStringExtra(AddReviewActivity.EXTRA_TIME);
           String newMeal = data.getStringExtra(AddReviewActivity.EXTRA_MEAL);
           int newRating = data.getIntExtra(AddReviewActivity.EXTRA_RATING, 0);
           boolean newIsFav = data.getBooleanExtra(AddReviewActivity.EXTRA_IS_FAVORITE, false);
           DateFormat formatterTime = new SimpleDateFormat("h:mm a", Locale.US);
           DateFormat formatterDate = new SimpleDateFormat("M/d/yyyy");
           Date date = null;
           try {
                date = formatterDate.parse(newDate);
           } catch (ParseException e) {
                e.printStackTrace();
           }
            Review newReview = null;
           try {
                newReview = new Review(newRestaurantName, date, new java.sql.Time(formatterTime.parse(newTime).getTime()), newMeal, newRating, newIsFav);
           } catch (ParseException e) {
                e.printStackTrace();
           }
           list.add(newReview);
           myAdapter.notifyDataSetChanged();
        }
    }
}