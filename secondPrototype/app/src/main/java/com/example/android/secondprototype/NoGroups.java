package com.example.android.secondprototype;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NoGroups extends android.support.v4.app.Fragment {


    //Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.nogroups, container, false);
        MainActivity.thisfragment="No Groups";
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.nogroup_addnew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setTitle("Create Group");
                MainActivity.fragmentName = "No Groups";
                displaySelectedScreen(R.id.nav_view);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("No Groups");
    }

    private void displaySelectedScreen(int id) {
        android.support.v4.app.Fragment fragment = null;
        switch (id) {
            case R.id.nav_view:
                fragment = new CreateGroup();
                break;
        }
        if (fragment != null) {
            MainActivity ma = (MainActivity) getContext();
            ma.setTitle("CreateGroup");
            FragmentTransaction ft = ma.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }

}
