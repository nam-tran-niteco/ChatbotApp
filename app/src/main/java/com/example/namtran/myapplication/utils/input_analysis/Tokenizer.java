/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Tokenizer extends TokenStream {

    protected Reader reader;

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void reset() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
