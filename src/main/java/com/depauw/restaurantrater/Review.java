package com.depauw.restaurantrater;

import java.sql.Time;

import java.util.Date;

public class Review {
    private String restaurantName;
    private Date date;
    private Time time;
    private String meal;
    private int rating;
    private boolean isFavorite;

    public Review(){}

    public Review (String restaurantName, Date date, Time time, String meal, int rating, boolean isFavorite){
        this.restaurantName = restaurantName;
        this.date = date;
        this.time = time;
        this.meal = meal;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    public String getRestaurantName(){
        return restaurantName;
    }

    public Date getDate(){
        return date;
    }

    public Time getTime(){
        return time;
    }

    public String getMeal(){
        return meal;
    }

    public int getRating(){
        return rating;
    }

    public boolean getIsFavorite(){
        return isFavorite;
    }

}
