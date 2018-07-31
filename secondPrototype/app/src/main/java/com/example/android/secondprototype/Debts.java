package com.example.android.secondprototype;

import java.util.ArrayList;

/**
 * Created by Ehtisham on 12/21/17.
 */

public class Debts {
    String name;
    private ArrayList<TransactionMembers> debts;
    public Debts(ArrayList<TransactionMembers> debts, String name){
        this.debts= debts;
        this.name = name;
    }
    public Debts(){
        this.name = null;
        this.debts = new ArrayList<>();
    }



    public void setDebts(ArrayList<TransactionMembers> debts) {
        this.debts = debts;
    }

    public ArrayList<TransactionMembers> getDebts() {

        return debts;
    }

}