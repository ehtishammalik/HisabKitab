package com.example.android.secondprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Ehtisham on 12/14/17.
 */

public class TDAdapter extends RecyclerView.Adapter <TDAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<TransactionMembers> data = new ArrayList<>();
    TransactionMembers items = new TransactionMembers();
    public TDAdapter (Context context, ArrayList<TransactionMembers> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.members_row, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        items = data.get(position);
        String amount = Integer.toString(items.getAmmount());
        holder.name.setText(items.getName());
        holder.amount.setText(amount);
    }

    @Override
    public int getItemCount() {
        Log.e("datasize in tdadapter", Integer.toString(data.size()));
        return data.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView amount;

        public myViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.member_name);
            amount = itemView.findViewById(R.id.member_amount);
        }
    }

}
