package com.example.namtran.myapplication.features;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.namtran.myapplication.constant.ParamsKey;

import java.util.HashMap;

/**
 * Created by Tran on 25-Feb-17.
 */

public class FeatureFactory {

    public static final int LIGHT_FEATURE = 1;

    @Nullable
    public static Feature getFeatureByParams(Context context, HashMap<String, String> params) {
        if ( params.containsKey(ParamsKey.TURN_ON_LIGHT) ) return new LightFeature(context, params);
        return null;
    }

}
