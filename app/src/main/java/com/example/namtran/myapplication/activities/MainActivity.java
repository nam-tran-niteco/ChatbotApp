package com.example.namtran.myapplication.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;
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
                new GetContacts().execute();
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, String, Void> {

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
                params.put("chat", userMessage);
                String jsonStr = sh.sendRequest(url_message, params.toString());
                Log.d("chatbot", jsonStr);

                if (!jsonStr.equals("")) {
                    JSONObject returnObject = new JSONObject(jsonStr);
                    JSONObject entities = returnObject.getJSONObject("entities");
                    Iterator<String> entityKeys = entities.keys();
                    HashMap<String, String> responseParams = new HashMap<>();
                    while (entityKeys.hasNext()) {
                        String entityKey = entityKeys.next();
                        responseParams.put(entityKey, entities.getJSONArray(entityKey).getJSONObject(0).getString("value"));
                    }
                    if (!responseParams.isEmpty()) {
                        Feature feature = FeatureFactory.getFeatureByParams(getApplicationContext(), responseParams);
                        publishProgress(feature.doAction());
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            chatView.changeMessage(botMessage, values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
