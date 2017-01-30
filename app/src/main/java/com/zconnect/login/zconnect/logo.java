package com.zconnect.login.zconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Timer;
import java.util.TimerTask;


public class logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        // Setting full screen view for the fi
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Calling xml view file
        this.setContentView(R.layout.activity_logo);

        // Time Delay ofor the logo activity
        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(logo.this, HomeActivity.class));
                finish();
            }
        }, 2500);
    }
}