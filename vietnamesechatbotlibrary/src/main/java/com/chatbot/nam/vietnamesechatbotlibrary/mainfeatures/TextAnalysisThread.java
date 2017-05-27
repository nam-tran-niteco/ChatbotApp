package com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures;

import android.os.AsyncTask;

import com.chatbot.nam.vietnamesechatbotlibrary.constant.NetworkInfo;
import com.chatbot.nam.vietnamesechatbotlibrary.utils.HttpHandler;
import com.chatbot.nam.vietnamesechatbotlibrary.utils.LMUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Tran on 23-Mar-17.
 */

public abstract class TextAnalysisThread {

    private static final String ENTITIES_KEY = "entities";

    private String inputMessage;

    private String botMessage;

    private GetResponseThread _getResponseThread;

    public void execute() {
        _getResponseThread = new GetResponseThread();
        _getResponseThread.execute();
    }

    private class GetResponseThread extends AsyncTask<Void, String, HashMap<String, String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onThreadPreExcute();
        }

        @Override
        protected HashMap<String, String> doInBackground(Void... arg0) {
            HashMap<String, String> textAnalyzed = new HashMap<>();
            try {
                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                JSONObject params = new JSONObject();
//                JSONArray input = new JSONArray(inputMessage);
                params.put(NetworkInfo.INPUT_KEY, inputMessage);
                String jsonStr = sh.sendRequest(NetworkInfo.SERVER_LOCAL_URL, params.toString());
                textAnalyzed = getResponseParams(jsonStr);
                setBotMessage(getBotResponse(jsonStr));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return textAnalyzed;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            onThreadProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            super.onPostExecute(result);
            onThreadPostExecute(result);
        }

        public void publishThread(String... values) {
            publishProgress(values);
        }
    }

    public abstract void onThreadPreExcute();

    public abstract void onThreadProgressUpdate(String... values);

    public abstract void onThreadPostExecute(HashMap<String, String> result);

    public void publishThread(String... values) {
        if (_getResponseThread != null) _getResponseThread.publishThread(values);
    }

    public HashMap<String, String> getResponseParams(String jsonStringResult) {
        HashMap<String, String> responseParams = new HashMap<>();
        try {
            JSONObject entities = getChildObject(jsonStringResult, ENTITIES_KEY);
            if (entities != null) {
                Iterator<String> entityKeys = entities.keys();
                while (entityKeys.hasNext()) {
                    String entityKey = entityKeys.next();
                    responseParams.put(entityKey, getEntitiesValue(entities, entityKey));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseParams;
    }

    private String getBotResponse(String jsonStringResult) {
        try {
            JSONObject response = getChildObject(jsonStringResult, "response");
            if (response != null) {
                return response.getString("text");
            }
            return "Tôi chưa rõ yêu cầu của bạn, hãy thử một yêu cầu khác";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private JSONObject getChildObject(String jsonStringResult, String objectKey) throws JSONException {
        if (!jsonStringResult.equals("")) {
            JSONObject resultObject = new JSONObject(jsonStringResult);
            if (resultObject.has(objectKey) && resultObject.getJSONObject(objectKey) != null) return resultObject.getJSONObject(objectKey);
        }
        return null;
    }

    private String getEntitiesValue(JSONObject entities, String key) throws JSONException {
        if (entities != null && entities.has(key)) {
            return entities.getJSONArray(key).getJSONObject(0).getString("value");
        }
        return "";
    }

    public String getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(String botMessage) {
        this.botMessage = botMessage;
    }
}
