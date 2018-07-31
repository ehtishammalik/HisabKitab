package com.example.android.secondprototype;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.android.secondprototype.GroupViewAdapter;
import com.example.android.secondprototype.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Ehtisham on 12/11/17.
 */
public class GroupDetailsFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private RecyclerView debtsrecyclerView;
    private GDFviewAdapter adapter;
    private DebtsAdapter debtsAdapter;
    FloatingActionButton fab;
    private FragmentManager fm;
    private LayoutInflater inflater2;
    private ViewGroup container2;
    public static groupitem current;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static List<Transactions> data = new ArrayList<>();
    public static List<Transactions> newTransaction = new ArrayList<>();
    public static List<Transactions> alldata = new ArrayList<>();
    public static List<TransactionMembers> debts = new ArrayList<>();
    public static List<TransactionMembers> getDebts = new ArrayList<>();
    ProgressDialog progressDialog2;
    static Transactions transactions;
    static List<TransactionMembers> transactionMembers = new ArrayList<>();
    String Date = "12/12/2017";

    //Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.thisfragment = "Group Detail";
        View view = inflater.inflate(R.layout.fragment_group_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        debtsrecyclerView = (RecyclerView) view.findViewById(R.id.debts_recycler_view);
        fab = (FloatingActionButton) view.findViewById(R.id.addnew);


        if (alldata.size() == 0 || getDebts.size() != 0) {
            newTransaction.clear();
            for (int i = 0; i < alldata.size(); i++) {
                if (alldata.get(i).getGroupname().equals(GroupViewAdapter.groupname)) {

                    newTransaction.add(alldata.get(i));
                }

            }
            if (newTransaction.size() != 0) {
                Log.e("notransaction", "withoutholder");

                adapter = new GDFviewAdapter(getContext(), newTransaction);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                debtsAdapter = new DebtsAdapter(getContext(), getDebts);
                debtsrecyclerView.setAdapter(debtsAdapter);
                debtsrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                fab = (FloatingActionButton) view.findViewById(R.id.addnew);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().setTitle("Create Transaction");
                        MainActivity.fragmentName = "GroupDetail";
                        displaySelectedScreen(R.id.Transaction_Name);
                    }
                });
            }

        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void transactionsView() {

    }

    private void displaySelectedScreen(int id) {
        android.support.v4.app.Fragment fragment = null;
        switch (id) {
            case R.id.nav_view:
                fragment = new Groups();
                break;
            case R.id.Transaction_Name:
                fragment = new TransactionName();
        }
        if (fragment != null) {
            MainActivity ma = (MainActivity) getContext();

            FragmentTransaction ft = ma.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }

}
