package com.readability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Brian Feran  -- adaption of Greg Fast's popular Syllable Counter
 */

public class SyllableCounter {

    // 'diphthong' list....[ei], [ai], [au], [oi], [ou].
    private static final Pattern[] SubDiphThongs = new Pattern[]{Pattern.compile("cial"), Pattern.compile("tia"),
        Pattern.compile("cius"), Pattern.compile("cious"), Pattern.compile("giu"), 
        Pattern.compile("ion"), Pattern.compile("iou"), Pattern.compile("sia$"), Pattern.compile(".ely$"), // absolutely!
    };
    // 'hiatus' list..
    private static final Pattern[] AddHiatuses = new Pattern[]{Pattern.compile("ia"), Pattern.compile("riet"),
        Pattern.compile("dien"), Pattern.compile("iu"), Pattern.compile("io"), Pattern.compile("ii"),
        Pattern.compile("[aeiouym]bl$"), 
        Pattern.compile("[aeiou]{3}"), 
        Pattern.compile("^mc"), Pattern.compile("ism$"),
        Pattern.compile("([^aeiouy])\1l$"),
        Pattern.compile("[^l]lien"), 
        Pattern.compile("^coa[dglx]."), 
        Pattern.compile("[^gq]ua[^auieo]"),
        // breaks
        Pattern.compile("dnt$"), 
    };

    public static final int getSyllable(final String wordInput) {
        if (wordInput == null || "".equals(wordInput)) {
            return 0;
        }
        final String word = wordInput.toLowerCase().replaceAll("'", "").replaceAll("e$", "");
        if (word.length() == 1) {
            return 1;
        }
        final String[] splitWord = word.split("[^aeiouy]+"); // 
        
        int syllableCount = 0;
        // special cases
        for (final Pattern p : SubDiphThongs) {
            final Matcher m = p.matcher(word);
            if (m.find()) {
                syllableCount--;
            }
        }
        for (final Pattern p : AddHiatuses) {
            final Matcher m = p.matcher(word);
            if (m.find()) {
                syllableCount++;
            }
        }
        // count vowel groupings
        if (splitWord.length > 0 && "".equals(splitWord[0])) {
            syllableCount += splitWord.length - 1;
        } else {
            syllableCount += splitWord.length;
        }
        if (syllableCount == 0) {
            // got no vowels? ("the", "crwth")
            syllableCount = 1;
        }
        return syllableCount;
    }
}