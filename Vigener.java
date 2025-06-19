import java.util.Scanner;

public class Vigener {

	
	public static char[][] tableau = new char[26][26];
	public static String keyword = ("");
	
	static Scanner input = new Scanner(System.in);
	


    static void makeTableau() {
    	System.out.println("Welcome To Vigenère Cipher\n\nIn This Vigenère cipher method we will be encrypting alphabetic text\nusing Vigenère Table and Here's how it works!:\n");
		System.out.println("The Vigenère table consists of 26 rows and 26 columns, with each row corresponding\nto a letter of the alphabet. The first row starts with A, the second with B\n(shifted one position to the left), and so on, until the last row starts with Z\n");

        for(int i=0;i<26;i++) {
    
            for(int j=i;j<26+i;j++) {
                int a = j+65;
                if (a>90) {
                	tableau[i][j-i] = (char)(a%90+64);
                }
                else {
                	tableau[i][j-i] = (char)a;

                }

            }

        }

    }
	
	static void makeyKeyword (String pt,String kw) {
		
		while (!kw.equals((String) kw)) {
			System.out.println("Invalid Input!");
			Scanner input = new Scanner(System.in);
			kw = input.nextLine();
		}
		pt =pt.toUpperCase().replaceAll(" ", "");
		kw =kw.toUpperCase().replaceAll(" ", "");
	char key[] = kw.toCharArray();
	
	for(int i=0;i<pt.length();i++) {
	
	    keyword = keyword + Character.toString(key[i%key.length]);
	   

	}
	 System.out.println(pt);
	 System.out.println(keyword);
	 
	}
	
	
	static String encrypt(String pt) {
		pt = pt.toUpperCase().replaceAll(" ", "");
		
	    char[] encpt1 = pt.toCharArray();
	    char[] charArrayKeyword = keyword.toCharArray();
	    String encpt2 = new String("");
	    for(int i=0;i<pt.length();i++) {
	        int j=charArrayKeyword[i];
	        int a = j-65;
	        int b = encpt1[i];
	        b = b-65;
	        encpt2 = encpt2 + Character.toString(tableau[a][b]);

	    }
		
		System.out.print("After Aligning The KeyWord To The PlainText We Can Now Use Vigenère Table In the Following Manner:\n\n");
		System.out.println("-1 For Each Letter in The Plaintext, Find The Intersection Of the Column\nCorresponding To The Plaintext Letter And The Row Corresponding To The KeyWord Letter\n");
		System.out.println("For Example;\nH (Row 7) And K (Column 10) Gives You R.\n");
		System.out.println("-2 Combine All The Intersected Letters To Get The Cipher Text");
		System.out.println();
		
		System.out.println("So The Cipher Text For The Entered PlainText is:\n");
	    return encpt2;
	}
	
	
	static String decrypt(String encpt) {
		encpt = encpt.toUpperCase().replaceAll(" ", "");
	    char[] decpt1 = keyword.toCharArray();
	    char[] decpt2 = encpt.toCharArray();
	    String decpt3 = new String("");
	    for(int k=0; k<keyword.length(); k++) {
	        int i=decpt1[k],j=0,b=0;
	        i = i-65;
	        for(j=0;j<26;j++) {
	            if (tableau[i][j]==decpt2[k]) {
	                b=j;
	                break;
	            }
	        }
	        decpt3 = decpt3 + Character.toString((char)(b+65));
	    }
	    System.out.println("-2 For each letter in the ciphertext, find the row corresponding to the keyword letter.\nIn that row, locate the ciphertext letter to find the corresponding plaintext letter:");
		System.out.println("For Example:\nIn the K row, find R → the corresponding plaintext letter is H ");
		System.out.println();
		System.out.println("-3 Combine All Result");
		System.out.println();
		
	    return decpt3;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		makeTableau();
		int num =-1;
		do { System.out.println("Choose 1 For Encryption, 2 for decryption and 0 To Exit");
		num = input.nextInt();
		if(num == 1) {
		System.out.println("Encryption Steps:\n\n-1 At first kindly Enter a Plaintext: ");
	    Scanner input = new Scanner(System.in);
		String pt = input.nextLine();
		System.out.println("-2 Enter a Desired Keyword:");
		String kw = input.nextLine();
		System.out.println();
		System.out.println("-3 The Keyword Will Be Repeatedly Typed In A Cyclic Manner Aligning Them To The Whole PlainText,\nLet See An Example With Your PlainText & KeyWord!:\n");
		makeyKeyword(pt,kw);
		System.out.println();
		String encpt = encrypt(pt);
		System.out.println(encpt);
		System.out.println();
		System.out.println();
		}
		else if (num == 2) {
			
		System.out.println("Decryption Steps:\n");
		System.out.println();
		System.out.println("-1 At first kindly Enter The CipherText you want to decrypt:\n");
		Scanner input = new Scanner(System.in);
		String ct = input.nextLine();
		System.out.println("-2 Enter the used Keyword in the encryption Proccess: ");
		String kw = input.nextLine();
		System.out.println("-3 The Keyword Will Be Repeatedly Typed In A Cyclic Manner Aligning Them To The Whole CipherText\\n");
		System.out.println();
		makeyKeyword(ct,kw);
		String decpt = decrypt(ct);
		System.out.println(decpt);
		}
		else if (num == 0) {
			System.out.println("Bye :)");
		}
		else{
			System.out.println();
			System.out.println("Invalid input! Please choose again");
			System.out.println();
		}
		} while(num != 0);
	}

	        
	
	
	
	
	
}
