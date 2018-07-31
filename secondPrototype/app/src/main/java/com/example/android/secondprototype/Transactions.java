package com.example.android.secondprototype;

import java.util.ArrayList;

/**
 * Created by Ehtisham on 12/21/17.
 */

public class Transactions {
    private String Event;
    private String Date;
    private int ammount;
    private String description;
    private String groupname;
    private ArrayList<TransactionMembers> members = new ArrayList<>();
    public Transactions(){
        this.Event=null;
        this.Date=null;
        this.ammount=0;
        this.members=null;
        this.description=null;
    }
    public Transactions(String event, String date, int ammount, ArrayList<TransactionMembers> members, String description){
        this.Event=event;
        this.Date=date;
        this.ammount=ammount;
        this.members=members;
        this.description=description;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMembers(ArrayList<TransactionMembers> members) {
        this.members = members;
    }

    public void setEvent(String event) {

        Event = event;

    }

    public String getEvent() {
        return Event;
    }

    public String getDate() {
        return Date;
    }

    public int getAmmount() {
        return ammount;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<TransactionMembers> getMembers() {
        return members;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }
}
