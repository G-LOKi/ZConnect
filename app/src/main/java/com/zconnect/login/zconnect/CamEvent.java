package com.zconnect.login.zconnect;

import android.hardware.Camera;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.SymbolSet;
/**
 * Created by Akhil on 28-08-2016.
 */
public class CamEvent implements Camera.PreviewCallback {

    private ImageScanner scanner;

    public CamEvent() {
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);// this is the striding value while scanning in the X-direction
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        scanner.setConfig(0, Config.ENABLE, 0);// to disable all symbols used in scaner
        scanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);// eabling QR code scan ability....meant only for opr=timsation and speed.

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera)  {

        Camera.Parameters camPara= camera.getParameters();
        Camera.Size size = camPara.getPreviewSize();
        int width = size.width;
        int height = size.height;


        Image image = new Image(width,height,"Y800");
        image.setData(data);
        int result = scanner.scanImage(image);
        String value ="";
        if(result!=-1 && result!=0){

            SymbolSet symbols = scanner.getResults();
            for(Symbol sym : symbols)
            {
                value = sym.getData();
                break;
            }

            camera.setPreviewCallback(null);
            camera.stopPreview();
        }
    }

}
