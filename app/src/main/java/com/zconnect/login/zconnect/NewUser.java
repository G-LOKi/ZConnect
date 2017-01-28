package com.zconnect.login.zconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class NewUser extends AppCompatActivity {

    public static final int SELECT_PICTURE = 1;
    private DatabaseReference mDatabase;
    private DatabaseReference mPhonebook;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private EditText mUserName;
    private EditText mPhoneNumber;
    private EditText mDesc;
    private EditText mEmail;
    private Button save;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private String category;
    private RadioButton radioButtonS, radioButtonA, radioButtonO;
    private ImageView mImage;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        mPhonebook = FirebaseDatabase.getInstance().getReference().child("ZConnect/Phonebook");
        mUserName = (EditText)findViewById(R.id.Username);
        mPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        mEmail = (EditText) findViewById(R.id.email);
        mDesc = (EditText) findViewById(R.id.desc);
        mProgress = new ProgressDialog(this);
        save = (Button)findViewById(R.id.Save);
        mImage = (ImageView) findViewById(R.id.contact_image);


        radioButtonS = (RadioButton) findViewById(R.id.radioButton);
        radioButtonA = (RadioButton) findViewById(R.id.radioButton2);
        radioButtonO = (RadioButton) findViewById(R.id.radioButton3);
        radioButtonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "S";
            }
        });

        radioButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "A";
            }
        });


        radioButtonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "O";
            }
        });

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Picasso.with(NewUser.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.contact_image));
            }
            imageUri = data.getData();
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void startPosting() {

        mProgress.setMessage("Welcome...");
        mProgress.show();
        final String Username = mUserName.getText().toString().trim();
        final String PhoneNumber = mPhoneNumber.getText().toString().trim();
        final String desc = mDesc.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String category = this.category;
            //StorageReference filepath = mStorage.child("ProductImage").child(mImageUri.getLastPathSegment());
            //filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//                    DatabaseReference newPost = mDatabase.child(userId);
//                    Map<String, Object> childUpdates = new HashMap<>();
//                    childUpdates.put("UserName", mUserName);
//                    newPost.updateChildren(childUpdates);


            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            final String userId = user.getUid();

//            if (mUserName != null && mPhoneNumber != null && mEmail != null && mDesc != null && category != null && imageUri != null) {
        if (mUserName != null && category != null && imageUri != null) {
            StorageReference filepath = mStorage.child("PhonebookImage").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    //posts to ZConnect/Users/child/
                    DatabaseReference newPost = mDatabase.child(userId);
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("Username", Username);
                    childUpdates.put("PhoneNumber", PhoneNumber);
                    newPost.updateChildren(childUpdates);
                    //posts to ZConnect/Phonebook/child
                    newPost = mPhonebook.push();
                    newPost.child("name").setValue(Username);
                    newPost.child("number").setValue(PhoneNumber);
                    newPost.child("UserId").setValue(userId);
                    newPost.child("desc").setValue(desc);
                    newPost.child("category").setValue(category);
                    newPost.child("email").setValue(email);
                    newPost.child("imageurl").setValue(downloadUri.toString());
                }
            });
            startActivity(new Intent(NewUser.this, home.class));


            //old code
//                    HashMap<String, String> map1 = new HashMap<>();
//                    map1.put("Username", Username);
//                    map1.put("PhoneNumber", PhoneNumber);
//                    mDatabase.setValue(map1);

//                }
            //    });mProgress.dismiss();

        } else {
            Snackbar snack = Snackbar.make(save, "Fields are empty. Can't add contact. Name and category are mandatory", Snackbar.LENGTH_LONG);
            TextView snackBarText = (TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text);
            snackBarText.setTextColor(Color.WHITE);
            snack.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue500));
            snack.show();
            mProgress.dismiss();
        }

    }
}
