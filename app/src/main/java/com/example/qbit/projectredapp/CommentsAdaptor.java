package com.example.qbit.projectredapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by qbit on 10/04/15.
 */
public class CommentsAdaptor extends RecyclerView.Adapter<CommentsAdaptor.CommentsViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<CommentClass> comments = new ArrayList<CommentClass>();
    public CommentsAdaptor(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        public TextView username_comment;
        public TextView score;
        public TextView comment_rec;
        public CommentsViewHolder(View v){
            super(v);
            username_comment= (TextView) v.findViewById(R.id.username_card);
            score = (TextView) v.findViewById(R.id.score_card);
            comment_rec = (TextView) v.findViewById(R.id.comment_card);
        }
    }
    public void setCommentData(ArrayList<CommentClass> netComments){
        this.comments=netComments;
        notifyItemRangeChanged(0,netComments.size());

    }
    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_adaptor_layout, parent, false);
        CommentsViewHolder vh = new CommentsViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(CommentsViewHolder holder, final int position){
            final CommentClass current =comments.get(position);
            holder.username_comment.setText(current.getUsername());
            holder.score.setText(current.getScore());
            holder.comment_rec.setText(current.getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
