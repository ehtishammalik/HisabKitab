package com.example.android.secondprototype;

import android.app.Fragment;
import android.app.FragmentManager;
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
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Ehtisham on 12/11/17.
 */
public class TransactionDetail extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private TDAdapter adapter;
    private FragmentManager fm;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    private static List<Transactions> check = new ArrayList<>();
    static Transactions transaction;
    TextView td_purpose, td_event, td_whopaid, td_amount, td_date, td_event_date;

    //Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_detail, container, false);
        MainActivity.thisfragment = "Transaction Detail";

        td_purpose = (TextView) view.findViewById(R.id.transactiondetail_purpose);
        td_event = (TextView) view.findViewById(R.id.transactiondetail_event);
        td_event.setText(GDFviewAdapter.eventname);
        td_whopaid = (TextView) view.findViewById(R.id.transactiondetail_whopaid);
        td_date = (TextView) view.findViewById(R.id.transactiondetail_date);
        td_event_date = (TextView) view.findViewById(R.id.transactiondetail_event_date);
        td_amount = (TextView) view.findViewById(R.id.td_event_amount);
        td_amount.setText(Integer.toString(TransactionDetailModel.thisGroup().getAmmount()));
        recyclerView = (RecyclerView) view.findViewById(R.id.transactiondetail_recview);
        td_event_date.setText(TransactionDetailModel.thisGroup().getDate());
        adapter = new TDAdapter(getActivity(), TransactionDetailModel.thisGroupMembers());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void displaySelectedScreen(int id) {
        android.support.v4.app.Fragment fragment = null;
        switch (id) {
            case R.id.nav_view:
                fragment = new CreateGroup();
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
