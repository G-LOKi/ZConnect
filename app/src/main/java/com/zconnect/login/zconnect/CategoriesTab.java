package com.zconnect.login.zconnect;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesTab extends Fragment {

    DatabaseReference mDatabase;
    private RecyclerView mProductList;



    public CategoriesTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_store_room, container, false);
        mProductList = (RecyclerView) view.findViewById(R.id.productList);
        mProductList.setHasFixedSize(true);
        mProductList.setLayoutManager(new LinearLayoutManager(getContext()));


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");

        Query mQueryCat = mDatabase.orderByChild("category").equalTo("");

        FirebaseRecyclerAdapter<Product, ReservedTab.ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ReservedTab.ProductViewHolder>(
                Product.class,
                R.layout.reserved_products_row,
                ReservedTab.ProductViewHolder.class,
                mQueryCat
        ) {
            @Override
            protected void populateViewHolder(ReservedTab.ProductViewHolder viewHolder, Product model, int position) {

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

        public void setProductName(String productName){

            TextView post_name = (TextView) mView.findViewById(R.id.productName);
            post_name.setText(productName);

        }

        public void setProductDesc(String productDesc){

            TextView post_desc = (TextView) mView.findViewById(R.id.productDescription);
            post_desc.setText(productDesc);

        }

        public void setImage(Context ctx, String image){


            ImageView post_image = (ImageView) mView.findViewById(R.id.postImg);
            Picasso.with(ctx).load(image).into(post_image);


        }

    }

}
