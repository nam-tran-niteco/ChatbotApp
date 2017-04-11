package com.example.namtran.myapplication.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.CalendarFeature;
import com.example.namtran.myapplication.utils.FileUtil;
import com.example.namtran.myapplication.utils.GetResponseFromWit;

import java.util.ArrayList;
import java.util.Locale;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    // URL to get contacts JSON
    private static String url = "https://chatbottestapi.herokuapp.com/chat/runactionsApi";
    private static String url_message = "https://chatbottestapi.herokuapp.com/chat/messageapi";
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private ChatView chatView;

    private ChatMessage botMessage;
    private String userMessage;

    private GetResponseFromWit getResponseFromWit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CalendarFeature(this).getCalendar();

        // Initial properties
        chatView = (ChatView) findViewById(R.id.chat_view);
        getResponseFromWit = new GetResponseFromWit(this, chatView);

        chatView.addMessage(new ChatMessage("Xin chào", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
//                userMessage = chatMessage.getMessage();
                if (chatMessage.getMessage().equals("")) {
                    promptSpeechInput();
                }
                return true;
            }

            @Override
            public void afterSendMessage() {
//                botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
//                chatView.addMessage(botMessage);
//                new GetContacts().execute();
            }
        });

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

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // Write result to file for analysis
                    FileUtil.writeFile(result);

                    userMessage = result.get(0);
                    chatView.addMessage(new ChatMessage(userMessage, System.currentTimeMillis(), ChatMessage.Type.SENT));
                    botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
                    chatView.addMessage(botMessage);
                    getResponseFromWit.setUserMessage(userMessage);
                    getResponseFromWit.set_botMessage(botMessage);
                    getResponseFromWit.execute();
                }
                break;
            }

        }
    }

}
