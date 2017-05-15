package com.example.namtran.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.namtran.myapplication.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tran on 24-Feb-17.
 */

public class TestFeatureActivity extends AppCompatActivity {

    private Button startServiceBt;

    private SpeechRecognizer speechRecognizer;

    private Intent recognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_feature);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getApplicationContext(), "Ready for speech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {
                Toast.makeText(getApplicationContext(), "Begin of speech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(getApplicationContext(), "End of speech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error) {
//                String message;
//                switch (error) {
//                    case SpeechRecognizer.ERROR_AUDIO:
//                        message = "Audio recording error";
//                        break;
//                    case SpeechRecognizer.ERROR_CLIENT:
//                        message = "Client side error";
//                        break;
//                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
//                        message = "Insufficient permissions";
//                        break;
//                    case SpeechRecognizer.ERROR_NETWORK:
//                        message = "Network error";
//                        break;
//                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
//                        message = "Network timeout";
//                        break;
//                    case SpeechRecognizer.ERROR_NO_MATCH:
//                        message = "No match";
//                        break;
//                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
//                        message = "RecognitionService busy";
//                        break;
//                    case SpeechRecognizer.ERROR_SERVER:
//                        message = "error from server";
//                        break;
//                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
//                        message = "No speech input";
//                        break;
//                    default:
//                        message = "Didn't understand, please try again.";
//                        break;
//                }

                Toast.makeText(getApplicationContext(), "Error: ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> resultList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
//                this.getPackageName());
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 6);


        startServiceBt = (Button) findViewById(R.id.startService);
        startServiceBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        startServiceBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
////                    Log.d("chatbot", "start service");
////                    startService(speechRecognitionService);
//                    Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
//                    speechRecognizer.startListening(recognizerIntent);
//                }
//                else {
////                    stopService(speechRecognitionService);
//                    speechRecognizer.stopListening();
//                    speechRecognizer.cancel();
//                }
//            }
//        });
    }
}
