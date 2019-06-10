package com.example.jacky.Model;
//目錄class, 資料Name + Image
public class Category {
    private String Name;
    private String Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Category()
    { }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }
}
