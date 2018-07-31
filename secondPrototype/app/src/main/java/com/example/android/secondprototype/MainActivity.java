package com.example.android.secondprototype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String fragmentName = "Groups";
    public static String thisfragment = "Groups";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressDialog = ProgressDialog.show(this,"Fetching Data", "It may take a while",true);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            final Handler handler = new Handler();
            if (GroupModel.data.size() == 0) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (GroupModel.data.size() != 0) {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.nav_view);
                        } else {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.nogroupyet);
                        }
                    }
                }, 12000);

            }
        }
        else {
            final Handler handler = new Handler();
            if (GroupModel.data.size() == 0) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (GroupModel.data != null) {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.nav_view);
                        } else {
                            progressDialog.dismiss();
                            displaySelectedScreen(R.id.nogroupyet);
                        }
                    }
                }, 12000);

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
        int count = getFragmentManager().getBackStackEntryCount();
        Log.e("stackentry" , Integer.toString(count));

        if (count == 0) {
            displaySelectedScreen(1);
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displaySelectedScreen (int id){
        android.support.v4.app.Fragment fragment = null;
        switch (id){
            case 1:
                if (fragmentName.equals("Groups")){
                    fragment = new Groups();
                }
                else if (fragmentName.equals("GroupDetail")){
                    fragmentName = "Groups";
                    fragment = new GroupDetailsFragment();
                }
                else if (fragmentName.equals("TransactionDetail")){
                    fragmentName = "GroupDetail";
                    fragment = new TransactionDetail();
                }
                else if (fragmentName.equals("No Groups")){
                    fragmentName = "No Groups";
                    fragment = new NoGroups();
                }

                break;
            case R.id.nav_view:
                if (thisfragment.equals("Groups")){
                    fragment = new Groups();
                }
                else if (thisfragment.equals("Group Detail")){
                    fragmentName = "Groups";
                    fragment = new GroupDetailsFragment();
                }
                else if (thisfragment.equals("Transaction Detail")){
                    fragmentName = "GroupDetail";
                    fragment = new TransactionDetail();
                }
                else if (thisfragment.equals("Create Group")){
                    fragmentName = "GroupDetail";
                    fragment = new CreateGroup();
                }
                else if (thisfragment.equals("Create Transaction")){
                    fragmentName = "GroupDetail";
                    fragment = new TransactionName();
                }
                else if (thisfragment.equals("No Groups")){
                    fragmentName = "No Groups";
                    fragment = new NoGroups();
                }
                break;
            case R.id.Logout:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
            case R.id.Groups:
                    fragment = new Groups();
                break;
            case R.id.CreateGroup:
                    fragment = new CreateGroup();
                break;
                case R.id.nogroupyet:
                    fragment = new NoGroups();
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_main, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
