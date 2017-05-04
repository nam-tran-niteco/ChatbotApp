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

public class TCAnalyzer extends Analyzer {

    private File _stopWordsFile;

    public TCAnalyzer() {
    }

    public TCAnalyzer(File stopWordsFile) {
        _stopWordsFile = stopWordsFile;
    }

    @Override
    public AnalysisComponents createAnalysisComponents() {
        Tokenizer source = new TCTokenizer();
        TokenStream stopwordFilter = new StopwordFilter(source,
                StopwordFilter.loadStopwords(_stopWordsFile));
        TokenStream lowercaseFilter = new LowerCaseFilter(stopwordFilter);
        return new AnalysisComponents(source, lowercaseFilter);
    }
    
    public static void example() throws FileNotFoundException {
        Analyzer analyzer = new TCAnalyzer();
        TokenStream ts = analyzer.tokenStream(new BufferedReader(new FileReader("data/0.tt")));
        String token = null;
        while ((token = ts.increaseToken()) != null) {            
            System.out.print(token + "|");
        }
    }
    
}
