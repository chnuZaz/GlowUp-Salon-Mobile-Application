package com.example.glowup;

public class Review_Model {
    String After,Before,UserName,Review;

    public Review_Model() {
    }

    public Review_Model(String after, String before, String userName, String review) {
        After = after;
        Before = before;
        UserName = userName;
        Review = review;
    }

    public String getAfter() {
        return After;
    }

    public String getBefore() {
        return Before;
    }

    public String getUserName() {
        return UserName;
    }

    public String getReview() {
        return Review;
    }
}
