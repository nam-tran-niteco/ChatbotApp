/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

public abstract class Filter extends TokenStream {

    public Filter(TokenStream stream) {
        super(stream);
    }

    @Override
    public String increaseToken() {
        String token = stream.increaseToken();
        while (! test(token)) {            
            token = stream.increaseToken();
        }
        
        token = accept(token);
        
        return token;
    }

    protected abstract String accept(String token);
    protected abstract boolean test(String token);
}
