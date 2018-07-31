package com.example.android.secondprototype;

/**
 * Created by Ehtisham on 12/14/17.
 */

public class groupitem {
    String GroupName;
    String Date;
    public groupitem(String GroupName, String Date){
        this.Date = Date;
        this.GroupName = GroupName;
    }
    public groupitem(){
        this.Date=null;
        this.GroupName=null;
    }
}
