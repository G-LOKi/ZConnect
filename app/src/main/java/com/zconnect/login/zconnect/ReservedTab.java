package com.zconnect.login.zconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReservedTab extends Fragment {

    String reserveString;
    Query query;
    private DatabaseReference mReservedProducts;
    private DatabaseReference mDatabase;
    private RecyclerView mProductList;
    private List<String> reserveList;
    private FirebaseAuth mAuth;

    public ReservedTab() {
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

        mReservedProducts = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        query = mDatabase.orderByChild("UsersReserved/" + userId).equalTo(user.getDisplayName());

//        mReservedProducts.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                reserveString = (String)dataSnapshot.child(userId +"/Reserved").getValue();
//                reserveList = new ArrayList<String>(Arrays.asList(reserveString.split(" ")));
////                Toast.makeText(getContext(), reserveString, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.reserved_products_row,
                ProductViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final ProductViewHolder viewHolder, Product model, int position) {

                final String product_key = getRef(position).getKey();

//               if(reserveList.contains(model.getKey())) {
                viewHolder.setProductName(model.getProductName());
                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());
//                }else {
//               }
                viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        viewHolder.ReserveReference = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom/" + product_key + "/UsersReserved");

                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        final String userId = user.getUid();
                        viewHolder.ReserveReference.child(userId).removeValue();
                    }
                });

            }
        };
        mProductList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {


        View mView;
        String[] keyList;
        String ReservedUid;
        private DatabaseReference ReserveReference;
        private Switch mReserve;
        private TextView ReserveStatus;
        private ImageButton deleteButton;
        private FirebaseAuth mAuth;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            //to delete reserved items
            deleteButton = (ImageButton) mView.findViewById(R.id.delete);
        }

        public void setProductName(String productName) {

            TextView post_name = (TextView) mView.findViewById(R.id.productName);
            post_name.setText(productName);

        }

        public void setProductDesc(String productDesc) {

            TextView post_desc = (TextView) mView.findViewById(R.id.productDescription);
            post_desc.setText(productDesc);

        }

        public void setImage(Context ctx, String image) {


            ImageView post_image = (ImageView) mView.findViewById(R.id.postImg);
            Picasso.with(ctx).load(image).into(post_image);


        }

    }

}


