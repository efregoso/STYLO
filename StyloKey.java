/*A class for matching text samples by frequency of keywords.*/
import java.util.*;
import java.lang.*;
import java.io.*;

public class StyloKey {

    private static Scanner sc = null;

    public static void main(String[] args) {
        Hashtable<String, Integer> alistair = StyloKey.createHashtable("alistair.txt");
        Hashtable<String, Integer> samp1 = StyloKey.createHashtable("keefauver2.txt");
        Hashtable<String, Integer> samp2 = StyloKey.createHashtable("mason2.txt");
        Hashtable<String, Integer> samp3 =StyloKey.createHashtable("monty2.txt");
        Hashtable<String, Integer> samp4 =StyloKey.createHashtable("nunn2.txt");
        Hashtable<String, Integer> samp5 =StyloKey.createHashtable("alistair2.txt");
        //compare values with the first guy & tally "points": frequency is a weight variable
        //(freq1alistair * freq1other) + (freq2alistair * freq2other) + ..
        Enumeration<String> e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 1: " + tallyScore(alistair, samp1, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 2: " + tallyScore(alistair, samp2, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 3: " + tallyScore(alistair, samp3, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 4: " + tallyScore(alistair, samp4, e));
        e = alistair.keys();
        System.out.println("Keyword score between Alistair and Sample 5 (ACTUAL): " + tallyScore(alistair, samp5, e));
    }

    public static Hashtable<String, Integer> createHashtable(String filename) {
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


}