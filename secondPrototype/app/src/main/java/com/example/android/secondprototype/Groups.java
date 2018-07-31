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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Groups extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private GroupViewAdapter adapter;
    private FragmentManager fm;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    public static Group_Details group;
    public static List<groupitem> data  = new ArrayList<>();
    public static List<Group_Details> members = new ArrayList<>();
    private List<groupitem> fetchdata = new ArrayList<>();
    ProgressDialog progressDialog;

    //Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.groups, container, false);
        MainActivity.thisfragment = "Groups";
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addnew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentName = "Groups";
                displaySelectedScreen(R.id.nav_view);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.rcview);
        fetchdata = GroupModel.data;
        members = GroupModel.allGroups;
        data = GroupModel.data;
        final Handler handler = new Handler();
        if(fetchdata.size() == 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (data.size() !=0) {
                        adapter = new GroupViewAdapter(getActivity(), fetchdata, fm);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                }
            }, 12000);
        }
        else {
            adapter = new GroupViewAdapter(getActivity(), fetchdata, fm);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        return view;
    }


//    public static List<groupitem> getdata() {
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Groups");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                data.clear();
//                for (DataSnapshot i : dataSnapshot.getChildren()) {
//
//                    group = i.getValue(Group_Details.class);
//                    groupitem current = new groupitem();
//                    current.GroupName = group.getName();
//                    current.Date = group.getDate();
//                    data.add(current);
//                    members.add(group);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
//        return data;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("OverView");
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
