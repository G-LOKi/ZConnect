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
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddEvent extends AppCompatActivity {

    private Uri mImageUri = null;

    private ImageButton mAddImage;
    private Button mPostBtn;

    private EditText mEventName;
    private EditText mEventDescription;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private Button CalendarButton;
    private static final int GALLERY_REQUEST = 7;

    String eventDate;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mAddImage = (ImageButton)findViewById(R.id.imageButton);
        mEventName = (EditText)findViewById(R.id.EventName);
        mEventDescription = (EditText)findViewById(R.id.EventDescription);
        mPostBtn = (Button)findViewById(R.id.postButton);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events");

        CalendarButton = (Button)findViewById(R.id.Calender_button);

        mProgress = new ProgressDialog(this);

        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();
            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();
            }
        });



    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            eventDate = date.toString();
            Toast.makeText(AddEvent.this, eventDate, Toast.LENGTH_SHORT).show();
            dateString = String.valueOf((date.getYear()*10000)+(100*date.getMonth())+date.getDay());
        }
    };


    private void startPosting() {

        mProgress.setMessage("JccJc");
        mProgress.show();
        final String eventNameValue = mEventName.getText().toString().trim();
        final String eventDescriptionValue = mEventDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(eventNameValue) && !TextUtils.isEmpty(eventDescriptionValue)&& mImageUri!=null)
        {
      //1
            StorageReference filepath = mStorage.child("EventImage").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("EventName").setValue(eventNameValue);
                    newPost.child("EventDescription").setValue(eventDescriptionValue);
                    newPost.child("EventImage").setValue(downloadUri.toString());
                    newPost.child("EventDate").setValue(eventDate);
                    newPost.child("FormatDate").setValue(dateString);

                    mProgress.dismiss();
                    startActivity(new Intent(AddEvent.this,AllEvents.class));
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
