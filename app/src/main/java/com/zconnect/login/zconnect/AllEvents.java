package com.zconnect.login.zconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.google.firebase.auth.FirebaseAuth;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class AllEvents extends AppCompatActivity {

    private RecyclerView mEventList;
    private DatabaseReference mDatabase;
    private DatabaseReference mPrivileges;
    boolean flag=false;

    private DatabaseReference mRequest;

    private Button Reminder;
        @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //get current user
            final String emailId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            mEventList = (RecyclerView) findViewById(R.id.eventList);
            mEventList.setHasFixedSize(true);
            mEventList.setLayoutManager(new LinearLayoutManager(this));

            mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Posts");
            mPrivileges = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Privileges");
            mRequest = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/");

            mPrivileges.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child: snapshot.getChildren()) {

                        if (child.getValue().equals(emailId))
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




                        //checks if user is online
                        if(!isOnline()) {
                            Toast.makeText(AllEvents.this, "Request not Sent. Check Internet Connection", Toast.LENGTH_LONG).show();
                        }
                        else {
//                                mRequest = FirebaseDatabase.getInstance().getReference().child("ZConnect/Events/Requests");
//                                DatabaseReference newPost = mRequest.push();
//                                newPost.child("Email").setValue(emailId);
                            writeNewPost(emailId);


//                            GMail ob = new GMail("zconnectmailer@gmail.com", "Cool@coder01", "garg.lokesh96@gmail.com", "SUBJECT", "LITE");
//                            try {
//                                MimeMessage message = ob.createEmailMessage();
//                            } catch (MessagingException e) {
//                                e.printStackTrace();
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                ob.sendEmail();
//                            } catch (MessagingException e) {
//                                e.printStackTrace();
//                            }
//
                            Toast.makeText(AllEvents.this, "Request Sent", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }
                });
                // Set other dialog properties


                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();}

            }
        });


    }

    @IgnoreExtraProperties
    public class Post {

        public String email;
        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post( String email) {

            this.email = email;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("email", email);
            result.put("stars", stars);
            return result;
        }

    }

    private void writeNewPost(String email) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        Post post = new Post( email);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();


        childUpdates.put( "/Requests" , postValues  );

        mRequest.updateChildren(childUpdates);
    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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
