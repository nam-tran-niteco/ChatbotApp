package com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.chatbot.nam.vietnamesechatbotlibrary.R;
import com.chatbot.nam.vietnamesechatbotlibrary.utils.LMUtil;

import java.util.ArrayList;
import java.util.Locale;

import static com.chatbot.nam.vietnamesechatbotlibrary.constant.LogTag.LOG_TAG;

/**
 * Created by Tran on 08-May-17.
 */

public abstract class SpeechRecognitionActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextAnalysisThread textAnalysisThread;

    private LMUtil lmUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lmUtil = new LMUtil(this);
    }

    /**
     * Showing google speech input dialog
     * */
    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speechResults = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    for (String result : speechResults) {
                        Log.d(LOG_TAG, result);
                    }
                    Log.d(LOG_TAG, "Choose: " + lmUtil.chooseTheBestOne(speechResults));
                    String selectedResult = speechResults.get(lmUtil.chooseTheBestOne(speechResults));

                    handleSpeechResult(speechResults, selectedResult);

                    if (textAnalysisThread == null) textAnalysisThread = getTextAnalysisThread();
                    textAnalysisThread.setInputMessage(selectedResult);
                    textAnalysisThread.execute();
                }
                break;
            }

        }
    }

    public abstract TextAnalysisThread getTextAnalysisThread ();

    public abstract void handleSpeechResult(ArrayList<String> speechResults, String selectedResult);

}
