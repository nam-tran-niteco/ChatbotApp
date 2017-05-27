package com.example.namtran.myapplication.features;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import com.example.namtran.myapplication.constant.BotMessage;
import com.example.namtran.myapplication.constant.ParamsKey;

import java.util.HashMap;

import static com.example.namtran.myapplication.constant.ParamsKey.*;

/**
 * Created by Tran on 23-Feb-17.
 */

public class LightFeature extends Feature{

    private CameraManager cameraManager;
    private String[] cameraList;
    private boolean isLightOn;

    public LightFeature(Context context, HashMap<String, String> params) {
        super(context, params);
        try {
            cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
            cameraList = cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String doAction() {
        if ( !isAvailableFlashLight(getContext()) ) {
            return BotMessage.LIGHT_FEATURE_NOT_AVAILABLE_MESSAGE;
        }
        else {
            if ( getParams().get(INTENT_ENTITY).equals(TURN_ON_LIGHT) ) setLightOn(true);
            else setLightOn(false);

            return BotMessage.LIGHT_FEATURE_SUCCESS_MESSAGE;
        }
    }

    public boolean isAvailableFlashLight(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        try {
            cameraManager.setTorchMode(cameraList[0], lightOn);
            isLightOn = lightOn;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


}
