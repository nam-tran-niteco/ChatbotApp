package com.example.namtran.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.services.SpeechService;

/**
 * Created by nam.tran on 18-Jan-17.
 */

public class VoiceActivity extends AppCompatActivity {

    private TextView returnedText;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        returnedText = (TextView) findViewById(R.id.textView1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    startService(new Intent(getApplicationContext(), SpeechService.class));
                } else {
                    stopService(new Intent(getApplicationContext(), SpeechService.class));
                }
            }
        });

    }
}
