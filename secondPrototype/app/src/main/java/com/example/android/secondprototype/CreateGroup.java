package com.example.android.secondprototype;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.android.secondprototype.GroupViewAdapter;
import com.example.android.secondprototype.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ehtisham on 12/11/17.
 */
public class CreateGroup extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private CreateGroupAdapter adapter;
    EditText textin;
    Button buttonadd;
    LinearLayout linearLayout;
    EditText GroupName;
    ArrayList<Members> members;
    Group_Details GroupDetails = new Group_Details();
    Button btn_creategroup;
    static String date;
    public static ArrayList<String> groupnames;
    public boolean alreadyexist;
    ProgressDialog progressDialog;
    static String getGroupName;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_create_group, container, false);
        MainActivity.thisfragment = "Create Group";
        /*TextView clock = view.findViewById(R.id.clock);
        Date date = Calendar.getInstance().getTime();
        clock.setText(date.toString());*/
        textin = view.findViewById(R.id.textin);
        GroupName = view.findViewById(R.id.Group_Name);
        buttonadd = (Button) view.findViewById(R.id.add);
        linearLayout = view.findViewById(R.id.container);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        date = df.format(c.getTime());
        btn_creategroup = (Button) view.findViewById(R.id.button2);
        groupnames = new ArrayList<>();
        textin.setText("");

        buttonadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!textin.getText().toString().isEmpty()) {
                    groupnames.add(textin.getText().toString());
                    recyclerView = view.findViewById(R.id.create_grp);
                    adapter = new CreateGroupAdapter(getActivity(), getdata());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    textin.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        btn_creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupDetails = new Group_Details(GroupName.getText().toString(), date, groupnames);
                for (int i=0; i<Groups.data.size();i++){
                    if (Groups.data.get(i).GroupName.equals(GroupName.getText().toString())){
                        alreadyexist = true;
                    }
                }
                if (!GroupName.getText().toString().isEmpty() && GroupDetails.getMembers().size() != 0 && GroupDetails.getDate() != null && alreadyexist==false) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Groups");

                    myRef.child(GroupName.getText().toString()).setValue(GroupDetails);
                    getActivity().setTitle(GroupName.getText());
                    getGroupName = GroupName.getText().toString();
                    groupitem gi = new groupitem();
                    gi.GroupName = GroupName.getText().toString();
                    gi.Date = GroupDetails.getDate();
                    GroupModel.data.add(gi);
                    GroupModel.allGroups.add(GroupDetails);


                    progressDialog = ProgressDialog.show(getContext(),"Creating Group", "It may take a while",true);
                    GroupDetailsFragment.alldata = GroupModel.transactionsData;
                    for (int i=0;i<GroupModel.alldebts.size();i++){

                    }
                    GroupDetailsFragment.getDebts.clear();


                    if (GroupDetailsFragment.alldata.size() == 0 || GroupDetailsFragment.getDebts.size() == 0) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                                    progressDialog.dismiss();
                                    displaySelectedScreen(R.id.nav_view);
                                } else {
                                    progressDialog.dismiss();
                                    displaySelectedScreen(R.id.notransactionyet);
                                }
                            }
                        }, 17000);

                    }
                    else {
                        if (GroupDetailsFragment.alldata.size()!=0 && GroupDetailsFragment.getDebts.size()!=0) {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.nav_view);
                        } else {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.notransactionyet);
                        }
                    }
                }


            }
        });
        getActivity().setTitle("Create Group");
        return view;
    }

    public static List<groupitem> getdata() {
        List<groupitem> data = new ArrayList<>();
        String[] dates = {"10 Dec,2017 11:55", "10 Dec,2017 11:55", "10 Dec,2017 11:55", "10 Dec,2017 11:55",};

        for (int i = 0; i < CreateGroup.groupnames.size() && i < dates.length; i++) {
            groupitem current = new groupitem();
            current.GroupName = CreateGroup.groupnames.get(i);
            current.Date = date;
            data.add(current);
        }
        return data;
    }

    private void displaySelectedScreen(int id) {
        android.support.v4.app.Fragment fragment = null;
        getActivity().setTitle(GroupName.getText());
        switch (id) {
            case R.id.nav_view:
                fragment = new GroupDetailsFragment();
                break;
            case R.id.notransactionyet:
                fragment = new NoTransactions();
                break;
        }
        if (fragment != null) {
            MainActivity ma = (MainActivity) getContext();

            FragmentTransaction ft = ma.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }


}
