package com.zconnect.login.zconnect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservedTab extends Fragment {

    private DatabaseReference mReservedProducts;
    private RecyclerView mProductList;



    public ReservedTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserved_tab, container, false);

        mReservedProducts = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users/Reserved");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Product,StoreRoom.ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, StoreRoom.ProductViewHolder>(

                Product.class,
                R.layout.products_row,
                StoreRoom.ProductViewHolder.class,
                mReservedProducts
        ) {

            @Override
            protected void populateViewHolder(StoreRoom.ProductViewHolder viewHolder, Product model, int position) {


                viewHolder.defaultSwitch(model.getKey());
                viewHolder.setSwitch(model.getKey());
                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());

            }
        };
        mProductList.setAdapter(firebaseRecyclerAdapter);
    }


}


