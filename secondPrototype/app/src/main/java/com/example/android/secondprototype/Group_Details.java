package com.example.android.secondprototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ehtisham on 12/21/17.
 */

public class Group_Details {
    private String Name;
    private String Date;
    private ArrayList<String> members;
    private Debts debts = new Debts();
    public Group_Details(String Name, String Date, ArrayList<String> members){
        this.Name=Name;
        this.Date=Date;
        this.members= members;
    }
    public Group_Details(){

    }

    public String getName() {
        return Name;
    }

    public String getDate() {
        return Date;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public Debts getDebts() {
        return debts;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void setDebts(Debts debts) {
        this.debts = debts;
    }
}
