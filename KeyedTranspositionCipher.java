import java.util.Scanner;

public class KeyedTranspositionCipher {

    private String text;
    private String keyInput;
    private int[] key;

    // Constructor
    public KeyedTranspositionCipher(String text, String keyInput) {
        this.text = text.replace(" ", ""); // Remove spaces

        // Check if the text is empty after removing spaces
        if (this.text.isEmpty()) {
            System.out.println("Error: The text is empty after removing spaces, Please enter a valid text.");
            System.exit(1);
        }

        this.keyInput = keyInput;
        this.key = new int[keyInput.length()];  // Define an array of the key size
        for (int i = 0; i < keyInput.length(); i++) {
            this.key[i] = Character.getNumericValue(keyInput.charAt(i)); // Convert String to int
        }

        // Check for duplicate key values
        for (int i = 0; i < key.length; i++) {
            for (int j = i + 1; j < key.length; j++) {
                if (key[i] == key[j]) {
                    System.out.println("Error: Duplicate key values are not allowed");
                    System.exit(1);
                }
            }
        }
    }

    // Method to explain the cipher process
    public void explainCipher() {
        System.out.println("You entered the text: \"" + text + "\" and the key: \"" + keyInput + "\".");
        System.out.println("The Keyed Transposition Cipher will encrypt your text by rearranging its characters into blocks.");
        System.out.println("The size of each block is determined by the length of the key, which in this case is: " + keyInput.length() + ".");
        System.out.println("Each block's characters will be reordered based on the positions specified by your key: \"" + keyInput + "\".");
    }

    // Encryption method
    public String encrypt() {
        int blockSize = key.length;
        int totalChars = text.length();
        int totalLength = (totalChars % blockSize == 0) ? totalChars : totalChars + (blockSize - (totalChars % blockSize));

        char[] textArray = new char[totalLength];

        for (int i = 0; i < totalLength; i++) {
            if (i < totalChars) {
                textArray[i] = text.charAt(i);
            } else {
                textArray[i] = 'Z';  // Padding with 'Z'
            }
        }

        String encryptedText = "";

        for (int i = 0; i < totalLength; i += blockSize) {
            char[] encryptedBlock = new char[blockSize];
            for (int j = 0; j < blockSize; j++) {
                int newL = key[j] - 1;
                if (newL >= 0 && newL < blockSize) {
                    encryptedBlock[j] = textArray[i + newL];
                } else {
                    System.out.println("Error: Invalid key value at position " + (j + 1));
                    return "";
                }
            }
            encryptedText += new String(encryptedBlock);
        }

        return encryptedText;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the text you want to encrypt:");
        String text = scanner.nextLine();

        System.out.println("Enter the key (Order for each block):");
        String keyInput = scanner.nextLine();

        KeyedTranspositionCipher cipher = new KeyedTranspositionCipher(text, keyInput);

        System.out.println("\nFinal encrypted text:");
        System.out.println(cipher.encrypt());
        System.out.println();

        cipher.explainCipher();
    }
}
