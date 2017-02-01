package com.zconnect.login.zconnect.Phonebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.zconnect.login.zconnect.R;

public class Search extends AppCompatActivity {
    String textInput;
    DatabaseReference mdata;
    RecyclerView mView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_contacts);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final EditText mtext = (EditText) findViewById(R.id.editText);

        TextWatcher mTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInput = mtext.getText().toString().trim();
                listView();
            }

            public void afterTextChanged(Editable s) {
                textInput = mtext.getText().toString().trim();
                listView();

            }
        };
        mtext.addTextChangedListener(mTextWatcher);


    }

    void listView() {
        mView = (RecyclerView) findViewById(R.id.Rec);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mView.setLayoutManager(linearLayoutManager);

        mdata = FirebaseDatabase.getInstance().getReference("ZConnect").child("Phonebook");
        Toast.makeText(getApplicationContext(), mdata.toString(), Toast.LENGTH_LONG).show();
        mdata.keepSynced(true);
        Query mQuery = mdata.orderByChild("name").startAt(textInput);

        FirebaseRecyclerAdapter<Phonebook_search, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Phonebook_search, ViewHolder>(
                Phonebook_search.class,
                R.layout.phonebook,
                ViewHolder.class,
                mQuery) {


            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final Phonebook_search model, int position) {
                Toast.makeText(getApplicationContext(), "Populate " + textInput, Toast.LENGTH_SHORT).show();
                if (textInput != null) {
                    if (model.getName().toLowerCase().startsWith(textInput.toLowerCase())) {

                        viewHolder.setTitle(model.getName());
                        viewHolder.setAddress(model.getNumber());

                    } else {

                        viewHolder.destroy();
                    }


                    //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_SHORT);
                } else {
                    viewHolder.setTitle(model.getName());
                    viewHolder.setAddress(model.getNumber());
                }

            }

        };
        mView.setAdapter(firebaseRecyclerAdapter);

    }
}






