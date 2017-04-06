package com.example.namtran.myapplication.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.namtran.myapplication.utils.NetworkUtil;

/**
 * Created by Tran on 22-Mar-17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetworkUtil.getConnectivityStatusString(context);
        if (isConnected) Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Lost network connection", Toast.LENGTH_SHORT).show();
    }

}
