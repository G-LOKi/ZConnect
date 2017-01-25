package com.zconnect.login.zconnect.Shop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zconnect.login.zconnect.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ListWalaView extends Fragment {
    private static  DatabaseReference mDatabase ; //TODO add location
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static ListWalaView newInstance(int sectionNumber,DatabaseReference mData) {
        ListWalaView fragment = new ListWalaView();
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
        FirebaseRecyclerAdapter<ImageWaliClass,ImageViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ImageWaliClass, ImageViewHolder>(

                ImageWaliClass.class,
                R.layout.image_wala_view,
                ImageViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ImageViewHolder viewHolder, ImageWaliClass model, int position) {

                viewHolder.setProductDesc(model.getProductDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());

            }};
        mView.setAdapter(firebaseRecyclerAdapter);
        return rootView;
    }

}

