import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class StyloUnigram {

    private static Scanner sc = null;

    public static void main(String[] args) {
        Hashtable<Character, Integer> ctrl = StyloUnigram.createHashtableAlpha("alistairsamechar.txt");
        Hashtable<Character, Integer> samp1 = StyloUnigram.createHashtableAlpha("keefauver2samechar.txt");
        Hashtable<Character, Integer> samp2 = StyloUnigram.createHashtableAlpha("mason2samechar.txt");
        Hashtable<Character, Integer> samp3 = StyloUnigram.createHashtableAlpha("monty2samechar.txt");
        Hashtable<Character, Integer> samp4 = StyloUnigram.createHashtableAlpha("nunn2samechar.txt");
        Hashtable<Character, Integer> samp5 = StyloUnigram.createHashtableAlpha("alistair2samechar.txt");
        //compare values with the first guy & tally "points": frequency is a weight variable
        //(freq1alistair * freq1other) + (freq2alistair * freq2other) + ..
        Enumeration<Character> e = ctrl.keys();
        System.out.println("Unigram score between control and Sample 1: " + tallyWeightedScore(ctrl, samp1, e));
        e = ctrl.keys();
        System.out.println("Unigram score between control and Sample 2: " + tallyWeightedScore(ctrl, samp2, e));
        e = ctrl.keys();
        System.out.println("Unigram score between control and Sample 3: " + tallyWeightedScore(ctrl, samp3, e));
        e = ctrl.keys();
        System.out.println("Unigram score between control and Sample 4: " + tallyWeightedScore(ctrl, samp4, e));
        e = ctrl.keys();
        System.out.println("Unigram score between control and Sample 5 (ACTUAL): " + tallyWeightedScore(ctrl, samp5, e));
    }

    public static Hashtable<Character, Integer> createHashtableAlpha(String filename) {
        Hashtable<Character, Integer> hash = new Hashtable<Character, Integer>();
        try {
            sc = new Scanner(new File("C:\\Users\\super\\OneDrive\\Documents\\DATA blog posts\\" + filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        for (; sc.hasNextLine(); ) {
            String line = sc.nextLine();
            line = line.toLowerCase();
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
            //go through each letter & add entries into the hashtable
            for (int k = 0; k < newLine.length; k++) {
                char letter = newLine[k];
                //if the hashtable does not have this bigram in it, add it
                if (hash.containsKey(letter) == false) {
                    hash.put(letter, 1);
                } else {
                    int newValue = hash.get(letter);
                    newValue++;
                    hash.put(letter, newValue);
                }
            }
        }
        sc.close();
        System.out.println(hash.toString());
        return hash;
    }

    public static int tallyScore(Hashtable<Character, Integer> control, Hashtable<Character, Integer> comparer, Enumeration<Character> e){
        int score = 0;
        for (; e.hasMoreElements();) {
            char compare = e.nextElement();
            if (comparer.containsKey(compare)){
                score = score + (control.get(compare)*comparer.get(compare));
            }
        }
        return score;
    }

    public static int tallyWeightedScore(Hashtable<Character, Integer> control, Hashtable<Character, Integer> comparer, Enumeration<Character> e){
        int score = 0;
        for (; e.hasMoreElements();) {
            char compare = e.nextElement();
            if (comparer.containsKey(compare)){
                int freqctrl = control.get(compare);
                int freqcomp = comparer.get(compare);
                if (freqcomp >= 0.75*freqctrl && freqcomp <=1.25*freqctrl) {
                    if (freqcomp > 0.9*freqctrl && freqcomp < 1.1*freqctrl) {
                        score = score + 2;
                    }
                    else {
                        score = score + 1;
                    }
                }
            }
        }
        return score;
    }

}

