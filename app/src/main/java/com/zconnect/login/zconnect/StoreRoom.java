package com.zconnect.login.zconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;


public class StoreRoom extends AppCompatActivity {

    private RecyclerView mProductList;
    private DatabaseReference mDatabase;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProductList = (RecyclerView) findViewById(R.id.productList);
            mProductList.setHasFixedSize(true);
            mProductList.setLayoutManager(new LinearLayoutManager(this));

            mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreRoom.this, AddProduct.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Product,ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(

                Product.class,
                R.layout.products_row,
                ProductViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                viewHolder.setSwitch(model.getKey());
                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };
        mProductList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        private DatabaseReference ReserveReference;
        View mView;
        private Switch mReserve;
        private TextView ReserveStatus;
        private FirebaseAuth mAuth;
        String [] keyList;
        String ReservedUid;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setSwitch(String key)
        {

            ReserveStatus = (TextView) mView.findViewById(R.id.switch1);
            mReserve = (Switch) mView.findViewById(R.id.switch1);

            //set the switch to ON
            mReserve.setChecked(true);
            //attach a listener to check for changes in state
            mReserve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    ReserveReference = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    final String userId = user.getUid();

                    ReserveReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ReservedUid = (String)dataSnapshot.child(userId +"/Reserved").getValue();
                            keyList = ReservedUid.split("\\s");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    if(isChecked){
                        ReserveStatus.setText("Product Reserved");
                        List list = (List) Arrays.asList(keyList);
                        list.add(userId);
                        ReservedUid = TextUtils.join(", ", list);
                        DatabaseReference newPost = ReserveReference.child(userId + "Reserved");
                        newPost.setValue()


                    }else{
                        ReserveStatus.setText("Reserve Now");
                    }
                }
            });

            //check the current state before we display the screen
            if(mReserve.isChecked()){
                ReserveStatus.setText("Switch is currently ON");
            }
            else {
                ReserveStatus.setText("Switch is currently OFF");
            }

        };

        public void setProductName(String productName){

            TextView post_name = (TextView) mView.findViewById(R.id.productName);
            post_name.setText(productName);

        }

        public void setProductDesc(String productDesc){

            TextView post_desc = (TextView) mView.findViewById(R.id.productDescription);
            post_desc.setText(productDesc);

        }

        public void setImage(Context ctx,String image){


            ImageView post_image = (ImageView) mView.findViewById(R.id.postImg);
            Picasso.with(ctx).load(image).into(post_image);


        }

    }


}
