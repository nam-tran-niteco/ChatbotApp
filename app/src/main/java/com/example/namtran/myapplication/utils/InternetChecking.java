package com.example.namtran.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Tran on 20-Feb-17.
 */

public class InternetChecking {

    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;

    public InternetChecking (Context context) {
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
    }

    public InternetChecking() {}

    public boolean isConnected (Context context) {
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public boolean isWifi () {
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public int getConnectionType () {
        return activeNetwork == null ? -1 : activeNetwork.getType();
    }

    public ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public void setConnectivityManager(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public NetworkInfo getActiveNetwork() {
        return activeNetwork;
    }

    public void setActiveNetwork(NetworkInfo activeNetwork) {
        this.activeNetwork = activeNetwork;
    }
}
