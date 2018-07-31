package com.example.android.secondprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


public class GroupViewAdapter extends RecyclerView.Adapter <GroupViewAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<groupitem> data = Collections.emptyList();
    FragmentManager fm;
    public static String groupname;
    ProgressDialog progressDialog2;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    static String Date;

    public GroupViewAdapter (Context context, List<groupitem> data,FragmentManager fm){
        inflater=LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.fm = fm;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grouprow, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        View itemview;
        final groupitem items = data.get(position);
        holder.text.setText(items.GroupName);
        holder.date.setText(items.Date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupname = data.get(position).GroupName;
                Date = data.get(position).Date;
                String TAG = "Check";
                Log.e(TAG, groupname);
                MainActivity.fragmentName = "Groups";

                progressDialog2 = ProgressDialog.show((MainActivity)context, "Fetching Data", "It may take a while", true);
                GroupDetailsFragment.alldata = GroupModel.transactionsData;

                GroupDetailsFragment.getDebts.clear();
                for (int i=0;i<GroupModel.alldebts.size();i++){
//                    if (GroupModel.alldebts.get(i).getName() == groupname){
//                        GroupDetailsFragment.getDebts = GroupModel.alldebts.get(i).getDebts();
//                    }
                }
                final Handler handler = new Handler();
                if (GroupDetailsFragment.alldata.size() == 0 || GroupDetailsFragment.getDebts.size() == 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                                progressDialog2.dismiss();
                                displaySelectedScreen(R.id.nav_view,groupname);
                            } else {
                                progressDialog2.dismiss();
                                displaySelectedScreen(R.id.notransactionyet,groupname);
                            }
                        }
                    }, 15000);

                }
                else {
                    if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                        progressDialog2.dismiss();
                        displaySelectedScreen(R.id.nav_view,groupname);
                    } else {
                        progressDialog2.dismiss();
                        displaySelectedScreen(R.id.notransactionyet,groupname);
                    }
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                // Toast.makeText(parent.getContext(),recyclerView.getChildAdapterPosition(view),Toast.LENGTH_LONG).show();
                groupname = data.get(position).GroupName;
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(view.getContext());
                }
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Group?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("Groups");
                                myRef.child(groupname).removeValue();
                                for (int i=0;i<GroupModel.data.size();i++){
                                    if (groupname == data.get(i).GroupName){
                                        GroupModel.data.remove(i);
                                    }
                                }



                                    final Handler handler = new Handler();
                                    if (GroupModel.data.size() == 0) {
                                        progressDialog2 = ProgressDialog.show((MainActivity)context,"Fetching Data", "It may take a while",true);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (GroupModel.data.size() != 0) {
                                                    progressDialog2.dismiss();
                                                    displaySelectedScreen(1,"OverView");
                                                } else {
                                                    progressDialog2.dismiss();
                                                    displaySelectedScreen(R.id.nogroupyet,"OverView");
                                                }
                                            }
                                        }, 15000);

                                    }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        TextView date;

        public myViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.rowtext);
            date = itemView.findViewById(R.id.rowdate);
        }
    }


    private void displaySelectedScreen (int id, String title){
        android.support.v4.app.Fragment fragment = null;
        switch (id){
            case R.id.nav_view:
                fragment = new GroupDetailsFragment();
                break;
            case R.id.notransactionyet:
                fragment = new NoTransactions();
                break;
            case R.id.nogroupyet:
                fragment = new NoGroups();
                break;
            case 1:
                fragment = new Groups();
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
