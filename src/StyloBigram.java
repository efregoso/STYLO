/*A class for matching text samples by frequency of keywords.*/
import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.Character;

public class StyloBigram {

    private static Scanner sc = null;

    public static void main(String[] args) {
        Hashtable<String, Integer> alistair = StyloBigram.createHashtableAlpha("alistairsamechar.txt");
        Hashtable<String, Integer> samp1 = StyloBigram.createHashtableAlpha("keefauver2samechar.txt");
        Hashtable<String, Integer> samp2 = StyloBigram.createHashtableAlpha("mason2samechar.txt");
        Hashtable<String, Integer> samp3 =StyloBigram.createHashtableAlpha("monty2samechar.txt");
        Hashtable<String, Integer> samp4 =StyloBigram.createHashtableAlpha("nunn2samechar.txt");
        Hashtable<String, Integer> samp5 =StyloBigram.createHashtableAlpha("alistair2samechar.txt");
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
        System.out.println("Keyword score between Alistair and Sample 5 (ACTUAL): " + tallyWeightedScore(alistair, samp5, e));
        sc.close();
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
            //create a character array
            char[] lineArray = line.toCharArray();
            StringBuilder amendWord = new StringBuilder();
            // remove everything that isn't an alphanumeric character
            for (int j = 0; j < lineArray.length; j++) {  //for each word in the line
                if (Character.isLetter(lineArray[j])) {
                    amendWord.append(lineArray[j]);
                }
            }
            char[] newLine = amendWord.toString().toCharArray();
            //go through each pair of letters & add entries into the hashtable
            for (int k = 0; k+1 < newLine.length; k++) {
                StringBuilder buffer = new StringBuilder();
                buffer.append(newLine[k]);
                buffer.append(newLine[k+1]);
                String word = buffer.toString();
                //if the hashtable does not have this bigram in it, add it
                if (hash.containsKey(word) == false) {
                    hash.put(word, 1);
                } else {
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
                if (diff > 0 && diff < 2*freqctrl){
                    score = score + ((1/diff)*freqctrl*freqcomp);
                }
            }
        }
        return score;
    }

}