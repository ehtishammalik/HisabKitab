package com.example.android.secondprototype;

/**
 * Created by Ehtisham on 12/29/17.
 */

public class TransactionMembers {
    private String Name=null;
    private int Ammount=0;
    private String GroupName;

    public TransactionMembers(){
      //for data snapshot
    }
    public TransactionMembers(String Name, int Ammount){
        this.Name=Name;
        this.Ammount=Ammount;
    }
    //public TransactionMembers(){
    //    this.Ammount=0;
      //  this.Name=null;
    //}
    public void setName(String name) {
        Name = name;
    }

    public void setAmmount(int ammount) {
        Ammount = ammount;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getName() {

        return Name;

    }

    public int getAmmount() {
        return Ammount;
    }
}
