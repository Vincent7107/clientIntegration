package com.example.jacky.Model;
//食物class, 資料 名+圖+描述+ID+$
public class Food {
    private String Description, Image, MenuID, Name, Price;

    public Food()
    {}

    public Food(String description, String image, String menuID, String name, String price) {
        Description = description;
        Image = image;
        MenuID = menuID;
        Name = name;
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescriptionn(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
