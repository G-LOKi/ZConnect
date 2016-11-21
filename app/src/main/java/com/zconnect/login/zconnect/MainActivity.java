package com.zconnect.login.zconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mEventList ;
    private DatabaseReference mDatabase;
    private LinearLayoutManager linearLayoutManager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mEventList= (RecyclerView) findViewById(R.id.eventList);
        mEventList.setHasFixedSize(true);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Event");
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        FloatingActionButton post =(FloatingActionButton) findViewById(R.id.add_event);
                post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Post_activity.class));

            }
        });

        mEventList.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Event,eventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event, eventViewHolder>(
                Event.class,
                R.layout.event_row,
                eventViewHolder.class,
                mDatabase) {



            @Override
            protected void populateViewHolder(eventViewHolder viewHolder, final Event model, int position) {

                viewHolder.setTitle(model.getTitl());
                viewHolder.setDescription(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());

               String Date = model.getDa()+"/"+model.getMon()+"/"+model.getYea()+"";
               String Time = model.getHo()+":"+model.getMin()+"";
               viewHolder.setDate(Date);
                viewHolder.setTime(Time);


               Calendar cal = Calendar.getInstance();
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

               try {
                   Date endDate = outputFormat.parse(model.getYea() + "-" + model.getMon() + "-" + model.getDa() + " " + model.getHo() + ":" + model.getMin());

                   viewHolder.makeButton(model.getTitl(),model.getDesc(),endDate.getTime()-cal.getTimeInMillis());

               }
               catch (ParseException e) {
                   e.printStackTrace();
               }


                //Toast.makeText(getApplicationContext(),"Check 1",Toast.LENGTH_LONG);


            }


        };
        mEventList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public static class eventViewHolder extends RecyclerView.ViewHolder

    {
        View mView;
        public eventViewHolder(View itemView) {

            super(itemView);
            mView=itemView;


        }

        public void setTitle(String title)
        {
            TextView postTitle= (TextView) mView.findViewById(R.id.title);
            postTitle.setText(title);

        }
        public void setDescription(String description)
        {
            TextView postDesc= (TextView) mView.findViewById(R.id.desc);
            postDesc.setText(description);

        }
       public void setDate(String title)
        {
            TextView setDate= (TextView) mView.findViewById(R.id.date);
            setDate.setText(title);

        }
        public void setTime(String title)
        {
            TextView setTime= (TextView) mView.findViewById(R.id.time);
            setTime.setText(title);

        }
        public void setImage(Context context, String url)
        {
            ImageView imageView= (ImageView) mView.findViewById(R.id.imageview);
            Picasso.with(context).load(url).into(imageView);


        }
      public void makeButton(final String title , final String desc , final long time)
        {
            Button set_reminder = (Button) mView.findViewById(R.id.Add);
            set_reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        addReminderInCalendar(title,desc,time,mView.getContext());

                }
            });
        }
        private void addReminderInCalendar(String title, String desc, long time_gaph,Context context ) {
            Calendar cal = Calendar.getInstance();


            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis()+time_gaph);
            intent.putExtra("allDay", false);
            intent.putExtra("rrule", "FREQ=DAILY");
            intent.putExtra("endTime", cal.getTimeInMillis()+time_gaph+60*60*1000);
            intent.putExtra("title",title);
            intent.putExtra("description",desc);
            context.startActivity(intent);

            // Display event id.
            //Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

            /** Adding reminder for event added. *
        }

        /** Returns Calendar Base URI, supports both new and old OS. */


    }}








}
