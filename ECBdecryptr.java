import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
    Author: Aidan Hackett
    Decrypts an ECB encrypted cipher from a plaintext and cipher file with the same key 

     javac ECBdecryptr.java && java ECBdecryptr
 */

public class ECBdecryptr {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    String cipherOne = new String();
    String plainOne = new String();
    
    char[] cipherKey =  new char[26];
    
    String cipherTwo = new String();
    String plainTwo = new String();

    ECBdecryptr(String cA, String pA, String cB) {
            this.cipherOne = readTxt(cA);
            this.plainOne = readTxt(pA);
            this.cipherTwo = readTxt(cB);

            this.cipherKey = keyMap(this.cipherOne, this.plainOne);
            this.plainTwo = crackCipher(this.cipherTwo, this.cipherKey);  
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

    /* Method to map key values from known cipher and plain text */
    private static char[] keyMap(String ciphr, String plain) {

        char[] key = new char[26]; // Initialise key array

        // Map each letter of the key into cipherBet
        for (int i=0; i < plain.length(); i++) // Cycle through plaintext chars
        { 
            for (int j=0; j < ALPHABET.length(); j++)  // Cycle through ALPHABET chars
            {
                // When ALPHABET matches plain text char 
                if (plain.charAt(i) == ALPHABET.charAt(j))
                {
                    key[j] = ciphr.charAt(i); // Assign to key
                }
            }
        }
        return key;
    }

    private static String crackCipher(String ciphr, char[] key) {

        char[] plainTxt= new char[ciphr.length()];
        // Convert second cipher into plainOne (Decrypt)
        for (int i = 0; i < ciphr.length(); i++) { // Cycle through length of plainOne
                for (int j = 0; j < 26; j++) {
                    // When ALPHABET matches plainOne char, assign to key
                    if (ciphr.charAt(i) == key[j]) {
                            plainTxt[i] = ALPHABET.charAt(j);
                    }
                }
        }
            String plainString = new String(plainTxt);
            return plainString;
    }

    public void printCrackedKey() {
        // Print ECB Key
        for (int j = 0; j < 26; j++) {
            System.out.println(ALPHABET.charAt(j) + " -> " + cipherKey[j]);
        }

    }

    public String returnCrackedText() {
        return plainTwo;
    }

}