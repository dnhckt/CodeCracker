import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
    Author: Aidan Hackett
    Decrypts a CBC encrypted cipher from a plaintext, cipher file and iv with the same key and a different iv

     javac CBCdecryptr.java && java CBCdecryptr
 */
public class CBCdecryptr {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    String cipherOne = new String();
    String plainOne = new String();

    char[] cipherKey = new char[26];

    String cipherTwo = new String();
    String plainTwo = new String();

    int iv1, iv2;

    CBCdecryptr(String cA, String pA, String cB, int iv1, int iv2) {
        this.cipherOne = readTxt(cA);
        this.plainOne = readTxt(pA);
        this.cipherTwo = readTxt(cB);

        this.iv1 = iv1;
        this.iv2 = iv2;

        this.cipherKey = keyMap(this.cipherOne, this.plainOne, this.iv1);
        this.plainTwo = crackCipher(this.cipherTwo, this.cipherKey, this.iv2);
    }
    /* Method to convert txt files into String */
    private static String readTxt(String path) {

        String text = ""; 
        // Set string to specified txt.file 
        try {
                text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException error) {
            error.printStackTrace(); // Print error to console log
        }
        return text; // Return txt String
    }
    /* Method to set indexes for each subsequent block */
    private static int getIndex(String cipher, int letterPos) {
            int index = 0;
            for (int k=0; k < 26; k++)  {
                if (cipher.charAt(letterPos) == ALPHABET.charAt(k)) {
                     index = k;
                }
            }
            return index;
    }

    private static char[] keyMap(String ciphr, String plain, int iv) {

        char[] cipherKey = new char[26]; // store the key
        int[] indexes = new int[plain.length()]; // Store index array in length of cipher

        for (int j = 0; j < 26; j++) {
            if (plain.charAt(0) == ALPHABET.charAt(j)) { // When ALPHABET matches first letter
                cipherKey[j + iv] = ciphr.charAt(0); // Add to key with IV
                indexes[0] = getIndex(ciphr, 0); // Initialise index array
            }
        }

        for (int i = 1; i < plain.length(); i++) {
                   
            for (int j = 0; j < 25; j++) {
                        int temp;
                        if (plain.charAt(i) == ALPHABET.charAt(j)) {

                            if ((j + indexes[i - 1]) >= 26) {
                                temp = (j + indexes[i - 1]) % 26;
                            } else {
                                temp = (j + indexes[i - 1]);
                            }

                            cipherKey[temp] = ciphr.charAt(i);
                            indexes[i] = getIndex(ciphr, i);
                        }
                    }
        }
        return cipherKey;
    }

    private static String crackCipher(String ciphr, char[] key, int iv) {
        
        char[] plain = new char[ciphr.length()];
        int[] indexes = new int[ciphr.length()];

        for (int j = 0; j < 26; j++) {
            if (ciphr.charAt(0) == key[j]) { // When ALPHABET matches first letter
                plain[0] = ALPHABET.charAt(Math.floorMod(j - iv, 26));
                indexes[0] = getIndex(ciphr, 0);
            }
        }

        for (int i = 1; i < ciphr.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (ciphr.charAt(i) == key[j]) {
                    plain[i] = ALPHABET.charAt(Math.floorMod((j - indexes[i - 1]), 26));
                    indexes[i] = getIndex(ciphr, i);
                }
            }
        }
            String plainString = new String(plain);
            return plainString;
    }

    public void printCrackedKey() {
        for (int j=0; j < 26; j++) {
           System.out.println(ALPHABET.charAt(j) + " -> " + cipherKey[j]);
        }
    }

    public String returnCrackedText() {
        return plainTwo;
    }

}