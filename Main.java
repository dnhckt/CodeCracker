/*
    Author: Aidan Hackett
    Mainfile for ECBdecryptr && CBCdecryptr

    Need to: 
    Add comments to all 
    Take user input + validate + provide output 
    Change printing of crackedText to return to a text file 
    Make jar

 */

public class Main {
    public static void main(String[] args) {
            ECBdecryptr ECB = new ECBdecryptr("ECB_c1.txt", "ECB_p1.txt", "ECB_c2.txt");
            ECB.printCrackedKey();
            System.out.println(ECB.returnCrackedText());

            CBCdecryptr CBC = new CBCdecryptr("CBC_iv=2_c1.txt", "CBC_iv=2_p1.txt", "CBC_iv=22_c2.txt", 2, 22);
            
            CBC.printCrackedKey();
            System.out.println(CBC.returnCrackedText());

        }

}