package com.example.namtran.myapplication.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.namtran.myapplication.constant.NetworkInfo;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

/**
 * Created by Tran on 23-Mar-17.
 */

public class GetResponseFromWit {

    private Context _context;

    private ChatView _chatView;

    private ChatMessage _botMessage;
    private String userMessage;

    private TextToSpeechUtil _textToSpeechUtil;

    private GetResponseThread _getResponseThread;

    public GetResponseFromWit(Context context, ChatView chatView) {
        _context = context;
        _chatView = chatView;
        _textToSpeechUtil = new TextToSpeechUtil(context);
    }

    public void execute() {
        _getResponseThread = new GetResponseThread();
        _getResponseThread.execute();
    }

    private class GetResponseThread extends AsyncTask<Void, String, Void> {

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
                String jsonStr = sh.sendRequest(NetworkInfo.SERVER_URL_MESSAGE, params.toString());
                publishProgress(getBotResponse(jsonStr));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            _chatView.changeMessage(_botMessage, values[0]);
            _textToSpeechUtil.speakText(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public String handleResponse(String jsonStringResult) {
        HashMap<String, String> responseParams = getResponseParams(jsonStringResult);
        if (!responseParams.isEmpty()) {
            Feature feature = FeatureFactory.getFeatureByParams(_context, responseParams);
            return feature.doAction();
        }
        return "";
    }

    @Nullable
    private HashMap<String, String> getResponseParams(String jsonStringResult) {
        try {
            if (!jsonStringResult.equals("")) {
                JSONObject entities = new JSONObject(jsonStringResult).getJSONObject("entities");
                Iterator<String> entityKeys = entities.keys();
                HashMap<String, String> responseParams = new HashMap<>();
                while (entityKeys.hasNext()) {
                    String entityKey = entityKeys.next();
                    responseParams.put(entityKey, entities.getJSONArray(entityKey).getJSONObject(0).getString("value"));
                }
                return responseParams;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getBotResponse(String jsonStringResult) {
        try {
            if (!jsonStringResult.equals("")) {
                JSONObject entities = new JSONObject(jsonStringResult).getJSONObject("entities");
                if (entities.has("intent") && getEntitiesValue(entities, "intent").equals("request")) {
                    String request_action = getEntitiesValue(entities, "request_action");
                    String request_object = getEntitiesValue(entities, "request_object");
                    return "Có phải bạn muốn " + request_action + request_object + "?";
                }
                return "Tôi chưa rõ yêu cầu của bạn, hãy thử một yêu cầu khác";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getEntitiesValue(JSONObject entities, String key) throws JSONException {
        if (entities.has(key)) {
            return entities.getJSONArray(key).getJSONObject(0).getString("value");
        }
        return "";
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public ChatView get_chatView() {
        return _chatView;
    }

    public void set_chatView(ChatView _chatView) {
        this._chatView = _chatView;
    }

    public ChatMessage get_botMessage() {
        return _botMessage;
    }

    public void set_botMessage(ChatMessage _botMessage) {
        this._botMessage = _botMessage;
    }
}
