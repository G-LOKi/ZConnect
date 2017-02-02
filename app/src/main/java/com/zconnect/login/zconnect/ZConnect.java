package com.zconnect.login.zconnect;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by shubhamk on 21/1/17.
 */

public class ZConnect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialise Fresco when app starts
        //this will speed download process and is required by this library
        //more info http://frescolib.org/
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        Fresco.initialize(this);


    }
}
