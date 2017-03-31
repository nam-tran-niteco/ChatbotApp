package com.example.namtran.myapplication.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.namtran.myapplication.constant.NetworkInfo;
import com.example.namtran.myapplication.features.Feature;
import com.example.namtran.myapplication.features.FeatureFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

/**
 * Created by Tran on 23-Mar-17.
 */

public class GetResponseFromWit extends AsyncTask<Void, String, Void> {

    private Context _context;

    private ChatView _chatView;
    private ChatMessage _botMessage;

    private String userMessage;

    public GetResponseFromWit(Context context, ChatView chatView, ChatMessage botMessage) {
        _context = context;
        _chatView = chatView;
        _botMessage = botMessage;
    }

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
            Log.d("chatbot", jsonStr);

            if (!jsonStr.equals("")) {
                JSONObject returnObject = new JSONObject(jsonStr);
                JSONObject entities = returnObject.getJSONObject("entities");
                Iterator<String> entityKeys = entities.keys();
                HashMap<String, String> responseParams = new HashMap<>();
                while (entityKeys.hasNext()) {
                    String entityKey = entityKeys.next();
                    Log.d("chatbot", entityKey);
                    responseParams.put(entityKey, entities.getJSONArray(entityKey).getJSONObject(0).getString("value"));
                }
                if (!responseParams.isEmpty()) {
                    Feature feature = FeatureFactory.getFeatureByParams(_context, responseParams);
                    // call publishProgress to update UI main thread
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
        _chatView.changeMessage(_botMessage, values[0]);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
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

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
