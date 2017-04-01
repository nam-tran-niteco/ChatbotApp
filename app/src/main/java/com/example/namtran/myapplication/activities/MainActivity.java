package com.example.namtran.myapplication.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;
import com.example.namtran.myapplication.utils.GetResponseFromWit;
import com.example.namtran.myapplication.utils.HttpHandler;
import com.example.namtran.myapplication.utils.TextToSpeechUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    private ChatView chatView;

    private ChatMessage botMessage;
    private String userMessage;

    private GetResponseFromWit getResponseFromWit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial properties
        chatView = (ChatView) findViewById(R.id.chat_view);
        getResponseFromWit = new GetResponseFromWit(this, chatView);

        chatView.addMessage(new ChatMessage("Xin chào", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                userMessage = chatMessage.getMessage();
                return true;
            }

            @Override
            public void afterSendMessage() {
                botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
                chatView.addMessage(botMessage);
                getResponseFromWit.setUserMessage(userMessage);
                getResponseFromWit.set_botMessage(botMessage);
                getResponseFromWit.execute();
            }
        });

    }

}
