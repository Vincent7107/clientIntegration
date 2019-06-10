package com.example.jacky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.jacky.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;  //manage database creation and version,
                                                            // provides developers with a simple way
                                                            //to ship their Android app with an existing
                                                            // SQLite database and to manage its initial
                                                            // creation and any upgrades required with
                                                            // subsequent version releases

import java.util.ArrayList;

//write process to DB

public class Database extends SQLiteAssetHelper {           //https://github.com/jgilfelt/android-sqlite-asset-helper
    private static final String DATABASE_NAME  = "Order.db";
    private static final int DATABASE_VERSION  = 1;
    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Order> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();  //This is a convenience class that helps build SQL queries to be sent to SQLiteDatabase objects.

        String[] sqlSelect = {"ID", "ProductId", "ProductName", "Quantity", "Price"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        //Cursor會先從資料庫裡面讀出資料，暫存於tempDB資料庫內，再從tempDB逐筆讀出處理
        //http://kyleap.blogspot.com/2013/12/ms-sqlcursor.html
        Cursor c = qb.query(db, sqlSelect,null,null,null,null,null);

        final ArrayList<Order>result = new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new Order(
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))));
            }while(c.moveToNext());
        }

        return result;
    }

    public void addToCart(Order order){    //將餐點加入Cart
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES('%s','%s','%s','%s')",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice());
        db.execSQL(query);
    }

    public void deleteOrder(Order order){
        //System.out.println(order.getProductId() + order.getProductName());
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE ProductName = '%s'", order.getProductName());
        db.execSQL(query);
    }

    public void cleanCart(){               //刪除Cart
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void updateCart(Order order){   //調整數量
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ProductName = '%s'", order.getQuantity(), order.getProductName());
        db.execSQL(query);
    }
}
