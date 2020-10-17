import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CrackerShift {

    private static CrackerShift instance = new CrackerShift();
    public Port port;

    final private static char fromChar = ' '; //space character
    final private static char toChar = '~'; //~
    final private static char numOfChars = toChar - fromChar + 1;

    final private static String dictionaryFilePath = "dictionary" + File.separator + "english.txt";
    private static Set<String> dictionary;

    private CrackerShift() {
        port = new Port();
    }

    public static CrackerShift getInstance() {
        return instance;
    }

    public class Port implements ICrackerShift {
        @Override
        public String crack(String cipher, File keyFile) {
            char[] res = ceasar(cipher.toCharArray(), 0);
            List<Integer> possibleKeys = heuristicCracker(res);
            for(int i : possibleKeys) {
                return String.valueOf(ceasar(res, (char) (numOfChars - i)));
            }
            return "";
        }
    }

    public static char[] ceasar(char [] clearText, int shiftKey) {
        char[] cipherText = new char[clearText.length];
        for (int i=0; i < clearText.length; i++) {
            cipherText[i] = (char) (clearText[i] + shiftKey);
            if (cipherText[i] > toChar) {
                cipherText[i] -= numOfChars;
            }
        }
        return cipherText;
    }

    private static Set<String> getDictionary () {
        if (dictionary != null)
            return dictionary;
        Scanner file = null;
        try {
            file = new Scanner(new File(dictionaryFilePath));
            dictionary = new HashSet<String>();
            // For each word in the input
            while (file.hasNext()) {
                // Convert the word to lower case, trim it and insert into the set
                dictionary.add(file.next().trim().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find dictionary file");
        } finally {
            file.close();
        }
        return dictionary;
    }

    //count number of words found in dictionary
    public static int evaluateMetric(String input) {
        //split String by space, punctuation
        String[] splitWords = input.split("[\\p{Punct}\\s]+");
        int match = 0;

        for (String s: splitWords) {
            if (getDictionary().contains(s)) {
                match++;
            }
        }
        return match;
    }

    //return the keys that seem to output most words than the rest
    public static List<Integer> heuristicCracker(char[] cipherText) {
        int[] matchesPerKeyArray = new int[numOfChars];
        for (int i = 0; i < numOfChars; i++) {
            char[] clear = ceasar(cipherText, numOfChars - i);
            matchesPerKeyArray[i] = evaluateMetric(String.valueOf(clear));
        }
        //find keys with most matches
        int max = Arrays.stream(matchesPerKeyArray).max().getAsInt();

        List<Integer> possibleKeys = new ArrayList<Integer>();
        for (int i = 0; i < numOfChars; i++) {
            if (matchesPerKeyArray[i] == max) {
                possibleKeys.add(i);
            }
        }
        return possibleKeys;
    }
}
