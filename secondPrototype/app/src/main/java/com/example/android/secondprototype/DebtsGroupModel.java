package com.example.android.secondprototype;

import java.util.ArrayList;

/**
 * Created by Ehtisham on 12/21/17.
 */

public class DebtsGroupModel {
    ArrayList<Debts> debtsGroup = new ArrayList<>();

    public DebtsGroupModel(ArrayList<Debts> debts){

        this.debtsGroup = debts;
    }


    public void setDebtsGroup(ArrayList<Debts> debts) {
        this.debtsGroup = debts;
    }

    public ArrayList<Debts> getDebts() {

        return debtsGroup;
    }

}