package com.example.namtran.myapplication.services;

import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.SpeechRecognitionService;
import com.chatbot.nam.vietnamesechatbotlibrary.mainfeatures.TextAnalysisThread;

/**
 * Created by Tran on 08-May-17.
 */

public class SpeechService extends SpeechRecognitionService {

    @Override
    public TextAnalysisThread getTextAnalysisThread() {
        return null;
    }
}
