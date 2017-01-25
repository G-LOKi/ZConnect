package com.zconnect.login.zconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OpenEventDetail extends AppCompatActivity {
    DatabaseReference mDatabase;
    TextView Event;
    ImageView EventImage;
    TextView EventDescription;
    TextView EventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_event_detail2);

        Event = (TextView) findViewById(R.id.event);
        EventImage = (ImageView) findViewById(R.id.EventImage);
        EventDescription = (TextView) findViewById(R.id.description);
        EventDate = (TextView) findViewById(R.id.date);

        String flag = null;
        String value = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key").toString();
            //The key argument here must match that used in the other activity
//            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Posts");

        mDatabase.child(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String eventName = (String) snapshot.child("EventName").getValue();  //prints "Do you have data? You'll love Firebase."
                String imageUri = (String) snapshot.child("EventImage").getValue();
                String eventDescription = (String) snapshot.child("EventDescription").getValue();
                String eventDate[] = ((String) snapshot.child("EventDate").getValue()).split("\\s");
                String date = "";
                int i = 0;
                while (i < 3) {
                    date = date + " " + eventDate[i];
                    i++;
                }

                Toast.makeText(OpenEventDetail.this, eventName, Toast.LENGTH_SHORT).show();

                Picasso.with(OpenEventDetail.this).load(imageUri).into(EventImage);
                Event.setText(eventName);
                EventDate.setText(date);
                EventDescription.setText(eventDescription);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

//        Event.setText(mDatabase.child("EventName").getKey().getValue());


    }
}