package com.example.namtran.myapplication.features;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tran on 23-Feb-17.
 */

public abstract class Feature {

    private HashMap<String, String> _params = null;

    private Context _context;

    public Feature(){}

    public Feature( Context context, HashMap<String, String> params ) {
        _context = context;
        _params = params;
    }

    public abstract String doAction();

    public HashMap<String, String> getParams() {
        return _params;
    }

    public void setParams(HashMap<String, String> params) {
        _params = params;
    }

    public Context getContext() {
        return _context;
    }

    public void setContext(Context context) {
        _context = context;
    }
}
