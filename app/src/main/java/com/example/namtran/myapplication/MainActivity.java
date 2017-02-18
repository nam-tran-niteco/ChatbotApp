package com.example.namtran.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    // URL to get contacts JSON
    private static String url = "http://192.168.1.2:3000/chat";

    private final int REQ_CODE_SPEECH_INPUT = 100;

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
                return true;
            }

            @Override
            public void afterSendMessage() {
                botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
                chatView.addMessage(botMessage);
                new GetContacts().execute();
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                JSONObject params = new JSONObject();
                params.put("chat", "Mình tên là Nam");
                String jsonStr = sh.sendRequest(url, params.toString());
                Log.d("test", jsonStr);

//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            botMessage.setMessage("Done");
        }

    }
}
