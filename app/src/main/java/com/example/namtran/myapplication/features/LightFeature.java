package com.example.namtran.myapplication.features;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import com.example.namtran.myapplication.constant.BotMessage;
import com.example.namtran.myapplication.constant.ParamsKey;

import java.util.HashMap;

/**
 * Created by Tran on 23-Feb-17.
 */

public class LightFeature extends Feature{

    private CameraManager cameraManager;
    private String[] cameraList;
    private boolean isLightOn;

    public LightFeature(Context context, HashMap<String, String> params) {
        setContext(context);
        setParams(params);
        try {
            cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
            cameraList = cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String doAction() {
        if ( getParams().containsKey(ParamsKey.INTENT_REQUEST) ) {
            if ( !isAvailableFlashLight(getContext()) ) {
                return BotMessage.LIGHT_FEATURE_NOT_AVAILABLE_MESSAGE;
            }
            else {
                if ( getParams().containsKey(ParamsKey.TURN_ON_LIGHT) ) {
                    if ( getParams().get(ParamsKey.TURN_ON_LIGHT).equals("true") ) setLightOn(true);
                    else setLightOn(false);
                }
                else setLightOn(false);
                return BotMessage.LIGHT_FEATURE_SUCCESS_MESSAGE;
            }

        }
        return "";
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
