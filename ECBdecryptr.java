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

    /* Method to map key values from known cipher and plain text */
    
    private static char[] keyMap(String ciphr, String plain)
    {
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

    public static void main(String[] args) {

        // Set content of first two text files to strings
        String cipherOne = ""; 
        String plainOne = ""; 
        cipherOne = readTxt("ECB_c1.txt");
        plainOne = readTxt("ECB_p1.txt");

        // Get key from text files
        char[] cipherKey = keyMap(cipherOne, plainOne); 

        // Set content of second cipher as string
        String cipherTwo = "";
        cipherTwo = readTxt("ECB_c2.txt");
        
        // To store decrypted message 
        char[] plainTwo = new char[cipherTwo.length()]; 
        
        // Convert second cipher into plainOne (Decrypt)
        for (int i=0; i < cipherTwo.length(); i++) // Cycle through length of plainOne
        {
            for (int j=0; j < 26; j++) 
            {
                // When ALPHABET matches plainOne char, assign to key
                if (cipherTwo.charAt(i) == cipherKey[j])
                {
                    plainTwo[i] = ALPHABET.charAt(j);
                }
            }
        }

        // Convert chars to string and print decrypted message
        String plainTwoString = new String(plainTwo);
        System.out.println(plainTwoString);
        for (int j=0; j < 26; j++)
        {
            System.out.println(ALPHABET.charAt(j) + " -> " + cipherKey[j]);
        }
    }   
}