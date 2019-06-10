package com.example.jacky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.jacky.Model.Comments;

import java.util.List;

public class NewComment extends AppCompatActivity {

    private EditText mName;
    private EditText mDescription;
    private Button mAdd_btn;
    private RatingBar mRatingbar;
    private float score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        mName = (EditText) findViewById(R.id.Name_editTxt);
        mDescription = (EditText) findViewById(R.id.Description_editTxt);

        mAdd_btn = (Button) findViewById(R.id.add_btn);

        mRatingbar = (RatingBar) findViewById(R.id.Score_ratingBar);

        mAdd_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Comments comment = new Comments();
                score = mRatingbar.getRating();
                comment.setCustomer_Name(mName.getText().toString());
                comment.setDescription(mDescription.getText().toString());
                comment.setScore("" + (int)score);
                new FirebaseDatabaseHelper().addComment(comment, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Comments> comments, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewComment.this, "The Comment record has been inserted successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }
        });
    }
}
