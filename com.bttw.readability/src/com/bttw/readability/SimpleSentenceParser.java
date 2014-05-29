package com.readability;

/**
 *
 * @author Brian Feran  
 */

import java.text.BreakIterator;
import java.util.ArrayList;

public class SimpleSentenceParser {

    // string to be broken into sentences
    public ArrayList<String> parse(String str) {
        // create a sentence break iterator

        BreakIterator brkit = BreakIterator.getSentenceInstance();
        brkit.setText(str);

        // iterate across the string
        ArrayList<String> sentences = new ArrayList();
        int start = brkit.first();
        int end = brkit.next();
        while (end != BreakIterator.DONE) {
            String sentence = str.substring(start, end);
            sentences.add(sentence);
            System.out.println(start + " " + sentence);
            start = end;
            end = brkit.next();
        }
        return sentences;
    }
}