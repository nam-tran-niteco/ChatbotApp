package com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chatbot.nam.vietnamesechatbotlibrary.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tran on 08-May-17.
 */

public abstract class SpeechRecognitionActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextAnalysisThread textAnalysisThread;

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

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//
//                    // Write result to file for analysis
//                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{FileUtil.writeFile(result).getAbsolutePath()},null, null);

                    String speechResult = result.get(0);

                    handleSpeechResult(speechResult);

                    textAnalysisThread = getTextAnalysisThread();
                    textAnalysisThread.setInputMessage(speechResult);
                    textAnalysisThread.execute();
                }
                break;
            }

        }
    }

    public abstract TextAnalysisThread getTextAnalysisThread ();

    public abstract void handleSpeechResult(String speechResult);

}
