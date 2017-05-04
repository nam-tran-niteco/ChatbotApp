/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.namtran.myapplication.utils.input_analysis;

public class LowerCaseFilter extends Filter{

    public LowerCaseFilter(TokenStream stream) {
        super(stream);
    }
    
    @Override
    protected boolean test(String token) {
        return true;
    }

    @Override
    protected String accept(String token) {
        if (token == null){
            return null;
        }
        return token.toLowerCase();
    }
    
}
