/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.hus.nlp.tokenizer.VietTokenizer;

public class TCTokenizer extends Tokenizer {

    private Iterator<String> tokens;
    private VietTokenizer tokenizer;

    public TCTokenizer() {
        tokenizer = new VietTokenizer();
    }

    @Override
    public String increaseToken() {
        if (tokens == null) {
            try {
                StringBuilder builder = new StringBuilder();
                char [] buff = new char[1024];
                int num;
                while ((num = reader.read(buff, 0, buff.length)) != -1) {
                    builder.append(buff, 0, num);
                }
                String text = builder.toString();
                String[] tokenArr = tokenizer.tokenize(text)[0].split(" ");
                tokens = Arrays.asList(tokenArr).iterator();
            } catch (IOException ex) {
                Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return tokens.hasNext() ? tokens.next() : null;
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        tokens = null;
    }
}
