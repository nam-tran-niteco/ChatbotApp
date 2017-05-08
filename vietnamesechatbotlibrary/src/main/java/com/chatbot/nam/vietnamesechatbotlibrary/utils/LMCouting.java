package com.chatbot.nam.vietnamesechatbotlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Tran on 07-May-17.
 */

public class LMCouting{

    private static final String LM_STRING = "discount.lm";

    private Context _context;

    public LMCouting (Context context) {
        _context = context;
    }

    public HashMap<String, Double> loadLM() {
        try {
            BufferedReader asset = new BufferedReader(new InputStreamReader(_context.getAssets().open("wb" + LM_STRING)));
            String line;
            while ((line = asset.readLine()) != null) {
                Log.d("chatbot", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
