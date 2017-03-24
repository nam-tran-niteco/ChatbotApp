package com.example.namtran.myapplication.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;
import com.example.namtran.myapplication.utils.GetResponseFromWit;
import com.example.namtran.myapplication.utils.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    // URL to get contacts JSON
    private static String url = "https://chatbottestapi.herokuapp.com/chat/runactionsApi";
    private static String url_message = "https://chatbottestapi.herokuapp.com/chat/messageapi";

    private ChatView chatView;

    private ChatMessage botMessage;
    private String userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup chatview
        chatView = (ChatView) findViewById(R.id.chat_view);

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
                GetResponseFromWit getResponseFromWit = new GetResponseFromWit(getApplicationContext(), chatView, botMessage);
                getResponseFromWit.setUserMessage(userMessage);
                getResponseFromWit.execute();
            }
        });

    }

}
