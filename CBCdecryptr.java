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
            for (int k=0; k < 25; k++)  {
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

        // System.out.println(cipherOne.charAt(0));
        // System.out.println(plainOne.charAt(0));

        // Get key from text files
        char[] cipherKey = new char[27]; //keyMap(cipherOne, plainOne); 
        int[] indexes = new int[1000];
                
        // When ALPHABET matches first letter, add to key and initialise index array
        for (int j=0; j < 26; j++)  {
                if(plainOne.charAt(0) == ALPHABET.charAt(j)) {
                    cipherKey[j+2] = cipherOne.charAt(0);
                    indexes[0] = getIndex(cipherOne, 0);
                }
        }

        for(int i=0; i < plainOne.length(); i++) {

            if(i!=0) {  
                for (int j=0; j < 26; j++) {
                        int temp;
                        if(plainOne.charAt(i) == ALPHABET.charAt(j)) {
                            if ((j + indexes[i-1]) > 26){
                                temp = (j + indexes[i-1]) % 26; 
                            }
                            else {
                                temp = (j + indexes[i-1]);
                            }
                            cipherKey[temp] = cipherOne.charAt(i);
                            indexes[i] = getIndex(cipherOne, i);
                        }            
                }
            
            }
        
        }

        

        // System.out.println(cipherKey[21] + " " + cipherKey[2]);
        for (int j=0; j < 26; j++) 
            {
                System.out.println(ALPHABET.charAt(j) + " " + cipherKey[j]);
            }

        // Set content of second cipher as string
        String cipherTwo = "";
        cipherTwo = readTxt("CBC_iv=22_c2.txt");
    }
    

}