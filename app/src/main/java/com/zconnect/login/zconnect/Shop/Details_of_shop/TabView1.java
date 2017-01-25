package com.zconnect.login.zconnect.Shop.Details_of_shop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.zconnect.login.zconnect.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TabView1 extends Fragment {
    private static  DatabaseReference mDatabase ; //TODO add location
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static TabView1 newInstance(int sectionNumber, DatabaseReference mData) {
        TabView1 fragment = new TabView1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        mDatabase= mData;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_details_tab1, container, false);
        RecyclerView mView = (RecyclerView) rootView.findViewById(R.id.ImageWalaView);
        Toast.makeText(getApplicationContext(),"List wala ",Toast.LENGTH_SHORT).show();
        FirebaseRecyclerAdapter<model,ImageViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<model, ImageViewHolder>(

                model.class,
                R.layout.image_wala_view,
                ImageViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ImageViewHolder viewHolder, model model, int position) {

                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());

            }};
        mView.setAdapter(firebaseRecyclerAdapter);
        return rootView;
    }

}

