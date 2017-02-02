package com.zconnect.login.zconnect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lokesh Garg on 15-01-2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder{

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

    public void defaultSwitch(final String key)
    {
        ReserveStatus = (TextView) mView.findViewById(R.id.switch1);
        mReserve = (Switch) mView.findViewById(R.id.switch1);
        ReserveReference = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
//            Toast.makeText(mView.getContext(), userId, Toast.LENGTH_SHORT).show();

        ReserveReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReservedUid = (String)dataSnapshot.child(userId +"/Reserved").getValue();
//                    Toast.makeText(mView.getContext(), ReservedUid, Toast.LENGTH_SHORT).show();

//                    if(ReservedUid == null)
//                        ReservedUid = "   ";

                keyList = ReservedUid.split(" ");

                Toast.makeText(mView.getContext(), ReservedUid, Toast.LENGTH_SHORT).show();
                //check the current state before we display the screen
                List<String> list = new ArrayList<String>(Arrays.asList(keyList));
                if (list.contains(key))
                {
                    mReserve.setChecked(true);
                    Toast.makeText(mView.getContext(), "Contains Id", Toast.LENGTH_SHORT).show();
                }
                else {
                    mReserve.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void setSwitch(final String key)
    {
        ReserveStatus = (TextView) mView.findViewById(R.id.switch1);
        mReserve = (Switch) mView.findViewById(R.id.switch1);
        //set the switch to ON

        //attach a listener to check for changes in state
        ReserveReference = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        Toast.makeText(mView.getContext(), userId, Toast.LENGTH_SHORT).show();

        ReserveReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReservedUid = (String)dataSnapshot.child(userId +"/Reserved").getValue();
                Toast.makeText(mView.getContext(), ReservedUid, Toast.LENGTH_SHORT).show();

                if(ReservedUid == null)
                    ReservedUid = "   ";

                keyList = ReservedUid.split(" ");



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mReserve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(isChecked){
                    ReserveStatus.setText("Product Reserved");
                    Toast.makeText(mView.getContext(), key, Toast.LENGTH_SHORT).show();
                    List<String> list = new ArrayList<String>(Arrays.asList(keyList));
                    if (!list.contains(key))
                        list.add(key);

                    ReservedUid = TextUtils.join(" ", list);

                    Toast.makeText(mView.getContext(),ReservedUid, Toast.LENGTH_SHORT).show();
                    DatabaseReference newPost = ReserveReference.child(userId);
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("Reserved", ReservedUid);
                    newPost.updateChildren(childUpdates);



                } else{
                    ReserveStatus.setText("Reserve Now");
                    List<String> list = new ArrayList<String>(Arrays.asList(keyList));
//                        //remove
                    list.remove(key);

                    ReservedUid = TextUtils.join(" ", list);
                    DatabaseReference newPost = ReserveReference.child(userId);
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("Reserved", ReservedUid);
                    newPost.updateChildren(childUpdates);
                }


            }
        });




    };


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
