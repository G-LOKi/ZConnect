package com.zconnect.login.zconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AllEvents extends AppCompatActivity {

    private RecyclerView mEventList;
    private DatabaseReference mDatabase;
    private DatabaseReference mPrivileges;
    boolean flag=false;

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

            mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Posts");
            mPrivileges = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Privileges");

            mPrivileges.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child: snapshot.getChildren()) {

                        if (child.getValue().equals("garg.lokesh96@gmal.com"))
                        {
                            flag=true;
                        }

                    } //prints "Do you have data? You'll love Firebase."
                }
                @Override public void onCancelled(DatabaseError error) {

                }
            });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (flag){
                    Intent intent = new Intent(AllEvents.this, AddEvent.class);
                    startActivity(intent);
                }
                else{
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(AllEvents.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.request, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        Toast.makeText(AllEvents.this, "Request Sent", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                // Set other dialog properties


                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();}

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

//                SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MM dd HH:mm:ss");
//                try {
//
//                    String simpleDate = model.getSimpleDate();
//
//                    Date endDate = outputFormat.parse(simpleDate);
//                    viewHolder.setEventReminder(model.getEventDescription(), model.getEventName(), endDate);
//
//                }
//                catch (ParseException e) {
//                    e.printStackTrace();
//                }


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

//        public void setEventReminder(final String eventDescription, final String eventName, final Date simpleDate)
//        {
//            Button Reminder = (Button) mView.findViewById(R.id.reminder);
//            Reminder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                                addReminderInCalendar(eventName,eventDescription,simpleDate,mView.getContext());
//
//                    }
//
//            });
//
//        }
//
//        private void addReminderInCalendar(String title, String desc, Date simpleDate, Context context) {
//            Calendar cal = Calendar.getInstance();
//
//
//            Intent intent = new Intent(Intent.ACTION_EDIT);
//            intent.setType("vnd.android.cursor.item/event");
//            intent.putExtra("beginTime", cal.getTimeInMillis()+simpleDate.getTime());
//            intent.putExtra("allDay", false);
//            intent.putExtra("rrule", "FREQ=DAILY");
//            intent.putExtra("endTime", cal.getTimeInMillis()+simpleDate.getTime()+60*60*1000);
//            intent.putExtra("title",title);
//            intent.putExtra("description",desc);
//            context.startActivity(intent);
//
//            // Display event id.
//            //Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();
//
//            /** Adding reminder for event added. *
//             }
//             /** Returns Calendar Base URI, supports both new and old OS. */
//
//
//        }
    }


}
