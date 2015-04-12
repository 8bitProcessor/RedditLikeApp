package com.example.qbit.projectredapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsAdaptor extends RecyclerView.Adapter<CommentsAdaptor.CommentsViewHolder>{
    private static String voteURL="http://192.168.1.21/QueryFiles/vote_comment.php";
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<CommentClass> comments = new ArrayList<CommentClass>();
    SaveSharedPreference pm = new SaveSharedPreference();
    public CommentsAdaptor(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        public TextView username_comment ,score, comment_rec;
        public ImageButton upvote,downvote;

        public CommentsViewHolder(View v){
            super(v);
            username_comment= (TextView) v.findViewById(R.id.username_card);
            score = (TextView) v.findViewById(R.id.score_card);
            comment_rec = (TextView) v.findViewById(R.id.comment_card);
            upvote = (ImageButton) v.findViewById(R.id.upvote_comment);
            downvote = (ImageButton) v.findViewById(R.id.downvote_comment);
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

        holder.upvote.setOnClickListener(   new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int voteType=1;
                if(pm.getUsername(context)==""){
                    Toast.makeText(context, "Please login to vote!", Toast.LENGTH_LONG).show();
                }else{

                    try {
                        voteComment(current.getCommentID(), voteType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.downvote.setOnClickListener(   new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int voteType=0;
                if(pm.getUsername(context)=="") {
                    Toast.makeText(context, "Please login to vote!", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        voteComment(current.getCommentID(), voteType);
                    } catch (JSONException e) {
                    e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return comments.size();
    }

public void voteComment(String comment_id, int vote_type) throws JSONException {
        Log.d("commentID : ", comment_id);
        JSONObject voteComment = new JSONObject();
        voteComment.put("username",pm.getUsername(context));
        voteComment.put("password", pm.getPassword(context));
        voteComment.put("commentID", comment_id);
        voteComment.put("vote_type",vote_type);
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest send_vote = new JsonObjectRequest(Request.Method.POST, voteURL, voteComment, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, "Response : " + response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }) {
        };
        requestQueue.add(send_vote);
    }

}
