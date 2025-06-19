import java.util.Scanner;   
   
public class MonoAlphabitec   
{   
	 public static final String alphabetic = "abcdefghijklmnopqrstuvwxyz";

	  public static String encrypt(String pt, int shiftKey) {
		  
	    pt = pt.toLowerCase();
	    String encryptedText = "";
	    
	    for (int i = 0; i < pt.length(); i++) {
	    	
	      int charPos = alphabetic.indexOf(pt.charAt(i));
	      
	      int key = (shiftKey + charPos) % 26;
	      
	      char replace = alphabetic.charAt(key);
	      encryptedText += replace;
	      
	      
	    }
	    System.out.println("-2 For each letter in the plaintext:\n\nWe're gonna Find its position in the alphabet and add the shift value to this position.\n");
	       System.out.println("-3 Replace the resulting positions with their corresponding letters in the alphabet.\n");
	       System.out.println("-4 Finally Combine all the encrypted letters to form the final ciphertext:\n\nSo the CipherText for your Plaintext is:\n");
	    return encryptedText;
	  }
      
	  public static String decrypt(String ct, int shiftKey) {
		  
		  ct = ct.toLowerCase();
		    String pt = "";
		    for (int i = 0; i < ct.length(); i++) {
		    	
		      int charPosition = alphabetic.indexOf(ct.charAt(i));
		      
		      int key = (charPosition - shiftKey) % 26;
		      
		      if (key < 0) {
		        key = alphabetic.length() + key;
		        
		      }
		      char replace = alphabetic.charAt(key);
		      pt += replace;
		    }
		    System.out.println("-2 For each letter in the CipherText:\n\nWe're gonna Find its position in the alphabet and Subtract the shift value from this position.\n");
		       System.out.println("-3 Replace the resulting positions with their corresponding letters in the alphabet.\n");
		       System.out.println("-4 Finally Combine all the PlainText letters:\n\nSo the PlainText for your CipherText is:\n");
		    return pt;
		  }
      
    public static void main(String[] args)   
    {   
       Scanner input = new Scanner(System.in);
       System.out.println("Welcome to Monoalphabetic Cipher\n\nA MonoAlphabetic cipher is a type of substitution cipher where each letter in the plaintext is shifted\na fixed number of places down or up the alphabet..\n ");
       int num =-1;
		do { System.out.println("Choose 1 For Encryption, 2 for decryption and 0 To Exit");
		num = input.nextInt();
		if(num == 1) {
       System.out.println();
       System.out.println("Encryption Steps:\n\n-1 At first kindly Enter a Plaintext:");
       String pt = input.next();
       System.out.println();
       System.out.println("-2 choose a positive Number that shifts each character in the plainText:\n");
       int shifKey = input.nextInt();
       System.out.println();
       System.out.println(encrypt(pt, shifKey));;
       System.out.println();
		}
		else if (num == 2) {
       System.out.println("Decryption Steps:\n\n-1 At first kindly Enter The CipherText you want to decrypt:");
       String ct = input.next();
       System.out.println();
       System.out.println("-2 Enter the Number that was Used in the Encryption proccess that will shifts each character in the CipherText:\n");
       int shiftKey = input.nextInt();
       System.out.println();
       System.out.println(decrypt(ct, shiftKey));;
       System.out.println();
		}else if (num == 0) {
			System.out.println("Bye :)");
		
		}
		else {
			System.out.println();
			System.out.println("Invalid input! Please choose again");
			System.out.println();
		}
		} while(num != 0);

       
       
    }   
}  