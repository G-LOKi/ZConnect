package com.zconnect.login.zconnect;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zconnect.login.zconnect.Phonebook_File.Phonebook;

import java.util.Timer;
import java.util.TimerTask;


public class logo extends AppCompatActivity {

    private NotificationCompat.Builder mBuilder;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ZConnect/storeroom");
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.messenger_bubble_small_blue)
                .setContentTitle("Notification!")
                .setContentText("Lorem Ipsum dolor sit et");

        Intent resultIntent = new Intent(this, Phonebook.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Phonebook.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        int notificationID = 1;
        mNotificationManager.notify(notificationID, mBuilder.build());

        // Setting full screen view for the fi
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Calling xml view file
        this.setContentView(R.layout.activity_logo);

        // Time Delay ofor the logo activity
        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(logo.this, logIn.class));
                finish();
            }
        }, 2500);
    }
}
