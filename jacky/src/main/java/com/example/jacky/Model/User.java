package com.example.jacky.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;

    public User(){
    }

    public User(String name, String password, String phone){
        Name = name;
        Password = password;
        Phone =  phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName(){
        return Name;
    }

    public String getPassword(){
        return Password;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public void setPassword(String password){
        Password = password;
    }
}
