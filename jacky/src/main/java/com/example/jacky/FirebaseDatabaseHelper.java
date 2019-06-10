package com.example.jacky;

import android.support.annotation.NonNull;

import com.example.jacky.Model.Comments;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceComment;
    private List<Comments> comments = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Comments> comments, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceComment = mDatabase.getReference("Comment");
    }

    public void readMenu(final DataStatus dataStatus){
        mReferenceComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Comments comment = keyNode.getValue(Comments.class);
                    comments.add(comment);
                }
                dataStatus.DataIsLoaded(comments, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addComment(Comments comment, final DataStatus dataStatus){
        String key = mReferenceComment.push().getKey();
        mReferenceComment.child(key).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }
}
