package com.example.qbit.projectredapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class FrontpageAdaptor extends RecyclerView.Adapter<FrontpageAdaptor.MyViewHolder> {
        private ArrayList<ThreadClass> threads = new ArrayList<ThreadClass>();
        private Context context;
        private LayoutInflater inflater;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView username;
        public TextView score;
        public TextView category;
        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            username = (TextView) v.findViewById(R.id.username);
            score = (TextView) v.findViewById(R.id.score);
            category = (TextView) v.findViewById(R.id.category);
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
    public FrontpageAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ThreadClass current=threads.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(current.getTitle());
        holder.username.setText(current.getUsername());
        holder.score.setText(current.getScore());
        holder.category.setText(current.getCategory());
    }
    @Override
    public int getItemCount() {
        return threads.size();
    }
}
