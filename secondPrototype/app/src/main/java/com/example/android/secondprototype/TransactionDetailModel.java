package com.example.android.secondprototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ehtisham on 1/5/18.
 */

public class TransactionDetailModel {
    private static List<Transactions> data = new ArrayList<>();
    public static List<Transactions> getdata() {
        data = GroupDetailsFragment.alldata;
        return data;
    }

    public static Transactions thisGroup() {
        Transactions temp = new Transactions();

        for (int i = 0; i < getdata().size(); i++) {
            if (GDFviewAdapter.eventname.equals(getdata().get(i).getEvent()) && GroupViewAdapter.groupname.equals(getdata().get(i).getGroupname())) {
                temp = getdata().get(i);
            }
        }
        return temp;
    }

    public static ArrayList<TransactionMembers> thisGroupMembers() {
        ArrayList<TransactionMembers> mem = new ArrayList<>();
        mem = thisGroup().getMembers();
        return mem;
    }
}
