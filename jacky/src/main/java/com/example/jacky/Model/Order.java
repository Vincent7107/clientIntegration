package com.example.jacky.Model;

public class Order {  //kind of item's order detail
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;

    public Order(){}

    public Order(String productId, String productName, String quantity, String price){
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public String getPrice() {
        return Price;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
