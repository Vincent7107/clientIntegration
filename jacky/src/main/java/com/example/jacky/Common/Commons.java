package com.example.jacky.Common;

import com.example.jacky.Model.User;

public class Commons {
    public static User currentUser;

    public static String convertCodeToStatus(String status){
        if(status.equals("0")) return "未確認";
        else if(status.equals("1")) return "已確認";
        else return "訂單已完成，請取餐";
    }
}
