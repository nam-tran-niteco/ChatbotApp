package com.example.namtran.myapplication.features;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.example.namtran.myapplication.constant.ParamsKey;

import java.util.HashMap;

import static com.example.namtran.myapplication.constant.ParamsKey.*;

/**
 * Created by Tran on 25-Feb-17.
 */

public class FeatureFactory {

    public static final int LIGHT_FEATURE = 1;

    @Nullable
    public static Feature getFeatureByParams(Context context, HashMap<String, String> params) {
        if (params.containsKey(INTENT_ENTITY)) {
            String intentValue = params.get(INTENT_ENTITY);

            if (intentValue.equals(TURN_ON_LIGHT) ||
                    intentValue.equals(TURN_OFF_LIGHT)) return new LightFeature(context, params);

            if (intentValue.equals(TURN_ON_BLUETOOTH) ||
                    intentValue.equals(TURN_OFF_BLUETOOTH)) return new BluetoothFeature(context, params);


        }


        return null;
    }

}
