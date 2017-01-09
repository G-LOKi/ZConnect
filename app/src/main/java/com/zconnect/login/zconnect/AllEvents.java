package com.zconnect.login.zconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class AllEvents extends AppCompatActivity {

    private RecyclerView mEventList;
    private DatabaseReference mDatabase;

    private Button Reminder;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEventList = (RecyclerView) findViewById(R.id.eventList);
            mEventList.setHasFixedSize(true);
            mEventList.setLayoutManager(new LinearLayoutManager(this));

            mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllEvents.this, AddEvent.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(
                Event.class,
                R.layout.events_row,
                EventViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventDesc(model.getEventDescription());
                viewHolder.setEventImage(getApplicationContext(), model.getEventImage());
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventReminder(model.getEventDate(), model.getEventDescription(), model.getEventName());
            }

        };
        mEventList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public EventViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEventName(String eventName){

            TextView post_name = (TextView) mView.findViewById(R.id.event);
            post_name.setText(eventName);

        }

        public void setEventDesc(String eventDesc){

            TextView post_desc = (TextView) mView.findViewById(R.id.description);
            post_desc.setText(eventDesc);

        }

        public void setEventImage(Context ctx, String image){


            ImageView post_image = (ImageView) mView.findViewById(R.id.postImg);
            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setEventDate(String eventDate)
        {

            TextView post_date = (TextView) mView.findViewById(R.id.date);
            post_date.setText(eventDate);
//            String month,date;
//            TextView post_date = (TextView) mView.findViewById(R.id.date);
//            month = new DateFormatSymbols().getMonths()[eventDate.getMonth()-1];
//            date=eventDate.getDate()+" " + month;
//            post_date.setText(date);
        }

        public void setEventReminder(String eventDate,String eventDescription, String eventName)
        {
            Button Reminder = (Button) mView.findViewById(R.id.reminder);
            Reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }

    }


}
