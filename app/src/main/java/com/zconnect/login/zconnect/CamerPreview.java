package com.zconnect.login.zconnect;

import android.content.Context;
import android.hardware.Camera;
import android.service.media.CameraPrewarmService;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Akhil on 28-08-2016.
 */
public class CamerPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera cam;
    private SurfaceHolder mhold;
    public CamerPreview(Context context, Camera camera){

        super(context);
        cam = camera;
        mhold = getHolder();
        mhold.addCallback(this);
        mhold.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)  {
    try{
        cam.setPreviewDisplay(mhold);
        cam.startPreview();
    }
    catch (IOException e){
        //handle exceptions
    }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)  {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)  {

    }

}
