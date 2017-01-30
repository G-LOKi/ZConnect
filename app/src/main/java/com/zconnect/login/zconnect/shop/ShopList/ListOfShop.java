package com.zconnect.login.zconnect.shop.ShopList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zconnect.login.zconnect.R;

import static com.zconnect.login.zconnect.R.layout.card_shop;


public class ListOfShop extends AppCompatActivity {
    String CatName;
    private RecyclerView mStoreList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_of_shops);
        mStoreList = (RecyclerView) findViewById(R.id.StoreList);
        mStoreList.setHasFixedSize(true);
        Intent intent = getIntent();
        CatName = intent.getExtras().getString("Cat_name");
        mDatabase = FirebaseDatabase.getInstance().getReference("ZConnect/Shop/List").child(CatName);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mStoreList.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<shopList, shopListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<shopList, shopListViewHolder>(
                shopList.class,
                card_shop,
                shopListViewHolder.class,
                mDatabase) {


            @Override
            protected void populateViewHolder(final shopListViewHolder viewHolder, final shopList model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setPhn(model.getPhnNo(), getApplicationContext());
                viewHolder.setImage(model.getImage_Url(), model.getTitle());
                viewHolder.makeButton(model.getLattitude(), model.getLongitude(), getApplicationContext());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.call_detailsOfShops(model.getTitle());

                    }
                });
                //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_LONG);
            }


        };
        mStoreList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }


}
