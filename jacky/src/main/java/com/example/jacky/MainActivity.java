package com.example.jacky;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jacky.Common.Commons;
import com.example.jacky.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    Button btnSignIn, btnSignUp;
    EditText estPhone, estPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button)findViewById(R.id.btnSignIN);
        btnSignUp = (Button)findViewById(R.id.btnSignUP);

        estPassword = (EditText)findViewById(R.id.estPassword);
        estPhone = (EditText)findViewById(R.id.estPhone);

        //connect to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //連到database內名稱為User的節點
        final DatabaseReference table_user = database.getReference("User");

        //按下登入btn, 開始比對firebase
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讀取中畫面, 轉圈圈的那個
                final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();

                //firebase函式庫, 持續監聽是資料只要有變化，就會有改變的狀態。
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if(dataSnapshot.child(estPhone.getText().toString()).exists()){
                            //get user information
                            mDialog.dismiss();//釋放對話框資源
                            User user = dataSnapshot.child(estPhone.getText().toString()).getValue(User.class);  //dataSnapshot:receive data from firebase
                            user.setPhone(estPhone.getText().toString());
                            //登入成功後, 轉換到Home.class
                            if(user.getPassword() != null && user.getPassword().equals(estPassword.getText().toString())){
                                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                                //紀錄登入的使用者
                                Commons.currentUser = user;
                                startActivity(homeIntent);
                                finish();//關閉現有activity
                            }else{
                                Toast.makeText(MainActivity.this, "Sign in failed!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "user not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //按下註冊btn
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //從MainActivity 轉換到 SgnUp.class
                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });
    }
    void testPush() {
        Log.d("test","測試用push上傳程式");
    }
}

