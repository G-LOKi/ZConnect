package com.zconnect.login.zconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class NewUser extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mPhonebook;
    private EditText mUserName;
    private EditText mPhoneNumber;
    private Button save;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Users");
        mPhonebook = FirebaseDatabase.getInstance().getReference().child("ZConnect/Phonebook");
        mUserName = (EditText)findViewById(R.id.Username);
        mPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        mProgress = new ProgressDialog(this);
        save = (Button)findViewById(R.id.Save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
                startActivity(new Intent(NewUser.this, home.class));
            }
        });
    }

    private void startPosting() {

        mProgress.setMessage("Welcome");
        mProgress.show();
        final String Username = mUserName.getText().toString().trim();
        final String PhoneNumber = mPhoneNumber.getText().toString().trim();

        if(!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(PhoneNumber))
        {
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
            //posts to ZConnect/Users/child/
            DatabaseReference newPost = mDatabase.child(userId);
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("Username", Username);
            childUpdates.put("PhoneNumber", PhoneNumber);
            newPost.updateChildren(childUpdates);

            //posts to ZConnect/Phonebook/child
            newPost = mPhonebook.push();
            newPost.child("UserName").setValue(Username);
            newPost.child("PhoneNumber").setValue(PhoneNumber);
            newPost.child("User Id").setValue(userId);

            //old code
//                    HashMap<String, String> map1 = new HashMap<>();
//                    map1.put("Username", Username);
//                    map1.put("PhoneNumber", PhoneNumber);
//                    mDatabase.setValue(map1);
                    mProgress.dismiss();
                    startActivity(new Intent(NewUser.this,home.class));
//                }
        //    });
        }

    }
}
