package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static android.media.CamcorderProfile.get;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    public ArrayList<String> words = new ArrayList<>();
    public HashMap<String,ArrayList<String>> map = new HashMap<>();
    public HashSet<String> set = new HashSet<>();
    public HashMap<Integer ,ArrayList<String>> size = new HashMap<>();



    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            words.add(word);
            set.add(word);
            int wordLength =word.length();

            if(size.containsKey(wordLength))
            {
                size.get(wordLength).add(word);
            }

            String sortedWord = sorted(word);
            if(map.containsKey(sortedWord)){
                map.get(sortedWord).add(word);
            }
            else
            {
                ArrayList<String> anagramGroup = new ArrayList<>();
                anagramGroup.add(word);
                map.put(sortedWord,anagramGroup);
            }
        }
        }


    public boolean isGoodWord(String word, String base) {
//set.contains(word) && !word.contains(base)
        if(set.contains(word) && word.indexOf(base)==-1)
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        /*
        for(int i=0;i<words.size();i++)
        {
            if(words.get(i).length()==targetWord.length()&&sorted(words.get(i)).equals(sorted(targetWord)))
                result.add(words.get(i));
        }
        */
        String sortedWord= sorted(targetWord);
        if(map.containsKey(sortedWord))
        {
            result.addAll(map.get(sortedWord));
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        //ArrayList<String> resultant;


        for(char i='a';i<='z';i++)
        {
            String wordNew= word + i;
            ArrayList<String> anagrams = getAnagrams(wordNew);
            for(String anagram:anagrams){
                if(isGoodWord(anagram,word)){
                    result.add(anagram);
                }
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        while(true) {
            String start = words.get(random.nextInt(words.size() - 1));
            String ab = sorted(start);
            if (map.containsKey(ab) && map.get(ab).size() >= MIN_NUM_ANAGRAMS) {
                return ab;
            }
        }
    }


    public String sorted(String word){
        char[] letters=word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
