package com.example.namtran.myapplication.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Tran on 01-Apr-17.
 */

public class TextToSpeechUtil {

    private TextToSpeech _textToSpeech;
    private Context _context;

    public TextToSpeechUtil(Context context) {
        _context = context;
        if (_context != null) {
            _textToSpeech = new TextToSpeech(_context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS){
                        int result =_textToSpeech.setLanguage(new Locale("vi", "VN"));
                        if(result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Log.e("chatbot", "This Language is not supported");
                        }
                    }
                    else
                        Log.e("chatbot", "Initilization Failed!");
                }
            });
        }
    }

    public void speakText(CharSequence textToSpeech) {
        if (_textToSpeech != null) {
            _textToSpeech.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void setLanguage(Locale locale) {
        if (_textToSpeech != null) {
            _textToSpeech.setLanguage(locale);
        }
    }

}
