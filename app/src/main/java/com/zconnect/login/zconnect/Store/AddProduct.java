package com.zconnect.login.zconnect.Store;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zconnect.login.zconnect.R;

public class AddProduct extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 7;
    private Uri mImageUri = null;
    private ImageButton mAddImage;
    private Button mPostBtn;
    private EditText mProductName;
    private EditText mProductDescription;
    private EditText mProductPrice;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mAddImage = (ImageButton)findViewById(R.id.imageButton);
        mProductName = (EditText)findViewById(R.id.nameOfProduct);
        mProductDescription = (EditText)findViewById(R.id.description);
        mProductPrice = (EditText)findViewById(R.id.PriceColoumn);
        mPostBtn = (Button)findViewById(R.id.postButton);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");

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
        final String productPrice = mProductPrice.getText().toString().trim();

        if(!TextUtils.isEmpty(productNameValue) && !TextUtils.isEmpty(productDescriptionValue)&& mImageUri!=null)
        {
            StorageReference filepath = mStorage.child("ProductImage").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    { DatabaseReference newPost = mDatabase.push();
                    newPost.child("ProductName").setValue(productNameValue);
                    newPost.child("ProductDescription").setValue(productDescriptionValue);
                    newPost.child("Image").setValue(downloadUri.toString());
                        newPost.child("Price").setValue(productPrice);
                    }
                    {//In Everything
                        DatabaseReference newEvent = mDatabase.child("Everything").push();
                        newEvent.child("type").setValue(0);
                        newEvent.child("Title").setValue(productNameValue);
                        newEvent.child("Description").setValue(productPrice);
                        newEvent.child("Day").setValue(null);
                        newEvent.child("Month").setValue(null);
                        newEvent.child("Year").setValue(null);
                        newEvent.child("Hour").setValue(null);
                        newEvent.child("Minute").setValue(null);
                        newEvent.child("Image_Url").setValue(downloadUri.toString());
                    }

                    mProgress.dismiss();
                    startActivity(new Intent(AddProduct.this,StoreRoom.class));
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
