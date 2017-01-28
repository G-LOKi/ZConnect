package com.zconnect.login.zconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProduct extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 7;
    private Uri mImageUri = null;
    private ImageButton mAddImage;
    private Button mPostBtn;
    private EditText mProductName;
    private EditText mProductDescription;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsername;
    private ProgressDialog mProgress;
    private Spinner spinner1;
    private FirebaseAuth mAuth;
    private String sellerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mAddImage = (ImageButton)findViewById(R.id.imageButton);
        mProductName = (EditText)findViewById(R.id.nameOfProduct);
        mProductDescription = (EditText)findViewById(R.id.description);
        mPostBtn = (Button)findViewById(R.id.postButton);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();
            }
        });
    }

    private void startPosting() {

        mProgress.setMessage("JccJc");
        mProgress.show();
        final String productNameValue = mProductName.getText().toString().trim();
        final String productDescriptionValue = mProductDescription.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        mUsername = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");

        mUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sellerName = (String) dataSnapshot.child(userId).child("Username").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (!TextUtils.isEmpty(productNameValue) && !TextUtils.isEmpty(productDescriptionValue) && mImageUri != null)
        {
            StorageReference filepath = mStorage.child("ProductImage").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    String key = newPost.getKey();
                    newPost.child("Category").setValue(String.valueOf(spinner1.getSelectedItem()));
                    newPost.child("Key").setValue(key);
                    newPost.child("ProductName").setValue(productNameValue);
                    newPost.child("ProductDescription").setValue(productDescriptionValue);
                    newPost.child("Image").setValue(downloadUri.toString());
                    newPost.child("PostedBy").setValue(mAuth.getCurrentUser().getUid());
                    newPost.child("SellerUsername").setValue(sellerName);
                    mProgress.dismiss();
                    startActivity(new Intent(AddProduct.this,TabStoreRoom.class));
                }
            });
        }

     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();// takes image that the user added
            mAddImage.setImageURI(mImageUri);//sets the image to mAddImage button

        }
    }

}
