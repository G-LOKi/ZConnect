package com.zconnect.login.zconnect.Phonebook_File;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zconnect.login.zconnect.R;

import java.util.Vector;

public class PhonebookSearch extends AppCompatActivity {

    private android.support.v7.widget.RecyclerView searchEventList;
    private PhonebookAdapter searchAdapter;
    private Toolbar toolbar;
    private TextView errorMessage;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Phonebook");
    private Vector<PhonebookItem> searchContact = new Vector<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_phonebook, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchQuery(query);
                } else {
                    searchQuery("");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchQuery(newText);
                } else {
                    searchQuery("");
                }
                return false;
            }
        });

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        Log.v("tag", "s");
        setContentView(R.layout.activity_phonebook_search);

        errorMessage = (TextView) findViewById(R.id.errorMessage);

        Intent intent = getIntent();


        searchEventList = (android.support.v7.widget.RecyclerView) findViewById(R.id.searchActivityRecyclerView);
        databaseReference.keepSynced(true);
        searchAdapter = new PhonebookAdapter(searchContact, PhonebookSearch.this);

        searchEventList.setHasFixedSize(true);
        searchEventList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        //Setup layout manager. VERY IMP ALWAYS
        searchEventList.setAdapter(searchAdapter);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


            handleIntent(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {


        final String query = intent.getStringExtra(SearchManager.QUERY);
        Log.v("Query obtained", query);
        searchQuery(query);

    }

    private void searchQuery(String string) {
        final String query = string.trim();


        if (query != "") {

            errorMessage.setVisibility(View.INVISIBLE);
            searchEventList.setVisibility(View.VISIBLE);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    searchContact.clear();

                    for (DataSnapshot childShot : dataSnapshot.getChildren()) {

                        //need to add a try catch block before release

                        // Storage of EventFormat by individual de-serialization

                        PhonebookDisplayItem foo = new PhonebookDisplayItem();

                        foo.setCategory(childShot.child("category").getValue(String.class));
                        foo.setDesc(childShot.child("desc").getValue(String.class));
                        foo.setEmail(childShot.child("email").getValue(String.class));
                        foo.setImageurl(childShot.child("imageurl").getValue(String.class));
                        foo.setName(childShot.child("name").getValue(String.class));
                        foo.setNumber(childShot.child("number").getValue(String.class));


                        String title = childShot.child("name").getValue(String.class);


                        String name = childShot.child("name").getValue(String.class);
                        String number = childShot.child("number").getValue(String.class);

                        String imageurl = childShot.child("imageurl").getValue(String.class);

                        String typeTemp = "";

                        //Type 1 and 2 check

                        if (title.toLowerCase().contains(query.toLowerCase())) {


                            PhonebookItem temp = new PhonebookItem(imageurl, name, number, foo);
                            searchContact.add(temp);

                        } else {
                            String wordSplit[] = title.split(" ");
                            int l = wordSplit.length;
                            for (int i = 0; i < l; i++) {

                                String t = wordSplit[i].toLowerCase();

                                String m = query.toLowerCase();
                                if (t.contains(m)) {

                                    PhonebookItem temp = new PhonebookItem(imageurl, name, number, foo);
                                    searchContact.add(temp);
                                }
                            }
                        }


                    }

                    // Need to add empty search result log message
                    if (searchContact.isEmpty()) {
                        errorMessage.setVisibility(View.VISIBLE);
                        searchEventList.setVisibility(View.INVISIBLE);

                    } else {

                        searchEventList.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            searchContact.clear();
            searchAdapter.notifyDataSetChanged();
            errorMessage.setVisibility(View.VISIBLE);
            searchEventList.setVisibility(View.INVISIBLE);


        }
    }


}
