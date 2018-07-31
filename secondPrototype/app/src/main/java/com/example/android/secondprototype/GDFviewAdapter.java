package com.example.android.secondprototype;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***************************************************************************************
 *    Title: Recycler View in Android: The basics.
 *    Author: Antonio Leiva
 *    Date Accessed: 2 Nov, 2017
 *    Availability: https://antonioleiva.com/recyclerview/
 *
 ***************************************************************************************/

public class GDFviewAdapter extends RecyclerView.Adapter <GDFviewAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<Transactions> data = new ArrayList<>();
    Transactions items = new Transactions();
    static String eventname;
    ProgressDialog progressDialog2;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    public static List<Transactions> newTransaction = new ArrayList<>();


    public GDFviewAdapter (Context context, List<Transactions> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.transaction_row, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        items = data.get(position);
        String amount = Integer.toString(items.getAmmount());
        holder.event_name.setText(items.getEvent());
        holder.event_date.setText(items.getDate());
        holder.event_amount.setText(amount);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventname = data.get(position).getEvent();
                MainActivity.fragmentName = "GroupDetail";
                displaySelectedScreen(R.id.nav_view);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                // Toast.makeText(parent.getContext(),recyclerView.getChildAdapterPosition(view),Toast.LENGTH_LONG).show();
                eventname = data.get(position).getEvent();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(view.getContext());
                }
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this reminder?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("Transactions");
                                myRef.child(eventname).removeValue();

                                GroupDetailsFragment.alldata = GroupDetailModel.getdata();
                                GroupDetailsFragment.getDebts.clear();
                                GroupDetailsFragment.getDebts = GroupDetailModel.getdebts();

                                if (GroupDetailsFragment.alldata.size() == 0) {
                                    newTransaction.clear();
                                    for (int i = 0; i < GroupDetailsFragment.alldata.size(); i++) {
                                        if (GroupDetailsFragment.alldata.get(i).getGroupname().equals(GroupViewAdapter.groupname)) {

                                            newTransaction.add(GroupDetailsFragment.alldata.get(i));
                                        }

                                    }
                                    if (newTransaction.size() == 0) {
                                        myRef = database.getReference("Debts");
                                        myRef.child(GroupViewAdapter.groupname).removeValue();
                                    }
                                }



                                GroupDetailsFragment.alldata = GroupDetailModel.getdata();
                                GroupDetailsFragment.getDebts.clear();
                                GroupDetailsFragment.getDebts = GroupDetailModel.getdebts();

                                final Handler handler = new Handler();
                                if (GroupDetailsFragment.alldata.size() == 0 || GroupDetailsFragment.getDebts.size() == 0) {
                                    progressDialog2 = ProgressDialog.show((MainActivity)context, "Fetching Data", "It may take a while", true);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                                                progressDialog2.dismiss();
                                                displaySelectedScreen(1);
                                            } else {
                                                progressDialog2.dismiss();
                                                displaySelectedScreen(R.id.notransactionyet);
                                            }
                                        }
                                    }, 15000);

                                }
                                else {
                                    if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                                        progressDialog2.dismiss();
                                        displaySelectedScreen(1);
                                    } else {
                                        progressDialog2.dismiss();
                                        displaySelectedScreen(R.id.notransactionyet);
                                    }
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
        TextView event_name;
        TextView event_date;
        TextView event_amount;

        public myViewHolder(View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_amount = itemView.findViewById(R.id.event_ammount);
            event_date = itemView.findViewById(R.id.event_date);
        }
    }

    private void displaySelectedScreen (int id){
        android.support.v4.app.Fragment fragment = null;
        MainActivity ma = (MainActivity)context;
        switch (id){
            case R.id.nav_view:
                fragment = new TransactionDetail();
                ma.setTitle(eventname);
                break;
            case R.id.notransactionyet:
                fragment = new NoTransactions();
                ma.setTitle("No Transactions");
                break;
            case 1:
                fragment = new GroupDetailsFragment();
                ma.setTitle(GroupViewAdapter.groupname);
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }


}
