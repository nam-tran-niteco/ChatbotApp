package com.example.namtran.myapplication.activities;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.SpeechRecognitionActivity;
import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.TextAnalysisThread;
import com.chatbot.nam.vietnamesechatbotlibrary.utils.TextToSpeechUtil;
import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.utils.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends SpeechRecognitionActivity {

    private ChatView chatView;

    private ChatMessage botMessage;

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

    @Override
    public TextAnalysisThread getTextAnalysisThread() {
        return customTextAnalisis;
    }

    @Override
    public void handleSpeechResult(ArrayList<String> speechResults, String selectedResult) {

        // Write result to file for analysis
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{FileUtil.writeFile(speechResults).getAbsolutePath()}, null, null);

        chatView.addMessage(new ChatMessage(selectedResult, System.currentTimeMillis(), ChatMessage.Type.SENT));
        botMessage = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
        chatView.addMessage(botMessage);

        customTextAnalisis.set_botMessage(botMessage);
    }

    public class CustomTextAnalysis extends TextAnalysisThread {

        private ChatView _chatView;

        private ChatMessage _botMessage;

        private TextToSpeechUtil _textToSpeechUtil;

        CustomTextAnalysis(Context context, ChatView chatView) {
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
