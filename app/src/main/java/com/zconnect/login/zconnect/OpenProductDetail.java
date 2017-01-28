package com.zconnect.login.zconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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

public class OpenProductDetail extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsername;
    private TextView productName;
    private TextView productDescription;
    private TextView sellerName;
    private TextView sellerNumber;
    private ImageView productImage;
    private String temporaryVariable;
    private String value;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_product_detail);

        productName = (TextView) findViewById(R.id.productName);
        productDescription = (TextView) findViewById(R.id.productDescription);
        sellerName = (TextView) findViewById(R.id.sellerName);
        productImage = (ImageView) findViewById(R.id.productImage);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        value = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key").toString();
            //The key argument here must match that used in the other activity
//            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        }
        temporaryVariable = value;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                final String userId = user.getUid();
                String productName = (String) snapshot.child("storeroom").child(value).child("ProductName").getValue();  //prints "Do you have data? You'll love Firebase."
                String imageUri = (String) snapshot.child("storeroom").child(value).child("Image").getValue();
                String productDescription = (String) snapshot.child("storeroom").child(value).child("ProductDescription").getValue();
                String sellerName = (String) snapshot.child("Users").child(userId).child("Username").getValue();


                Toast.makeText(OpenProductDetail.this, productName, Toast.LENGTH_SHORT).show();

                Picasso.with(OpenProductDetail.this).load(imageUri).into(productImage);
                OpenProductDetail.this.productName.setText(productName);
                OpenProductDetail.this.productDescription.setText(productDescription);
                OpenProductDetail.this.sellerName.setText(sellerName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

//
//        mUsername = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
//        String value2 = temporaryVariable;
//
//        mUsername.child(value2).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String sellerName = (String) dataSnapshot.child("UserName").getValue();
//                OpenProductDetail.this.sellerName.setText(sellerName);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


}
