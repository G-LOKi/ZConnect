package com.zconnect.login.zconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zconnect.login.zconnect.Shop.Details_of_shop.ShopDetails;


public class logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting full screen view for the fi
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Calling xml view file
        this.setContentView(R.layout.activity_logo);
        Button mButton = (Button) findViewById(R.id.MagicIsland);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logo.this, ShopDetails.class));

            }
        });
        // Time Delay for the logo activity
     /**   new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(logo.this, logIn.class));
                finish();
            }
        }, 2500);*/
    }
}
