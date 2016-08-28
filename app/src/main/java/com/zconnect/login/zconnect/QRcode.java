package com.zconnect.login.zconnect;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class QRcode extends AppCompatActivity {

    private Camera camera;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialise();
    }

    protected void initialise(){

        camera = Camera.open();
        camera.setDisplayOrientation(90);
        Camera.Parameters camPara = camera.getParameters();
        camPara.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(camPara);
        CamerPreview cameraPreview = new CamerPreview(QRcode.this,camera);
        camera.setPreviewCallback(new CamEvent());
        frameLayout = (FrameLayout)findViewById(R.id.FL_camera);
        frameLayout.addView(cameraPreview);

    }
}
