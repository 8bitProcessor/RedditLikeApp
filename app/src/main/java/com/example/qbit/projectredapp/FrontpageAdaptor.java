package com.example.qbit.projectredapp;

import android.content.Context;
import android.content.Intent;
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

public class FrontpageAdaptor extends RecyclerView.Adapter<FrontpageAdaptor.MyViewHolder> {
        private ArrayList<ThreadClass> threads = new ArrayList<ThreadClass>();
        private Context context;
        private LayoutInflater inflater;
        SaveSharedPreference pm =new SaveSharedPreference();
        private static final String voteURL =  "http://192.168.1.21/QueryFiles/vote.php";
    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        // each data item is just a string in this case
        public TextView title;
        public TextView username;
        public TextView score;
        public TextView category;
        public ImageButton upvote;
        public ImageButton downvote;
        public ImageButton open;
        public ImageButton comments;

        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            username = (TextView) v.findViewById(R.id.username);
            score = (TextView) v.findViewById(R.id.score);
            category = (TextView) v.findViewById(R.id.category);
            upvote = (ImageButton) v.findViewById(R.id.up);
            downvote =(ImageButton) v.findViewById(R.id.down);
            open = (ImageButton) v.findViewById(R.id.open);
            comments =(ImageButton) v.findViewById(R.id.comments);
        }
    }

    public FrontpageAdaptor(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public void setData(ArrayList<ThreadClass> netThreads){
        this.threads = netThreads;
        notifyItemRangeChanged(0,netThreads.size());
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ThreadClass current=threads.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(current.getTitle());
        holder.username.setText(current.getUsername());
        holder.score.setText(current.getScore());
        holder.category.setText(current.getCategory());

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int voteType=1;
                try {
                    voteThread(current.getThreadID(), voteType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.downvote.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               int voteType=0;
               try {
                   voteThread(current.getThreadID(),voteType);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
        });
        holder.comments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Comments.class);
                i.putExtra("threadID", current.getThreadID());
                i.putExtra("title", current.getTitle());
                i.putExtra("username", current.getUsername());
                i.putExtra("category", current.getCategory());
                i.putExtra("score", current.getScore());
                context.startActivity(i);
            }
        });
   }
    @Override
    public int getItemCount() {
        return threads.size();
    }

    public void voteThread(String threadID, int voteType) throws JSONException {
        JSONObject voteObj = new JSONObject();
        //int tID = Integer.parseInt(threadID);
        voteObj.put("username",pm.getUsername(context));
        voteObj.put("password", pm.getPassword(context));
        voteObj.put("threadID", threadID);
        voteObj.put("voteType", voteType);
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest SendScore = new JsonObjectRequest(Request.Method.POST,voteURL,voteObj, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try {
                    Toast.makeText(context,"Response : " +response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }},new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){
        };
        requestQueue.add(SendScore);


    }






}
