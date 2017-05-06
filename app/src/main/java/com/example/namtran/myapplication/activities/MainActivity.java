package com.example.namtran.myapplication.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chatbot.nam.vietnamesechatbotlibrary.utils.TextAnalysisThread;
import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.utils.FileUtil;
import com.example.namtran.myapplication.utils.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private ChatView chatView;

    private ChatMessage botMessage;
    private String userMessage;

    private CustomTextAnalysis customTextAnalisis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial properties
        chatView = (ChatView) findViewById(R.id.chat_view);
        customTextAnalisis = new CustomTextAnalysis(this, chatView);

        chatView.addMessage(new ChatMessage("Xin chào", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                if (chatMessage.getMessage().equals("")) {
                    promptSpeechInput();
                }
                return true;
            }

            @Override
            public void afterSendMessage() {
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
                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{FileUtil.writeFile(result).getAbsolutePath()},null, null);

                    userMessage = result.get(0);
                    chatView.addMessage(new ChatMessage(userMessage, System.currentTimeMillis(), ChatMessage.Type.SENT));
                    botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
                    chatView.addMessage(botMessage);

                    customTextAnalisis.setInputMessage(userMessage);
                    customTextAnalisis.set_botMessage(botMessage);
                    customTextAnalisis.execute();
                }
                break;
            }

        }
    }

    private class CustomTextAnalysis extends TextAnalysisThread {

        private Context _context;

        private ChatView _chatView;

        private ChatMessage _botMessage;

        private TextToSpeechUtil _textToSpeechUtil;

        CustomTextAnalysis(Context context, ChatView chatView) {
            _context = context;
            _chatView = chatView;
            _textToSpeechUtil = new TextToSpeechUtil(context);
        }

        @Override
        public void onThreadPreExcute() {

        }

        @Override
        public void onThreadProgressUpdate(String... values) {
            _chatView.changeMessage(_botMessage, values[0]);
            _textToSpeechUtil.speakText(values[0]);
        }

        @Override
        public void onThreadPostExecute(HashMap<String, String> result) {

        }

        public ChatMessage get_botMessage() {
            return _botMessage;
        }

        public void set_botMessage(ChatMessage _botMessage) {
            this._botMessage = _botMessage;
        }
    }

}
