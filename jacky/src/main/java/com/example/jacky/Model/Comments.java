package com.example.jacky.Model;

public class Comments {
    private String Customer_Name;
    private String Score;
    private String Description;

    public Comments() {
    }

    public Comments(String customer_Name, String score, String description) {
        Customer_Name = customer_Name;
        Score = score;
        Description = description;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
