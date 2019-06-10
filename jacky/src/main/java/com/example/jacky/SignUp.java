package com.example.jacky;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacky.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText estPhone, estName, estPassword;
    Button btnSignUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        estPhone = (EditText)findViewById(R.id.estPhone);
        estName = (EditText)findViewById(R.id.estName);
        estPassword = (EditText)findViewById(R.id.estPassword);

        btnSignUP = (Button)findViewById(R.id.btnSignUP);

        //connect to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDialog.dismiss();
                        //check if already in firebase
                        if(dataSnapshot.child(estPhone.getText().toString()).exists()){
                            Toast.makeText(SignUp.this, "Phone Number already register!", Toast.LENGTH_SHORT).show();
                        }else{
                            User user = new User(estName.getText().toString(), estPassword.getText().toString(), estPhone.getText().toString());
                            table_user.child(estPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "create succussfully!", Toast.LENGTH_SHORT).show();
                            finish(); //Call this when your activity is done and should be closed
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
