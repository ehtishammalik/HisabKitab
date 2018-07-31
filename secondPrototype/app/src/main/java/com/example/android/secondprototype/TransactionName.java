package com.example.android.secondprototype;

import android.app.Fragment;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
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

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class TransactionName extends android.support.v4.app.Fragment {
    EditText Transactionname, Description;
    Button buttonnext;
    public static Transactions transaction;
    public String Date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transaction_name, container, false);
        MainActivity.thisfragment = "Create Transaction";

        Transactionname = view.findViewById(R.id.Transaction_Name);
        Description = view.findViewById(R.id.description);
        buttonnext = (Button) view.findViewById(R.id.transactionname_next);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date = df.format(c.getTime());

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = new Transactions();
                transaction.setEvent(Transactionname.getText().toString());
                transaction.setDescription(Description.getText().toString());
                transaction.setDate(Date);
                getActivity().setTitle("Create Transaction");
                displaySelectedScreen(R.id.nav_view);


            }
        });
        getActivity().setTitle("Create Transaction");
        return view;
    }

    private void displaySelectedScreen(int id) {
        android.support.v4.app.Fragment fragment = null;
        switch (id) {
            case R.id.nav_view:
                fragment = new WhoPaid();
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
