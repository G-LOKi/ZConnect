package com.zconnect.login.zconnect;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

public class ProductsTab extends Fragment {


    private RecyclerView mProductList;
    private DatabaseReference mDatabase;
    private boolean flag=false;
    private FirebaseAuth mAuth;


    public ProductsTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_store_room, container, false);

        mProductList = (RecyclerView) view.findViewById(R.id.productList);
        mProductList.setHasFixedSize(true);
        mProductList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();

        // StoreRoom feature Reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");
        mDatabase.keepSynced(true);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Firebase predefined Recycler Adapter
        FirebaseRecyclerAdapter<Product,ProductsTab.ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductsTab.ProductViewHolder>(

                Product.class,
                R.layout.products_row,
                ProductsTab.ProductViewHolder.class,
                mDatabase
        ) {

            @Override
            protected void populateViewHolder(ProductsTab.ProductViewHolder viewHolder, final Product model, int position) {
                viewHolder.defaultSwitch(model.getKey());
                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mListener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        flag = true;

                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (flag) {
                                    if (dataSnapshot.child(model.getKey()).child("UsersReserved").hasChild(mAuth.getCurrentUser().getUid())) {
                                        mDatabase.child(model.getKey()).child("UsersReserved").child(mAuth.getCurrentUser().getUid()).removeValue();
                                        flag = false;
                                    } else {

                                        mDatabase.child(model.getKey()).child("UsersReserved").child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getDisplayName());
                                        flag = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                };
                viewHolder.mReserve.setOnCheckedChangeListener(viewHolder.mListener);
            }
        };
        mProductList.setAdapter(firebaseRecyclerAdapter);
    }

    // Each View Holder Class
    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        View mView;

        private DatabaseReference Users = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        private DatabaseReference StoreRoom= FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");
        private DatabaseReference CurrentProduct;

        //Switch View
        Switch mReserve;
        TextView ReserveStatus;

        public CompoundButton.OnCheckedChangeListener mListener;


        // Auth to get Current User
        private FirebaseAuth mAuth;

        // Flag Variable to get each Reserve Id
        String [] keyList;

        // Flag to get combined user Id
        String ReservedUid;

        // Constructor
        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mReserve = (Switch)mView.findViewById(R.id.switch1);
            ReserveStatus = (TextView)mView.findViewById(R.id.switch1);
            StoreRoom.keepSynced(true);
        }

        // Setting default switch
        public void defaultSwitch(final String key)
        {
            // Getting User ID
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            final String userId = user.getUid();

            //Getting  data from database
            StoreRoom.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mReserve.setOnCheckedChangeListener(null);
                    if(dataSnapshot.child(key).child("UsersReserved").hasChild(userId))
                    {
                        mReserve.setChecked(true);
                    }
                    else{
                        mReserve.setChecked(false);
                    }
                    mReserve.setOnCheckedChangeListener(mListener);
//                    ReservedUid = (String)dataSnapshot.child(userId +"/Reserved").getValue();
//
////                    if(ReservedUid == null)
////                        ReservedUid = "   ";
//
//                    keyList = ReservedUid.split(" ");
//
//                    // Toast.makeText(mView.getContext(), ReservedUid, Toast.LENGTH_SHORT).show();
//                    //check the current state before we display the screen
//                    List<String> list = new ArrayList<String>(Arrays.asList(keyList));
//                    if (list.contains(key))
//                    {
//                        mReserve.setChecked(true);
//                        //Toast.makeText(mView.getContext(), "Contains Id", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        mReserve.setChecked(false);
//                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


        }
//        public void setSwitch(final String key)
//        {
//            // Switch Variables initialization
//            ReserveStatus = (TextView) mView.findViewById(R.id.switch1);
//            mReserve = (Switch) mView.findViewById(R.id.switch1);
//
//            //attach a listener to check for changes in state
//            CurrentProduct = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom/"+key+"/UsersReserved");
//            mAuth = FirebaseAuth.getInstance();
//            FirebaseUser user = mAuth.getCurrentUser();
//            final String userId = user.getUid();
//            //Toast.makeText(mView.getContext(), userId, Toast.LENGTH_SHORT).show();
//
//            Users.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ReservedUid = (String)dataSnapshot.child(userId +"/Reserved").getValue();
//                    //Toast.makeText(mView.getContext(), ReservedUid, Toast.LENGTH_SHORT).show();
//
//                    if(ReservedUid == null)
//                        ReservedUid = "   ";
//
//                    keyList = ReservedUid.split(" ");
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//            mReserve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        ReserveStatus.setText("Product Reserved");
//                        //Toast.makeText(mView.getContext(), key, Toast.LENGTH_SHORT).show();
////                        List<String> list = new ArrayList<String>(Arrays.asList(keyList));
////                        if (!list.contains(key))
////                            list.add(key);
////
////                        ReservedUid = TextUtils.join(" ", list);
////
////                        //Toast.makeText(mView.getContext(),ReservedUid, Toast.LENGTH_SHORT).show();
////                        DatabaseReference newPost = Users.child(userId);
////                        Map<String, Object> childUpdates = new HashMap<>();
////                        childUpdates.put("Reserved", ReservedUid);
////                        newPost.updateChildren(childUpdates);
//
//                        CurrentProduct.child(userId).getRef().removeValue();
////                        Query query = CurrentProduct.orderByChild(userId).equalTo(userId);
////                        query.addListenerForSingleValueEvent(new ValueEventListener() {
////                            @Override
////                            public void onDataChange(DataSnapshot dataSnapshot) {
////                                dataSnapshot.getRef().removeValue();
////                            }
////
////                            @Override
////                            public void onCancelled(DatabaseError databaseError) {
////                                Log.e(TAG, "onCancelled", databaseError.toException());
////                            }
////                        });
//                    } else{
//
//                        ReserveStatus.setText("Reserve Now");
////                        List<String> list = new ArrayList<String>(Arrays.asList(keyList));
////                        list.remove(key);
////                        ReservedUid = TextUtils.join(" ", list);
////                        DatabaseReference newPost = Users.child(userId);
////                        Map<String, Object> childUpdates = new HashMap<>();
////                        childUpdates.put("Reserved", ReservedUid);
////                        newPost.updateChildren(childUpdates);
//                        HashMap<String, String> map1 = new HashMap<>();
//                        map1.put(userId,userId);
//                        CurrentProduct.setValue(map1);
//
//                    }
//                }
//            });
//        };

        //Set name of product
        public void setProductName(String productName){

            TextView post_name = (TextView) mView.findViewById(R.id.productName);
            post_name.setText(productName);

        }
        //Set Product Description
        public void setProductDesc(String productDesc){

            TextView post_desc = (TextView) mView.findViewById(R.id.productDescription);
            post_desc.setText(productDesc);

        }
        //Set Product Image
        public void setImage(Context ctx, String image){

            ImageView post_image = (ImageView) mView.findViewById(R.id.postImg);
            Picasso.with(ctx).load(image).into(post_image);

        }

    }

}
