package com.example.android.secondprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/***************************************************************************************
        *    Title: Recycler View in Android: The basics.
        *    Author: Antonio Leiva
        *    Date Accessed: 2 Nov, 2017
        *    Availability: https://antonioleiva.com/recyclerview/
        *
        ***************************************************************************************/

public class CreateGroupAdapter extends RecyclerView.Adapter <CreateGroupAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<groupitem> data = Collections.emptyList();
    public CreateGroupAdapter (Context context, List<groupitem> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grouprow, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        groupitem items = data.get(position);
        holder.text.setText(items.GroupName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView date;

        public myViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.rowtext);
            date = itemView.findViewById(R.id.rowdate);
        }
    }

}
