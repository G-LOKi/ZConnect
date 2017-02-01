package com.zconnect.login.zconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zconnect.login.zconnect.Phonebook_File.Phonebook;
import com.zconnect.login.zconnect.Phonebook_File.PhonebookDisplayItem;
import com.zconnect.login.zconnect.shop.categories.Shop;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayoutManager linearLayoutManager;
    RecyclerView mEverything;
    RelativeLayout mType12;
    LinearLayout mtype3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mEverything = (RecyclerView) findViewById(R.id.everything);
        mEverything.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mEverything.setLayoutManager(linearLayoutManager);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        makeRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        makeRecyclerView();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_infone) {
            Intent intent = new Intent(this, Phonebook.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_event) {
            Intent intent = new Intent(this, AllEvents.class);
            startActivity(intent);
        } else if (id == R.id.nav_storeroom) {
            Intent intent = new Intent(this, TabStoreRoom.class);
            startActivity(intent);
        } else if (id == R.id.nav_shop) {
            Intent intent = new Intent(this, Shop.class);
            startActivity(intent);
        } else if (id == R.id.nav_signout) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void makeRecyclerView() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ZConnect").child("everything");
        FirebaseRecyclerAdapter<Home_data, everythingViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Home_data, everythingViewHolder>(
                Home_data.class,
                R.layout.everything_row,
                everythingViewHolder.class,
                mDatabase) {


            @Override
            protected void populateViewHolder(everythingViewHolder viewHolder, Home_data model, int position) {


                if (model.getType() == 0) {

                    viewHolder.removeView();
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDescription(model.getDescription());
                    viewHolder.setImage(getApplicationContext(), model.getUrl());
                    Calendar cal = Calendar.getInstance();
                    viewHolder.setDate(model.getmultiUse2(), false, getApplicationContext());
                    viewHolder.makeButton(model.getTitle(), model.getDescription(), Long.parseLong(model.getmultiUse1()) - cal.getTimeInMillis());
                } else if (model.getType() == 1) {
                    viewHolder.removeView();
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDescription(model.getDescription());
                    viewHolder.setImage(getApplicationContext(), model.getUrl());
                    viewHolder.setDate(String.valueOf(model.getPhone_no()), true, getApplicationContext());


                } else if (model.getType() == 2) {

                    PhonebookDisplayItem displayItem = new PhonebookDisplayItem(model.getUrl(), model.getTitle(), model.getDescription(), String.valueOf(model.getPhone_no()), model.getmultiUse1(), model.getmultiUse2());
                    viewHolder.makeContactView(getApplicationContext(), displayItem);

                }
                //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_LONG);


            }


        };
        mEverything.setAdapter(firebaseRecyclerAdapter);
    }
}
