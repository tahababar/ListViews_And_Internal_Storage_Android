package com.depauw.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.depauw.restaurantrater.databinding.ActivityAddReviewBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class AddReviewActivity extends AppCompatActivity {

    private ActivityAddReviewBinding binding;
    private String date;
    private String time;
    public static final String EXTRA_RESTAURANT_NAME = "com.depauw.restaurantrater.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_DATE = "com.depauw.restaurantrater.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.depauw.restaurantrater.EXTRA_TIME";
    public static final String EXTRA_MEAL = "com.depauw.restaurantrater.EXTRA_MEAL";
    public static final String EXTRA_RATING = "com.depauw.restaurantrater.EXTRA_RATING";
    public static final String EXTRA_IS_FAVORITE = "com.depauw.restaurantrater.EXTRA_IS_FAVORITE";

    private View.OnClickListener addReviewBtn_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!TextUtils.isEmpty(binding.edittextRestaurantName.getText().toString()) && !TextUtils.isEmpty(binding.edittextReviewDate.getText().toString()) && !TextUtils.isEmpty(binding.edittextReviewTime.getText().toString())) {
                File myFile = new File(getFilesDir(), getResources().getString(R.string.internal_file_name));
                try (FileWriter myWriter = new FileWriter(myFile, true)) {
                    myWriter.write(binding.edittextRestaurantName.getText().toString());
                    myWriter.write(",");
                    myWriter.write(date);
                    myWriter.write(",");
                    myWriter.write(time);
                    myWriter.write(",");
                    String meal = "";
                    if (binding.radioBreakfast.isChecked()) {
                        meal = getResources().getString(R.string.breakfast);
                        myWriter.write(meal);
                    } else if (binding.radioLunch.isChecked()) {
                        meal = getResources().getString(R.string.lunch);;
                        myWriter.write(meal);
                    } else {
                        meal = getResources().getString(R.string.dinner);;
                        myWriter.write(meal);
                    }
                    myWriter.write(",");
                    myWriter.write(String.valueOf(binding.seekbarRating.getProgress()));
                    myWriter.write(",");
                    boolean isFav = false;
                    if (binding.checkboxFavorite.isChecked()) {
                        isFav = true;
                        myWriter.write("1");
                    } else {
                        isFav = false;
                        myWriter.write("0");
                    }
                    myWriter.write(System.lineSeparator());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(EXTRA_RESTAURANT_NAME, binding.edittextRestaurantName.getText().toString());
                    returnIntent.putExtra(EXTRA_DATE, date);
                    returnIntent.putExtra(EXTRA_TIME, time);
                    returnIntent.putExtra(EXTRA_MEAL, meal);
                    returnIntent.putExtra(EXTRA_RATING, binding.seekbarRating.getProgress());
                    returnIntent.putExtra(EXTRA_IS_FAVORITE, isFav);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private DatePickerDialog.OnDateSetListener dateepicker_datereview_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            date = String.valueOf(i1 + 1) + "/" + String.valueOf(i2) + "/" + String.valueOf(i);
            binding.edittextReviewDate.setText(date);
        }
    };

    private TimePickerDialog.OnTimeSetListener timepicker_timereview_timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            int hour = i;
            int minute = i1;
            int changeHour = 12;
            int changeMin = 10;
            int zeroHour = 0;
            String amOrPm = null;
            if (hour >= zeroHour && hour < changeHour){
                amOrPm = getResources().getString(R.string.AM_tag);
            }
            else{
                amOrPm = getResources().getString(R.string.PM_tag);;
            }
            String sMinute = "00";
            if(minute < changeMin){
                sMinute = "0" + minute;
            } else {
                sMinute = String.valueOf(minute);
            }
            if(hour > changeHour){
                hour = hour - changeHour;
            }
            if(hour != zeroHour) {
                time = String.valueOf(hour) + ":" + sMinute + " " + amOrPm;
            }
            else{
                time = "0" + String.valueOf(hour) + ":" + sMinute + " " + amOrPm;
            }
            binding.edittextReviewTime.setText(time);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        binding.buttonAddReview.setOnClickListener(addReviewBtn_onClickListener);
        binding.edittextReviewDate.setOnClickListener(edittext_clickListeners);
        binding.edittextReviewTime.setOnClickListener(edittext_clickListeners);
    }

    private View.OnClickListener edittext_clickListeners = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.edittext_review_date: {
                    Calendar myCalendar = Calendar.getInstance();
                    DatePickerDialog myPicker = new DatePickerDialog(AddReviewActivity.this, dateepicker_datereview_dateSetListener,
                            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    myPicker.show();
                    break;
                }
                case R.id.edittext_review_time:{
                    Calendar myCalendar = Calendar.getInstance();
                    TimePickerDialog myPicker = new TimePickerDialog(AddReviewActivity.this, timepicker_timereview_timeSetListener,
                            myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);
                    myPicker.show();
                    break;
                }
            }
        }
    };
}