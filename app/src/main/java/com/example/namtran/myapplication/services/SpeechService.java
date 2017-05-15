package com.example.namtran.myapplication.services;

import android.util.Log;

import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.SpeechRecognitionService;
import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.TextAnalysisThread;

import java.util.HashMap;

import static com.chatbot.nam.vietnamesechatbotlibrary.constant.LogTag.LOG_TAG;

/**
 * Created by Tran on 08-May-17.
 */

public class SpeechService extends SpeechRecognitionService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Speech Service created");
    }

    @Override
    public TextAnalysisThread getTextAnalysisThread() {
        return new CustomTextAnalysisThread();
    }

    private class CustomTextAnalysisThread extends TextAnalysisThread {

        @Override
        public void onThreadPreExcute() {

        }

        @Override
        public void onThreadProgressUpdate(String... values) {

        }

        @Override
        public void onThreadPostExecute(HashMap<String, String> result) {

        }
    }

}
