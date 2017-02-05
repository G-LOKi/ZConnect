package com.zconnect.login.zconnect.Phonebook_File;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zconnect.login.zconnect.R;

import java.util.Vector;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class PhonebookHostelWise extends AppCompatActivity {
    String hostel;
    Vector<PhonebookItem> phonebookItems = new Vector<>();
    Vector<PhonebookDisplayItem> phonebookDisplayItems = new Vector<>();
    private PhonebookAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ZConnect").child("Phonebook").child("Student");
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_hostel_wise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
            getWindow().setStatusBarColor(colorPrimary);
            getWindow().setNavigationBarColor(colorPrimary);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        recyclerView = (RecyclerView) findViewById(R.id.content_phonebook_hostel_rv);
        progressBar = (ProgressBar) findViewById(R.id.content_phonebook_hostel_progress);

        //MAIN--------------------------------------------------------------------------------------

        //Keep databaseReference in sync even without needing to call valueEventListener
        databaseReference.keepSynced(true);

        hostel = getIntent().getStringExtra("Hostel");
        getSupportActionBar().setTitle(hostel + " Phonebook");
        //setHasFixedSize is used to optimise RV if we know for sure that this view's bounds do not
        // change with data
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        //Setup layout manager. VERY IMP ALWAYS
        adapter = new PhonebookAdapter(phonebookItems, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(VISIBLE);
                phonebookItems.clear();
                phonebookDisplayItems.clear();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {

                    phonebookDisplayItems.add(shot.getValue(PhonebookDisplayItem.class));
                }
                for (int i = 0; i < phonebookDisplayItems.size(); i++) {
                    if (phonebookDisplayItems.get(i).getCategory() != null && hostel != null && phonebookDisplayItems.get(i).getHostel().equals(hostel)) {
                        phonebookItems.add(new PhonebookItem(phonebookDisplayItems.get(i).getImageurl(), phonebookDisplayItems.get(i).getName(), phonebookDisplayItems.get(i).getNumber(), phonebookDisplayItems.get(i)));
                    }

                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(INVISIBLE);
            }
        });

    }
}