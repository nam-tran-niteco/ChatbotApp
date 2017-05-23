package com.chatbot.nam.vietnamesechatbotlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tran on 07-May-17.
 */

public class LMCouting{

    private static final String LM_STRING = "discount.lm";

    private Context _context;

    private HashMap<String, Double> lm;

    public LMCouting (Context context) {
        _context = context;
        lm = new HashMap<>();
        loadLM();
    }

    private void loadLM() {
        long start = System.currentTimeMillis();
        try {
            BufferedReader asset = new BufferedReader(new InputStreamReader(_context.getAssets().open("wb" + LM_STRING)));
            String line;
            while ((line = asset.readLine()) != null) {
                if (line.equals("") || line.equals("\\data\\") || line.contains("gram") || line.equals("\\end\\")) {
                    continue;
                }
                String[] temp = line.split("\t");
                lm.put(temp[1], Double.parseDouble(temp[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("chatbot", (System.currentTimeMillis() - start) + "");
    }

    public int chooseTheBestOne(ArrayList<String> results) {
        int N = 3;
        int index = 0;
        double min = 0;

        for (int i = 0; i < results.size(); i++) {
            String result = "<s> " + results.get(i).toLowerCase() + " </s>";
            String[] tokens = result.split(" ");
            float logarit = 0;
            double perplexity = 0;
            int OOV = 0;

            for (int j = 1; j < tokens.length; j++) {
                int selectedIndex = j < (N - 1) ? 0 : j - (N - 1);
                int n = j - selectedIndex;
                while (n > 0) {
                    String gram = TextUtils.join(" ", Arrays.copyOfRange(tokens, selectedIndex, selectedIndex + n));
                    if (lm.containsKey(gram)) {
                        logarit += lm.get(gram);
                        break;
                    } else {
                        n--;
                        if (n == 0) OOV++;
                    }
                }
            }

            perplexity = Math.pow(10, -(logarit / (tokens.length - 2 - OOV)));

            if (min == 0) {
                min = perplexity;
                index = i;
            }
            else {
                if (perplexity < min) {
                    min = perplexity;
                    index = i;
                }
            }
        }

        return index;
    }

}
