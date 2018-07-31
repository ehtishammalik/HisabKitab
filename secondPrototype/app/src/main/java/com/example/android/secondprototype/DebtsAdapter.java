package com.example.android.secondprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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


public class DebtsAdapter extends RecyclerView.Adapter <DebtsAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<TransactionMembers> data = new ArrayList<>();
    public static String groupname;

    public DebtsAdapter (Context context, List<TransactionMembers> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
        Log.e("adapterconstructor", Integer.toString(data.size()));
        this.context = context;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.members_row, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        View itemview;
        final TransactionMembers items = data.get(position);
        Log.e("Size in adapter", Integer.toString(data.size()));
        holder.Name.setText(items.getName());
        if (items.getAmmount()<0){
            holder.Amount.setTextColor(context.getResources().getColor(R.color.red));
        }
        else {
            holder.Amount.setTextColor(context.getResources().getColor(R.color.teal));
        }
        holder.Amount.setText(Integer.toString(items.getAmmount()));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView Amount;

        public myViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.member_name);
            Amount = itemView.findViewById(R.id.member_amount);
        }
    }


    private void displaySelectedScreen (int id, String title){
        android.support.v4.app.Fragment fragment = null;
        switch (id){
            case R.id.nav_view:
                fragment = new GroupDetailsFragment();
                break;
        }

        if (fragment!=null){
            MainActivity ma = (MainActivity)context;
            ma.setTitle(title);
            FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }


}
