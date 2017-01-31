package com.zconnect.login.zconnect.shop.Details_of_shop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.zconnect.login.zconnect.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TabView2 extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static DatabaseReference mDatabase; //TODO add location
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;

    public static TabView2 newInstance(int sectionNumber, DatabaseReference mData) {
        TabView2 fragment = new TabView2();
        Bundle args = new Bundle();
        mDatabase = mData;
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.shop_details_tab2, container, false);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.ImageWalaView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        FirebaseRecyclerAdapter<modelData, ImageViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelData, ImageViewHolder>(

                modelData.class,
                R.layout.image_wala_view,
                ImageViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ImageViewHolder viewHolder, modelData modeldata, int position) {
                viewHolder.setProductDesc(modeldata.getDesc());
                viewHolder.setImage(getApplicationContext(), modeldata.getUl());
                viewHolder.setProductTitle(modeldata.getTitl());

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        return rootView;
    }

}

