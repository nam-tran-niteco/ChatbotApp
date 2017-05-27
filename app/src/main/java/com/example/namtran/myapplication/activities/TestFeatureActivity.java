package com.example.namtran.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.AlarmFeature;
import com.example.namtran.myapplication.features.BluetoothFeature;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tran on 24-Feb-17.
 */

public class TestFeatureActivity extends AppCompatActivity {

    private ToggleButton startFeature;

    private AlarmFeature alarmFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_feature);
        alarmFeature = new AlarmFeature(getApplicationContext(), null);
        startFeature = (ToggleButton) findViewById(R.id.startService);
        startFeature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) alarmFeature.doAction();
            }
        });
    }
}
