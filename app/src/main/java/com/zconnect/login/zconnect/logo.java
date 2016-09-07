package com.zconnect.login.zconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_logo);

        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
////                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_logo);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(logo.this, logIn.class));
                finish();
            }
        }, 5000);



    }
}
