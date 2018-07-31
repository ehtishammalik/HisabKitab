package com.example.android.secondprototype;

/**
 * Created by hamza on 12/21/17.
 */

public class Debts_Details {
    public String from;
    public String to;
    public int ammount;
    public Debts_Details(){
        this.ammount=0;
        this.from=null;
        this.to=null;
    }
    public Debts_Details(String to, String from, int ammount){
        this.ammount=ammount;
        this.from=from;
        this.to=to;
    }

}
