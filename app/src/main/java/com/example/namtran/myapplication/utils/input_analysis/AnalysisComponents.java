/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

import java.io.Reader;

public class AnalysisComponents {

    private Tokenizer inStream;
    private TokenStream outStream;

    public AnalysisComponents(Tokenizer inStream, TokenStream outStream) {
        this.inStream = inStream;
        this.outStream = outStream;
    }

    public TokenStream getTokenStream(){
        return outStream;
    }

    /**
     * Set new reader for inStream. Using this function when want to re-use a
     * AnalysisComponents object
     *
     * @param reader
     */
    public void setReader(Reader reader) {
        inStream.reset();
        inStream.setReader(reader);
    }
}
