import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
    Author: Aidan Hackett
    Decrypts an ECB encrypted cipher from a plaintext and cipher file with the same key 

     javac CBCdecryptr.java && java CBCdecryptr
 */
public class CBCdecryptr {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    
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
    private static int getIndex(String cipher, int letterPos) {
            int index = 0;
            for (int k=0; k < 26; k++)  {
                if (cipher.charAt(letterPos) == ALPHABET.charAt(k)) {
                     index = k;
                }
            }
            return index;
    }

    public static void main(String[] args) {

        // Set content of first two text files to strings
        String cipherOne = ""; 
        String plainOne = ""; 

        cipherOne = readTxt("CBC_iv=2_c1.txt");
        plainOne = readTxt("CBC_iv=2_p1.txt");
        
        int iv = 2;
        int iv2 = 22;

        // System.out.println(cipherOne.charAt(0));
        // System.out.println(plainOne.charAt(0));

        char[] cipherKey = new char[26]; // store the key
        int[] indexes = new int[plainOne.length()]; // Store index array in length of cipher
                
        for (int j=0; j < 26; j++)  {
                if(plainOne.charAt(0) == ALPHABET.charAt(j)) { // When ALPHABET matches first letter
                    cipherKey[j+iv] = cipherOne.charAt(0);  // Add to key with IV 
                    indexes[0] = getIndex(cipherOne, 0); // Initialise index array
                }
        }

        for(int i=1; i < plainOne.length(); i++) { 
                for (int j=0; j < 25; j++) {
                        int temp;
                        if(plainOne.charAt(i) == ALPHABET.charAt(j)) {
                            
                            if ((j + indexes[i-1]) >= 26){
                                temp = (j + indexes[i-1]) % 26; 
                            } else { temp = (j + indexes[i-1]); }
                            
                            cipherKey[temp] = cipherOne.charAt(i);
                            indexes[i] = getIndex(cipherOne, i);
                        }            
                }
        }

        for (int j=0; j < 26; j++) 
        {
            System.out.println(ALPHABET.charAt(j) + " -> " + cipherKey[j]);
        }

        // Set content of second cipher as string
        String cipherTwo = "";
        cipherTwo = readTxt("CBC_iv=22_c2.txt");
        char[] plainTwo = new char[cipherTwo.length()];
        int[] indexesTwo = new int[cipherTwo.length()];

        for (int j=0; j < 26; j++){
            if(cipherTwo.charAt(0) == cipherKey[j]) { // When ALPHABET matches first letter
                plainTwo[0] = ALPHABET.charAt(Math.floorMod(j-iv2, 26));
                indexesTwo[0] = getIndex(cipherTwo, 0);
            }
        }

        for(int i=1; i < cipherTwo.length(); i++) {  
                for (int j=0; j < 26; j++) {
                        if(cipherTwo.charAt(i) == cipherKey[j]) {
                            plainTwo[i] = ALPHABET.charAt(Math.floorMod((j-indexesTwo[i-1]), 26));
                            indexesTwo[i] = getIndex(cipherTwo, i);
                        }            
                }
        }

        // Print decrypted message
        String plainTwoString = new String(plainTwo);
        System.out.println(plainTwoString);

    }
}