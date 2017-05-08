package com.chatbot.nam.vietnamesechatbotlibrary.services;

import android.content.Context;
import android.content.Intent;
import android.speech.SpeechRecognizer;

/**
 * Created by Tran on 08-May-17.
 */

public class SpeechRecognitionThread  {

    private Context _context;

    private SpeechRecognizer _speechRecognizer;
    private Intent _recognizerIntent;

    private int _hashCode;

    public SpeechRecognitionThread(Context context, Intent recognizerIntent) {
        _context = context;
        _recognizerIntent = recognizerIntent;
        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(_context);
        _hashCode = _speechRecognizer.hashCode();
    }

    public void startListening() {
        _speechRecognizer.startListening(_recognizerIntent);
    }

    public void stopListening() {
        _speechRecognizer.stopListening();
    }

    public void cancel() {
        _speechRecognizer.cancel();
    }
}
