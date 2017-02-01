package com.zconnect.login.zconnect.shop.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zconnect.login.zconnect.R;
import com.zconnect.login.zconnect.shop.ShopList.ListOfShop;

import java.util.ArrayList;
import java.util.Map;

public class Shop extends AppCompatActivity {
    GridView gv;
    ArrayAdapter<String> adapter;
    ArrayList<String> mCategoryName = new ArrayList<>();
    ArrayList<String> mCategoryUrl = new ArrayList<>();
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        gv = (GridView) findViewById(R.id.gv);
        db = FirebaseDatabase.getInstance().getReference("ZConnect/Shop").child("categories");
        retrieve();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, mCategoryName);
        gv.setAdapter(adapter);


    }

    public void drawGV() {
        Adapter adapter = new Adapter(getApplicationContext(), mCategoryUrl.toArray(new String[mCategoryUrl.size()]), mCategoryName.toArray(new String[mCategoryName.size()]));
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListOfShop.class);
                intent.putExtra("Cat_name", mCategoryName.get(position));
                startActivity(intent);
            }

        });
        //Toast.makeText(getApplicationContext(),mCategoryName.get(1),Toast.LENGTH_LONG);

    }

    public void retrieve() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Mcategories((Map<String, Object>) dataSnapshot.getValue());
                drawGV();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void Mcategories(Map<String, Object> Cat) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : Cat.entrySet()) {

            //Get user map
            Map singleCat = (Map) entry.getValue();
            //Get phone field and append st list
            mCategoryName.add((String) singleCat.get("name"));
            mCategoryUrl.add((String) singleCat.get("url"));
            drawGV();

        }
    }

}

