package com.zconnect.login.zconnect;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Post_activity extends AppCompatActivity {
    public static final int GALLERY_REQUEST = 1;
    final Calendar c = Calendar.getInstance();
    int day , month, hour, minute;
    int year;
    EditText mTitle , mDesc;
    TimePickerDialog timePickerDialog ;
    DatePickerDialog datepickerDialog;
    DatabaseReference mDatabase;
    ImageView imageView;
    Uri imageUri=null;
    TextView dateTV;
    TextView TimeTV;
    Intent galleryIntent;
    Context context = this;
    String Title , Desc;
    private ProgressDialog pBar;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour= c.get(Calendar.HOUR);
        minute=c.get(Calendar.MONTH);
        imageView = (ImageView) findViewById(R.id.imageView) ;
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        dateTV = (TextView) findViewById(R.id.datebtn);
        TimeTV = (TextView) findViewById(R.id.timebtn);
        dateTV.setText(day+"/"+month+"/"+year);
        TimeTV.setText(hour+":"+minute);
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);


                        }
        });


        //Time Selection
        {
            findViewById(R.id.timebtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    timePickerDialog = new TimePickerDialog(context,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minuteofDay) {
                                    hour = hourOfDay;
                                    minute = minuteofDay;

                                }


                            }, 10, 10, true);
                    timePickerDialog.show();
                    TimeTV.setText(hour+":"+minute);
                }
            });
        }

        //Date Selection

        {
            findViewById(R.id.datebtn).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

                    datepickerDialog = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int yearob,
                                                      int monthOfYear, int dayOfMonth) {
                                    year = yearob;
                                    month = monthOfYear;
                                    day = dayOfMonth;
                                }


                            }, year, month, day);
                    datepickerDialog.show();
                    dateTV.setText(day+"/"+month+"/"+year);



                }
            });
        }

        pBar=new ProgressDialog(this);
        mTitle= (EditText)findViewById(R.id.eventName);
        mDesc= (EditText)findViewById(R.id.eventDetails);
        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

private void startPosting() {

        Title= mTitle.getText().toString().trim();
        Desc= mDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(Title)&&!TextUtils.isEmpty(Desc)) {
            pBar.setMessage("Posting Event....");
            pBar.show();

            StorageReference filepath = mStorage.child("Event Images").child(Title+year+""+month+""+day+""+hour+""+minute);
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadURL =taskSnapshot.getDownloadUrl();
                    DatabaseReference newEvent = mDatabase.push();
                    newEvent.child("Title").setValue(Title);
                    newEvent.child("Description").setValue(Desc);
                    newEvent.child("Day").setValue(day);
                    newEvent.child("Month").setValue(month);
                    newEvent.child("Year").setValue(year);
                    newEvent.child("Hour").setValue(hour);
                    newEvent.child("Minute").setValue(minute);
                    newEvent.child("Image_Url").setValue(downloadURL.toString());



                }
            });


           new Handler().postDelayed(new Runnable() {

               @Override
               public void run() {
                    pBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_LONG).show();

               }
          }, 2000);
        }

        else
        {

            Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
}}