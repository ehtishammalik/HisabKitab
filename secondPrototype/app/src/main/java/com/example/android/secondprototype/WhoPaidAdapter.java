package com.example.android.secondprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 ***************************************************************************************/

public class WhoPaidAdapter extends RecyclerView.Adapter <WhoPaidAdapter.myViewHolder> {
    public LayoutInflater inflater;
    Context context;
    List<String> data = new ArrayList<>();
    static ArrayList<TransactionMembers> members = new ArrayList<>();
    static int Ammount = 0;
    int count =0;
    TransactionMembers tm;
    public WhoPaidAdapter (Context context, List<String> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.Ammount = 0;
        members.clear();
        for (int i=0; i<data.size();i++){
            TransactionMembers transactionMembers = new TransactionMembers();
            transactionMembers.setName(data.get(i));
            Log.e("setting members", transactionMembers.getName());
            members.add(transactionMembers);
        }
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grouprow, parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final String member = data.get(position);
        holder.text.setText(member);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tm = new TransactionMembers();
                tm.setName(member);
                showChangeLangDialog();
//                Bundle b = new Bundle();
//                b.putSerializable("Members",members);
//                android.support.v4.app.Fragment fragment = new CreateGroup();
//                fragment.setArguments(b);

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
    /***************************************************************************************
     *    Title: Show custom alert dialoge.
     *    Author: Bhavyanshu Parasher
     *    Date Accessed: 2 Nov, 2017
     *    Availability: https://bhavyanshu.me/tutorials/create-custom-alert-dialog-in-android/08/20/2015
     *
     ***************************************************************************************/

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        inflater=LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.amountpopup, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);


        dialogBuilder.setTitle("Amount");
        dialogBuilder.setMessage("Enter Amount");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               String s= edt.getText().toString();
               tm.setAmmount(Integer.parseInt(s));
               for (int i=0;i<members.size();i++){
                   if (members.get(i).getName().equals(tm.getName()) && members.get(i).getAmmount() ==0){
                       members.get(i).setAmmount(tm.getAmmount());
                       members.get(i).setName(tm.getName());
                   }
               }

               Ammount +=tm.getAmmount();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
