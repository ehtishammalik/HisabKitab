package com.example.android.secondprototype;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.android.secondprototype.GroupViewAdapter;
import com.example.android.secondprototype.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Ehtisham on 12/11/17.
 */
public class WhoPaid extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    WhoPaidAdapter adapter;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    public String groupname = GroupViewAdapter.groupname;
    Button btn_creategroup;
    int amountPerPerson =0, credit=0;
    boolean alreadyexist;
    ProgressDialog progressDialog2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_who_paid, container, false);
        recyclerView = view.findViewById(R.id.members);
        adapter = new WhoPaidAdapter(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_creategroup = (Button) view.findViewById(R.id.whopaid_next);
        btn_creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TransactionName.transaction.setMembers(WhoPaidAdapter.members);
                TransactionName.transaction.setAmmount(WhoPaidAdapter.Ammount);
                TransactionName.transaction.setGroupname(groupname);

                if (WhoPaidAdapter.members.size() != 0 && TransactionName.transaction.getEvent() != null && ifAlreadyexist() == false) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Transactions");
                    myRef.child(TransactionName.transaction.getEvent()).setValue(TransactionName.transaction);
                    GroupDetailsFragment.alldata.clear();
                    myRef = database.getReference("Debts");
                    List<TransactionMembers> write = calculateDebts();
                    //for (int i=0;i<write.size();i++){
                     //   myRef.child(GroupViewAdapter.groupname).setValue(write.get(i));
                    //}

                    if (myRef.child(GroupViewAdapter.groupname) == null || myRef == null){
                        Log.e("setting debts", "new");
                        myRef.child(GroupViewAdapter.groupname).setValue(calculateDebts());
                    } else {
                        Log.e("setting debts", "update");
                        myRef.child(GroupViewAdapter.groupname).setValue(updateDebts());
                    }

                    progressDialog2 = ProgressDialog.show(getContext(), "Fetching Data", "It may take a while", true);
//                    GroupDetailsFragment.alldata.clear();
//                    GroupDetailsFragment.getDebts.clear();
                    GroupDetailsFragment.alldata = GroupDetailModel.getdata();
                    GroupDetailsFragment.getDebts = GroupDetailModel.getdebts();

                    final Handler handler = new Handler();
                    if (GroupDetailsFragment.alldata.size() == 0 || GroupDetailsFragment.getDebts.size() == 0) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (GroupDetailsFragment.alldata.size() != 0 && GroupDetailsFragment.getDebts.size() != 0) {
                                    progressDialog2.dismiss();
                                    displaySelectedScreen(R.id.nav_view,groupname);
                                }
                            }
                        }, 18000);

                    }
                }

            }
        });
        getActivity().setTitle("");
        return view;
    }

    public List<String> getdata() {
        List<Group_Details> groups = Groups.members;
        List<String> members = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getName() == groupname) {
                members = groups.get(i).getMembers();
            }
        }


        return members;
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
        }

        if (fragment!=null){
            getActivity().setTitle(title);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
    }
    public boolean ifAlreadyexist(){
        for (int i=0; i<GroupDetailsFragment.alldata.size();i++){
            if (GroupDetailsFragment.alldata.get(i).equals(TransactionName.transaction.getEvent())){
                alreadyexist = true;
                return true;
            }
        }
        return false;
    }
    public List<TransactionMembers> calculateDebts(){
        amountPerPerson = (int)(WhoPaidAdapter.Ammount/WhoPaidAdapter.members.size());
        List<TransactionMembers> membersDebts = new ArrayList<>();
        TransactionMembers member;
        for (int i=0;i <WhoPaidAdapter.members.size();i++){
            credit = WhoPaidAdapter.members.get(i).getAmmount() - amountPerPerson;
            Log.e("credit", Integer.toString(credit));
            member=new TransactionMembers(WhoPaidAdapter.members.get(i).getName(),credit);
            membersDebts.add(member);
            //member.setName(WhoPaidAdapter.members.get(i).getName());
            //member.setAmmount(credit);
            //membersDebts.add(member);
            //Log.e("credit in debts", Integer.toString(membersDebts.get(0).getAmmount()));
            //Log.e("Name in debts", membersDebts.get(0).getName());
        }
      return membersDebts;
    }


    public List<TransactionMembers> updateDebts(){
        amountPerPerson = (int)(WhoPaidAdapter.Ammount/WhoPaidAdapter.members.size());
        List<TransactionMembers> membersDebts = calculateDebts();
        List<TransactionMembers> previousDebts = GroupDetailModel.getdebts();
        for (int i=0;i<previousDebts.size();i++){
            int newAmount = previousDebts.get(i).getAmmount() + membersDebts.get(i).getAmmount();
            membersDebts.get(i).setAmmount(newAmount);
        }
        return membersDebts;
    }

}
