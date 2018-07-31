package com.example.android.secondprototype;

import android.app.ProgressDialog;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GroupDetailModel {
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static List<Transactions> data = new ArrayList<>();
    public static List<Transactions> newTransaction = new ArrayList<>();
    public static List<Transactions> alldata = new ArrayList<>();
    public static List<TransactionMembers> debts = new ArrayList<>();
    public static List<TransactionMembers> getDebts = getdebts();
    ProgressDialog progressDialog2;
    static Transactions transactions;
    static List<TransactionMembers> transactionMembers = new ArrayList<>();
    public static List<Transactions> getdata() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Transactions");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    Transactions transactions = new Transactions();
                    transactions = i.getValue(Transactions.class);
                    data.add(transactions);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return data;
    }

    public static List<TransactionMembers> getdebts() {
        debts.clear();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Debts").child(GroupViewAdapter.groupname);
        if (database.getReference("Debts").child(GroupViewAdapter.groupname).equals(null)){

        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                debts.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    TransactionMembers transaction = new TransactionMembers();
                    transaction= i.getValue(TransactionMembers.class);
                    Log.e("kaka",transaction.getName());
                    Log.e("kaka",  Integer.toString(transaction.getAmmount()));
                    debts.add(transaction);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return debts;
    }
}
