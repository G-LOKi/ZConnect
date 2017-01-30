package com.zconnect.login.zconnect.shop.ShopList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zconnect.login.zconnect.R;

import static com.zconnect.login.zconnect.R.layout.card_shop;


public class ListOfShop extends AppCompatActivity {
    String ShopName;
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
        //Toast.makeText(getApplicationContext(),intent.getExtras().getString("Cat_name"),Toast.LENGTH_LONG).show();
        mDatabase = FirebaseDatabase.getInstance().getReference("ZConnect/Shop/List").child(intent.getExtras().getString("Cat_name"));
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
            protected void populateViewHolder(shopListViewHolder viewHolder, final shopList model, int position) {
                ShopName = model.getFirebaseUID();
                viewHolder.setTitle(ShopName);
                viewHolder.setAddress(model.getAddress());
                viewHolder.setPhn(model.getPhnNo(), getApplicationContext());

                viewHolder.setImage(getApplicationContext(), model.getImage_Url(), model.getTitle());
                viewHolder.makeButton(model.getLattitude(), model.getLongitude(), getApplicationContext());

                //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_LONG);
                viewHolder.createClicklistener(ShopName);
            }


        };
        mStoreList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }


}
