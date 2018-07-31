package com.example.android.secondprototype;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ehtisham on 1/4/18.
 */



public class GroupModel extends AsyncTask<String,String,String> {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference groupsRef = database.getReference("Groups");
    private static DatabaseReference debtsRef = database.getReference("Debts");
    private static DatabaseReference transactionsRef = database.getReference("Transactions");
    public static List<groupitem> data  = new ArrayList<>();
    public static Group_Details group;
    public static Debts debts;
    public static List<Group_Details> allGroups = new ArrayList<>();
    public static List<Debts> alldebts = new ArrayList<>();
    private static Transactions transactions;
    public static List<Transactions> transactionsData = new ArrayList<>();
    private static Debts debt = new Debts();
    ArrayList<TransactionMembers> tempList;

    @Override
    protected String doInBackground(String... strings) {

        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {

                    group = i.getValue(Group_Details.class);
                    groupitem current = new groupitem();
                    current.GroupName = group.getName();
                    current.Date = group.getDate();
                    data.add(current);
                    if(i.getKey() != null){
                        Log.e("GroupModel", "checking all groups " + data.get(0).GroupName);
                    }
                    allGroups.add(group);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        debtsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alldebts.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {

                    DatabaseReference myRef = database.getReference("Debts").child(i.getKey());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            tempList = new ArrayList<>();
                            for (DataSnapshot j : dataSnapshot.getChildren()) {
                                TransactionMembers transaction = j.getValue(TransactionMembers.class);
                                tempList.add(transaction);
                                if (tempList.size()!=0){
                                    Log.e("GroupModel", "checking all debts size" + tempList.size() + " plus data " + tempList.get(tempList.size()-1).getName());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });

                    debt.setDebts(tempList);
                    debt.name = i.getKey();
                    alldebts.add(debt);
                    if (alldebts.size()!=0){
                        Log.e("GroupModel", "checking all debts size  :-" + alldebts.size() + " -----plus data---- " + alldebts.get(alldebts.size()-1).name);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


       transactionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionsData.clear();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    Transactions transactions = new Transactions();
                    transactions = i.getValue(Transactions.class);
                    transactionsData.add(transactions);
                    if (transactionsData.size()!=0){
                        Log.e("GroupModel", "checking all debts size  :-" + transactionsData.size() + " -----plus data---- " + transactionsData.get(transactionsData.size()-1).getEvent());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return "Done";
    }

    @Override
    protected void onPostExecute(String s) {
        return;
    }
}
