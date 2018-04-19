/*A class for matching text samples by frequency of keywords.*/
import java.util.*;
import java.lang.*;
import java.io.*;

public class StyloKey {

    private static Scanner sc = null;

    public static void main(String[] args) {
        Hashtable<String, Integer> alistair = StyloKey.createHashtableAlpha("alistairsameword.txt");
        Hashtable<String, Integer> samp1 = StyloKey.createHashtableAlpha("keefauver2sameword.txt");
        Hashtable<String, Integer> samp2 = StyloKey.createHashtableAlpha("mason2sameword.txt");
        Hashtable<String, Integer> samp3 =StyloKey.createHashtableAlpha("monty2sameword.txt");
        Hashtable<String, Integer> samp4 =StyloKey.createHashtableAlpha("nunn2sameword.txt");
        Hashtable<String, Integer> samp5 =StyloKey.createHashtableAlpha("alistair2sameword.txt");
        //compare values with the first guy & tally "points": frequency is a weight variable
        //(freq1alistair * freq1other) + (freq2alistair * freq2other) + ..
        Enumeration<String> e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 1: " + tallyWeightedScore(alistair, samp1, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 2: " + tallyWeightedScore(alistair, samp2, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 3: " + tallyWeightedScore(alistair, samp3, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 4: " + tallyWeightedScore(alistair, samp4, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 5: " + tallyWeightedScore(alistair, samp5, e));
    }

    public static Hashtable<String, Integer> createHashtableAlpha(String filename) {
        Hashtable<String, Integer> hash = new Hashtable<String, Integer>();
        try {
            sc = new Scanner(new File("C:\\Users\\super\\OneDrive\\Documents\\DATA blog posts\\" + filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        for (; sc.hasNextLine(); ) {
            String line = sc.nextLine();
            //split line along spaces
            String[] lineArray = line.split(" ");
            // remove everything that isn't an alphanumeric character
            for (int j = 0; j < lineArray.length; j++) {  //for each word in the line
                String word = lineArray[j];
                StringBuilder amendWord = new StringBuilder();
                for (int k = 0; k < word.length(); k++) {    //for each letter in the word
                    if (Character.isLetter(word.charAt(k))) {
                        amendWord.append(word.charAt(k));
                    }
                }
                word = amendWord.toString();
                word = word.toLowerCase();
                //if the hashtable does not have this word in it, add it
                if (hash.containsKey(word) == false && word.length() >= 5) {
                    hash.put(word, 1);
                } else if (word.length() >= 5) {
                    int newValue = hash.get(word);
                    newValue++;
                    hash.put(word, newValue);
                }
            }
        }
        return hash;
    }

    public static void testEnum(){
        Hashtable<String, String> test = new Hashtable<String, String>();
        test.put("Hello", "there");
        test.put("nerd", "there");
        test.put("there", "there");
        for (Enumeration<String> e = test.keys(); e.hasMoreElements();) {
            System.out.println(e.nextElement());
        }
    }

    public static int tallyScore(Hashtable<String, Integer> control, Hashtable<String, Integer> comparer, Enumeration<String> e){
        int score = 0;
        for (; e.hasMoreElements();) {
            String compare = e.nextElement();
            if (comparer.containsKey(compare)){
                score = score + (control.get(compare)*comparer.get(compare));
            }
        }
        return score;
    }

    public static int tallyWeightedScore(Hashtable<String, Integer> control, Hashtable<String, Integer> comparer, Enumeration<String> e){
        int score = 0;
        for (; e.hasMoreElements();) {
            String compare = e.nextElement();
            if (comparer.containsKey(compare)){
                int freqctrl = control.get(compare);
                int freqcomp = comparer.get(compare);
                int diff = Math.abs(freqctrl - freqcomp);
                if (diff > 0 && diff <= 0.5*freqctrl) {
                    score = score + ((1/diff)*freqctrl*freqcomp);
                }
            }
        }
        return score;
    }

}