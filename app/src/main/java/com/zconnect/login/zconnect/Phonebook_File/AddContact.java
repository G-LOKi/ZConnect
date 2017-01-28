package com.zconnect.login.zconnect.Phonebook_File;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zconnect.login.zconnect.R;

public class AddContact extends AppCompatActivity {
    public static final int SELECT_PICTURE = 1;
    private static final int GALLERY_REQUEST = 7;
    ImageView image;
    Uri imageuri;
    private android.support.design.widget.TextInputEditText editTextName;
    private android.support.design.widget.TextInputEditText editTextEmail;
    private android.support.design.widget.TextInputEditText editTextDetails;
    private android.support.design.widget.TextInputEditText editTextNumber;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ZConnect").child("Phonebook");
    private ProgressDialog mProgress;
    private RadioButton radioButtonS, radioButtonA, radioButtonO;
    private String name, email, details, number, category = null, imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mProgress = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
            getWindow().setStatusBarColor(colorPrimary);
            getWindow().setNavigationBarColor(colorPrimary);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        editTextDetails = (TextInputEditText) findViewById(R.id.contact_details_editText);
        editTextEmail = (TextInputEditText) findViewById(R.id.contact_email_editText);
        editTextName = (TextInputEditText) findViewById(R.id.contact_name_editText);
        editTextNumber = (TextInputEditText) findViewById(R.id.contact_number_editText);
        image = (ImageView) findViewById(R.id.contact_image);
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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
//        imageurl=imageuri.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Picasso.with(AddContact.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.contact_image));
            }
            imageuri = data.getData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            if (!isNetworkAvailable(getApplicationContext())) {

                Snackbar snack = Snackbar.make(editTextDetails, "No Internet. Can't Add Contact.", Snackbar.LENGTH_LONG);
                TextView snackBarText = (TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text);
                snackBarText.setTextColor(Color.WHITE);
                snack.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue500));
                snack.show();

            } else {
                startposting();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void startposting() {
        mProgress.setMessage("Adding...");
        mProgress.show();
        name = editTextName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        details = editTextDetails.getText().toString().trim();
        number = editTextNumber.getText().toString().trim();
//        imageurl = imageuri.toString();
        if (name != null && number != null && email != null && details != null && category != null && imageuri != null) {
            StorageReference filepath = mStorage.child("PhonebookImage").child(imageuri.getLastPathSegment());
            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = ref.push();
                    // String key = newPost.getKey();
                    newPost.child("name").setValue(name);
                    newPost.child("desc").setValue(details);
                    newPost.child("imageurl").setValue(downloadUri.toString());
                    newPost.child("number").setValue(number);
                    newPost.child("category").setValue(category);
                    newPost.child("email").setValue(email);

                    mProgress.dismiss();
                    startActivity(new Intent(AddContact.this, Phonebook.class));
                }
            });
        } else {
            Snackbar snack = Snackbar.make(editTextDetails, "Fields are empty. Can't add contact.", Snackbar.LENGTH_LONG);
            TextView snackBarText = (TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text);
            snackBarText.setTextColor(Color.WHITE);
            snack.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue500));
            snack.show();
            mProgress.dismiss();
        }


    }
}