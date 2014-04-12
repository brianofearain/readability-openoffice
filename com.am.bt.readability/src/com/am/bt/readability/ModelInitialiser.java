package com.readability;

import java.util.ArrayList;
import java.util.Arrays;

public final class ModelIntialiser {

    private double overSixCharacters = 0.0;
    private double singleSyllable = 0.0;
    private double ruleBased = 0.00;
    double totalSyllableCount = 0;
    double totalLaingSyllableCount = 0;
    double combinedLaingAndRuleBased = 0;
    double polySyllableCount = 0;
    public final String FLESCHREADINGEASE = "Flesch Reading Ease";
    public final String FLESCHGRADELEVEL = "Flesch Grade Level";
    public final String ARI = "Automated Readability Index";
    public final String COLEMANLIAU = "Coleman-Liau Index";
    public final String SMOG = "Smog Index";
    private double daleChallWordCount = 0.0;
    private double liangSyllableCount;
    public boolean isLessThan100 = false;
    public ModelIntialiser() {
    }

    public int countChars(String tmpSentence) {
        int count = 0;
        for (int i = 0; i < tmpSentence.length() - 1; i++) {
            //     if ((Character.isLetter(tmpSentence.charAt(i))) || (Character.isDigit(tmpSentence.charAt(i)))) {
            count++;
            //   }
        }
        return count;
    }

    public Model dispatch(String text) {
        SentenceOpenNlp sentences = new SentenceOpenNlp();
        ArrayList<String> sentenceList = sentences.getSentences(text);
        
       
        int rbSentenceCount = new SimpleSentenceParser().parse(text).size();
        Model model = new Model();
        model.setDDNumSentences((double) sentenceList.size());
        ArrayList<SentenceStats> listOfSentenceStats = new ArrayList();

             String textTrimmed = text.trim();
             
       String extraCharsRemoved = textTrimmed.replaceAll("[^A-Za-z0-9 ]", "");
                String[] totalWords = extraCharsRemoved.toLowerCase().split("\\s+");
                  ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(totalWords));
                   ArrayList<String> subWordList;
                  if (wordList.size() < 100){
                     liangSyllableCount =   Liang.hyphenateWord(wordList);
                     isLessThan100 = true;
                  }
                  else{
             subWordList = new ArrayList(wordList.subList(0, 100));                             
            liangSyllableCount =   Liang.hyphenateWord(subWordList); 
                  }
        
        SentenceStats sentenceStats = null;
        for (String sentence : sentenceList) {
            String charSentenceForCount = sentence.replace(".", "");
            charSentenceForCount = charSentenceForCount.replaceAll("\\s", "");
            if (sentence.length() > 0) {
                sentenceStats = new SentenceStats();
                String trimmed = sentence.trim();
                String[] words = trimmed.split("\\s+");
                int sentenceWordCount = trimmed.isEmpty() ? 1 : trimmed
                        .split("\\s+").length;
                sentenceStats.setNumWords(sentenceWordCount);
                sentenceStats.setCharCount(countChars(charSentenceForCount));
                for (int j = 0; j < words.length; j++) {
                    ruleBased = SyllableCounter.getSyllable(words[j]);
                    totalSyllableCount = totalSyllableCount + ruleBased;
                //    daleChallWordCount = daleChallWordCount + DaleChall.checkForWord(words[j]);
                    if (ruleBased == 1.0) {
                        singleSyllable= ruleBased + singleSyllable;
                    }
                   
                    if (ruleBased > 2) {
                        polySyllableCount= polySyllableCount + 1;
                    }
                    if (words[j].length() > 5) {
                        overSixCharacters = overSixCharacters + 1;

                    }


                }

            }
            listOfSentenceStats.add(sentenceStats);


        }
        model.setExtract(sentenceList.get(0));
        model.setSingleSyllable(singleSyllable);
        model.setOverallTotalSyllableCount(totalSyllableCount);
    //     model.setOverallLaingSyllableCount(totalLaingSyllableCount);
        model.setOverallPollySyllables(polySyllableCount);
        model.setTotalOverSixCharacterWords(overSixCharacters);
        //     model.setCombinedLaingAndRuleBased(combinedLaingAndRuleBased);
        model.setDaleChallWordCount(daleChallWordCount);
        model.setSentenceStats(listOfSentenceStats);
        model.setDDNumSentences((double) listOfSentenceStats.size());
       model.setRBNumSentences((double) rbSentenceCount);
        model.setOverallLaingSyllableCount(liangSyllableCount);
        model.setModelPer100(isLessThan100);

       
        return model;

    }

    public double roundValue(double value) {
        return Math.round(value * 100.0) / 100.0;


    }
}