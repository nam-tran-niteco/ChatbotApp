package com.example.namtran.myapplication.activities;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;

import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.SpeechRecognitionActivity;
import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.TextAnalysisThread;
import com.chatbot.nam.vietnamesechatbotlibrary.utils.TextToSpeechUtil;
import com.example.namtran.myapplication.R;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;
import com.example.namtran.myapplication.utils.DBHelper;
import com.example.namtran.myapplication.utils.DbUtil;
import com.example.namtran.myapplication.utils.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

import static com.example.namtran.myapplication.constant.ParamsKey.INTENT_ENTITY;

public class MainActivity extends SpeechRecognitionActivity {

    private ChatView chatView;

    private ChatMessage botMessageView;

    private CustomTextAnalysis customTextAnalisis;

    private DbUtil dbUtil;

    private String userMessage;
    private String botMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial properties
        chatView = (ChatView) findViewById(R.id.chat_view);
        customTextAnalisis = new CustomTextAnalysis(getApplicationContext(), chatView);
        dbUtil = new DbUtil(getApplicationContext());

        Cursor cursor = dbUtil.getChatDialog();

        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                chatView.addMessage(new ChatMessage(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ChatEntry.COLUMN_USER_MESSAGE)), System.currentTimeMillis(), ChatMessage.Type.SENT));
                chatView.addMessage(new ChatMessage(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ChatEntry.COLUMN_BOT_MESSAGE)), System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
            }
        }

        cursor = dbUtil.getUserInfo("username");

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToNext();
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.UserEntry.COLUMN_VALUE));
            chatView.addMessage(new ChatMessage("Xin chào " + username + ", chào mừng quay trở lại !!!", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        }
        else {
            chatView.addMessage(new ChatMessage("Xin chào", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        }

        cursor.close();

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

        userMessage = selectedResult;
        chatView.addMessage(new ChatMessage(selectedResult, System.currentTimeMillis(), ChatMessage.Type.SENT));
        botMessageView = new ChatMessage("Bot đang nhập ...", System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
        chatView.addMessage(botMessageView);

        customTextAnalisis.set_botMessage(botMessageView);
    }

    public class CustomTextAnalysis extends TextAnalysisThread {

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
        public void onThreadPreExcute() {}

        @Override
        public void onThreadProgressUpdate(String... values) {
            _chatView.changeMessage(_botMessage, values[0]);
            _textToSpeechUtil.speakText(values[0]);
        }

        @Override
        public void onThreadPostExecute(HashMap<String, String> result) {
            Feature feature = FeatureFactory.getFeatureByParams(_context, result);
            if (feature != null) publishThread(botMessage = feature.doAction());
            else if (getBotMessage() != null) {
                if (result.containsKey(INTENT_ENTITY) && result.get(INTENT_ENTITY).equals("info")) {
                    Log.d("chatbot", result.get("username"));
                    if (result.containsKey("username")) {
                        dbUtil.insertUserInfo("username", result.get("username"));
                    }
                }
                publishThread(botMessage = getBotMessage());
            }
            else publishThread(botMessage = "Tôi chưa rõ yêu cầu của bạn. Hãy thử yêu cầu khác");
            dbUtil.insertChat(userMessage, botMessage);
        }

        public ChatMessage get_botMessage() {
            return _botMessage;
        }

        public void set_botMessage(ChatMessage _botMessage) {
            this._botMessage = _botMessage;
        }
    }

}
