package com.example.jacky;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.jacky.Model.Comments;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private CommentsAdapter commentsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Comments> comments, List<String> keys){
        mContext = context;
        commentsAdapter = new CommentsAdapter(comments, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(commentsAdapter);
    }

    class CommentItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        //private TextView mScore;
        private TextView mDescription;
        private RatingBar mScore;

        private String key;

        public CommentItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name_txtView);
            //mScore = (TextView) itemView.findViewById(R.id.price_txtView);
            mDescription = (TextView) itemView.findViewById(R.id.description_txtView);
            mScore = (RatingBar) itemView.findViewById(R.id.Score_ratingBar);
        }
        public void bind(Comments comments, String key){
            mName.setText(comments.getCustomer_Name());
            //mScore.setText(comments.getScore());
            mDescription.setText(comments.getDescription());
            mScore.setRating(Float.valueOf(comments.getScore()));
            this.key = key;
        }
    }
    class CommentsAdapter extends RecyclerView.Adapter<CommentItemView> {
        private List<Comments> comments;
        private List<String> mKeys;

        public CommentsAdapter(List<Comments> commentsList, List<String> mKeys) {
            this.comments = commentsList;
            this.mKeys = mKeys;
        }

        @Override
        public CommentItemView onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new CommentItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(CommentItemView commentItemView, int i) {
            commentItemView.bind(comments.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }
}
