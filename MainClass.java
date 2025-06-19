import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        System.out.println("*****************************************");
        System.out.println("          Welcome to Cipher Program!     ");
        System.out.println("*****************************************");
        System.out.println("In this program, you can explore various encryption and decryption methods:");
        System.out.println("1. Keyed Transposition Cipher");
        System.out.println("2. Monoalphabetic Cipher");
        System.out.println("3. Vigener Cipher");
        System.out.println("4. Playfair Cipher");
        System.out.println("5. DES Cipher");
        System.out.println("6. Combined Monoalphabetic and Keyed Transposition Cipher");
        System.out.println("Please choose an option from the menu to get started!");
        System.out.println("*****************************************");
        System.out.println();


        do {
        	System.out.println();
            System.out.println("Choose a cipher:");
            System.out.println("1. Keyed Transposition Cipher");
            System.out.println("2. Monoalphabetic Cipher(encryption and decryption)");
            System.out.println("3. Vigener Cipher (encryption and decryption)");
            System.out.println("4. Playfair Cipher (encryption and decryption)");
            System.out.println("5. DES Cipher");
            System.out.println("6. Combined Monoalphabetic and Keyed Transposition Cipher");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Keyed Transposition Cipher
                    System.out.println("Enter the text you want to encrypt:");
                    String text = scanner.nextLine();
                    System.out.println("Enter the key (Order for each block):");
                    String keyInput = scanner.nextLine();
                    KeyedTranspositionCipher keyedCipher = new KeyedTranspositionCipher(text, keyInput);
                    System.out.println("Final encrypted text:");
                    System.out.println();
                    System.out.println(keyedCipher.encrypt());
                    keyedCipher.explainCipher();
                    break;

                case 2:
                    // Monoalphabetic Cipher
                    System.out.println("Welcome to Monoalphabetic Cipher");
                    int num = -1;
                    do {
                        System.out.println("Choose 1 For Encryption, 2 for Decryption, and 0 To Exit:");
                        num = scanner.nextInt();
                        scanner.nextLine(); 
                        if (num == 1) {
                            System.out.println("Enter a Plaintext:");
                            String pt = scanner.nextLine();
                            System.out.println("Choose a Shift Key:");
                            int shifKey = scanner.nextInt();
                            System.out.println("CipherText: " + MonoAlphabitec.encrypt(pt, shifKey));
                        } else if (num == 2) {
                            System.out.println("Enter a CipherText:");
                            String ct = scanner.nextLine();
                            System.out.println("Enter the Shiftkey that was Used in the Encryption proccess:");
                            int shiftKey = scanner.nextInt();
                            System.out.println("PlainText: " + MonoAlphabitec.decrypt(ct, shiftKey));
                        } else if (num == 0) {
                            System.out.println("Exiting Monoalphabetic Cipher...");
                            System.out.println();
                        } else {
                            System.out.println("Invalid input! Please choose again.");
                        }
                    } while (num != 0);
                    break;

                case 3:
                    // Vigener Cipher
                  Vigener.makeTableau();
                    int num2 = -1;
                    do {
                        System.out.println("Choose 1 For Encryption, 2 for Decryption and 0 To Exit:");
                        num2 = scanner.nextInt();
                        scanner.nextLine(); 
                        if (num2 == 1) {
                            System.out.println("Enter a Plaintext:");
                            String pt = scanner.nextLine();
                            System.out.println("Enter a Desired Keyword:");
                            String kw = scanner.nextLine();
                            System.out.println("The Keyword Will Be Repeatedly Typed In A Cyclic Manner Aligning Them To The Whole PlainText,\nLet See An Example With Your PlainText & KeyWord!:\n");
                            Vigener.makeyKeyword(pt, kw);
                            
                            String encpt = Vigener.encrypt(pt);
                            System.out.println(encpt);
                        } else if (num2 == 2) {
                            System.out.println("Enter The CipherText you want to decrypt:");
                            String ct = scanner.nextLine();
                            System.out.println("Enter the used Keyword in the encryption Process:");
                            String kw = scanner.nextLine();
                            System.out.println("-1 The Keyword Will Be Repeatedly Typed In A Cyclic Manner Aligning Them To The Whole CipherText\n");
                            Vigener.makeyKeyword(ct, kw);
                            System.out.println();
                            String decpt = Vigener.decrypt(ct);
                            System.out.println("So The decrypted PlainText is:\n" + decpt);
                        } else if (num2 == 0) {
                            System.out.println("Exiting Vigenère Cipher...");
                            System.out.println();
                        } else {
                            System.out.println("Invalid input! Please choose again.");
                        }
                    } while (num2 != 0);
                    break;


                case 4:
                    // Playfair Cipher
                    playfairCypher playfair = new playfairCypher();
                    System.out.println("Enter a keyword (no spaces):");
                    String keyword = scanner.nextLine();
                    playfair.setKey(keyword);
                    playfair.KeyGen();
                    System.out.println("Encrypt or Decrypt?");
                    String word = scanner.nextLine();
                    if (word.equalsIgnoreCase("encrypt")) {
                        System.out.println("Enter word to encrypt:");
                        String toEncrypt = scanner.nextLine().toLowerCase();
                        System.out.println("Encrypted: " + playfair.encryptMessage(toEncrypt));
                    } else {
                        System.out.println("Enter word to decrypt:");
                        String toDecrypt = scanner.nextLine().toLowerCase();
                        System.out.println("Decrypted: " + playfair.decryption(toDecrypt));
                        System.out.println();
                    }
                    break;
                    
                case 5:
                	//DES Cipher
                	System.out.println("Enter the text you want to encrypt:");
                	String text2 = scanner.nextLine();
                	System.out.println("Enter the key:");
                    String key = scanner.next();
                    EncryptionDES DES = new EncryptionDES();
                    DES.encrypt(text2, key);
                    break;
                    
                case 6:
                    System.out.println("Enter the text you want to encrypt:");
                    String Text3 = scanner.nextLine();

                    // Step 1: Monoalphabetic Cipher
                    System.out.println("Enter the shift key for Monoalphabetic Cipher:");
                    int KeyMono = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String monoEncryptedText = MonoAlphabitec.encrypt(Text3, KeyMono);
                    System.out.println("Text after Monoalphabetic Cipher: " + monoEncryptedText);

                    // Step 2: Keyed Transposition Cipher
                    System.out.println("Enter the key for Keyed Transposition Cipher:");
                    String KeyTran = scanner.nextLine();
                    KeyedTranspositionCipher transpositionCipher = new KeyedTranspositionCipher(monoEncryptedText, KeyTran);
                    String finalText = transpositionCipher.encrypt();
                    System.out.println("Final encrypted text (after both ciphers): " + finalText);
                    break;
               
                	
                	
                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
        
    }

}
