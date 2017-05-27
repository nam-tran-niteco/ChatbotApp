package com.example.namtran.myapplication.features;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.example.namtran.myapplication.constant.BotMessage;

import java.util.HashMap;

import static com.example.namtran.myapplication.constant.ParamsKey.INTENT_ENTITY;
import static com.example.namtran.myapplication.constant.ParamsKey.TURN_ON_BLUETOOTH;
import static com.example.namtran.myapplication.constant.ParamsKey.TURN_ON_LIGHT;

/**
 * Created by Tran on 26-May-17.
 */

public class BluetoothFeature extends Feature {

    private BluetoothAdapter bluetoothAdapter;

    public BluetoothFeature(Context context, HashMap<String, String> params) {
        super(context, params);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public String doAction() {

        if ( getParams().get(INTENT_ENTITY).equals(TURN_ON_BLUETOOTH) ) {
            enableBT();
        }
        else disableBT();

        return BotMessage.BLUETOOTH_SUCCESS_MESSAGE;

    }

    public void enableBT() {
        if(bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) bluetoothAdapter.enable();
    }

    public void disableBT() {
        if(bluetoothAdapter != null && bluetoothAdapter.isEnabled()) bluetoothAdapter.disable();
    }

}
