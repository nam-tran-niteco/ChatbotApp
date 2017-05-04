/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StopwordFilter extends Filter {
    public static String STOP_WORD_FILE = "stopwords.txt";
    private Set<String> stopWords;

    public StopwordFilter(TokenStream input, Set<String> stopWords) {
        super(input);
        this.stopWords = stopWords;
    }

    @Override
    protected boolean test(String token) {
        return stopWords.contains(token) ? false : true;
    }

    public static Set loadStopwords(File stopWordsFile) {
        if (stopWordsFile == null) {
            throw new NullPointerException();
        }
        Set rs = new HashSet();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(stopWordsFile));
            String line = null;
            while ((line = reader.readLine()) != null){
                rs.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StopwordFilter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StopwordFilter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(StopwordFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rs;
    }

    @Override
    protected String accept(String token) {
        return token;
    }
}
