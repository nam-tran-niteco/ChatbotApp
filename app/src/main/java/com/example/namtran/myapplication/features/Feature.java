package com.example.namtran.myapplication.features;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tran on 23-Feb-17.
 */

public abstract class Feature {

    private HashMap<String, String> params = null;

    private Context context;

    public Feature(){}

    public Feature( Context context, HashMap<String, String> params ) {}

    public abstract String doAction();

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
