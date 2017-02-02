package com.zconnect.login.zconnect.shop.Details_of_shop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TabView1 extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static DatabaseReference mDatabase;
    TextView mNameView, mDescription;
    ImageView mShopOffer;
    View rootView;

    public static TabView1 newInstance(int sectionNumber, DatabaseReference mData) {
        TabView1 fragment = new TabView1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        mDatabase = mData;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.shop_details_tab1, container, false);
        mNameView = (TextView) rootView.findViewById(R.id.Name_Shop_offer);
        mDescription = (TextView) rootView.findViewById(R.id.Description_shop_offer);
        mShopOffer = (ImageView) rootView.findViewById(R.id.image_Shop_offer);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                modelData details = dataSnapshot.getValue(modelData.class);
                CreateDetailTab(details.getTitl(), details.getDesc(), details.getUl());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        return rootView;

    }

    public void CreateDetailTab(String Title, String Disc, String url) {
        mNameView.setText(Title);
        mDescription.setText(Disc);
        Picasso.with(getApplicationContext()).load(url).into(mShopOffer);
    }


}


