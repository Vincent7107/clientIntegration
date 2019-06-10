package com.example.jacky.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.jacky.Cart;
import com.example.jacky.Common.Commons;

import com.example.jacky.Database.Database;
import com.example.jacky.Model.Request;
import com.example.jacky.OrderStatus;
import com.google.android.gms.common.internal.service.Common;
import com.example.jacky.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference requests;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Request");
    }

    @Override
    public int onStartCommand(Intent intend, int flags, int startId){
        requests.addChildEventListener(this);
        return super.onStartCommand(intend, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s){}

    public void onChildChanged(DataSnapshot dataSnapshot, String s){
        Request request = dataSnapshot.getValue(Request.class);
        //submit to firebase
        if(request.getPhone().equals(Commons.currentUser.getPhone()))
            showNotification(dataSnapshot.getKey(),request);
    }

    private void showNotification(String key, Request request){
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        //intent.putExtra("userPhone", request.getPhone());
        //System.out.println(request.getPhone());

        //狀態通知
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            System.out.println("in");
            String channelId = "default";
            String channelName = "notice";
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Your order was updated")
                .setContentText("Order #" + key + "was update status to " + Commons.convertCodeToStatus(request.getStatus()))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .build();
        // 建立通知
        notificationManager.notify(1, notification);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot){}

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s){}

    @Override
    public void onCancelled(DatabaseError databaseError){}
}
