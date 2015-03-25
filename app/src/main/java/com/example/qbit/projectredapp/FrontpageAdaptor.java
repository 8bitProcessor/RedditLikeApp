package com.example.qbit.projectredapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by qbit on 24/03/15.
 */
public class FrontpageAdaptor extends RecyclerView.Adapter<FrontpageAdaptor.MyViewHolder> {
    private LayoutInflater inflater;
    private List<ThreadClass> threads= Collections.emptyList();
    public FrontpageAdaptor(Context context, List<ThreadClass> threads){
        inflater= LayoutInflater.from(context);
        this.threads=threads;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.thread_view, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ThreadClass current=threads.get(position);
        holder.title.setText(current.title);
        holder.username.setText(current.username);
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public FrontpageAdaptor() {
        super();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView username;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            username=(TextView) itemView.findViewById(R.id.username);

        }
    }
}
