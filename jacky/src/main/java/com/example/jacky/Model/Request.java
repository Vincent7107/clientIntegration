package com.example.jacky.Model;

import java.util.ArrayList;

public class Request {
    private String phone;
    private String name;
    private String total;
    private String status;  //show order status
    private ArrayList<Order> foods;   //list of food order
    private String description;

    public Request(){}

    public Request(String phone, String name, String total, ArrayList<Order> foods, String description){
        this.phone = phone;
        this.name = name;
        this.total = total;
        this.status = "0";  //  0->placed, 1->shipping, 2->shipped
        this.foods = foods;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Order> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Order> foods) {
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
