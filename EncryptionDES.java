import java.io.UnsupportedEncodingException;

public class EncryptionDES {
	 private String superKey // original key 64 bit  
	 , cipher //cipher in binary
     , hexCipher //cipher in hexadecimal  
	 , permuteKey // key after permutation 56 bit
	 , C0 , D0 // split permutation key 28 bit
	 , c [], d []
	 , cipherBlock // for explain
	 ;
	 private int PC1[] = {57,49,41,33,25,17,9,
			 			  1,58,50,42,34,26,18,
	 					  10,2,59,51,43,35,27,
	 					  19,11,3,60,52,44,36,
	 					  63,55,47,39,31,23,15,
	 					  7,62,54,46,38,30,22,
	 					  14,6,61,53,45,37,29,
	 					  21,13,5,28,20,12,4 };
	 
	 private int PC2[] = {14,17,11,24,1,5,
			 			  3,28,15,6,21,10,
			 			  23,19,12,4,26,8,
			 			  16,7,27,20,13,2,
			 			  41,52,31,37,47,55,
			 			  30,40,51,45,33,48,
			 			  44,49,39,56,34,53,
			 			  46,42,50,36,29,32 };
	 //Initial Permutation
	 private int IP[] = {58,    50,   42,    34,    26,   18,    10,    2,
			   			 60,    52,   44,    36,    28,   20,    12,    4,
			 			 62,    54,   46,    38,    30,   22,    14,    6,
			 			 64,    56,   48,    40,    32,   24,    16,    8,
			 			 57,    49,   41,    33,    25,   17,     9,    1,
			 			 59,    51,   43,    35,    27,   19,    11,    3,
			 			 61,    53,   45,    37,    29,   21,    13,    5,
			 			 63,    55,   47,    39,    31,   23,    15,    7};
	 
	 //Final Permutation
	 private int FP[] = {40,     8,   48,    16,    56,   24,    64,   32,
			 			 39,     7,   47,    15,    55,   23,    63,   31,
			 			 38,     6,   46,    14,    54,   22,    62,   30,
			 			 37,     5,   45,    13,    53,   21,    61,   29,
			 			 36,     4,   44,    12,    52,   20,    60,   28,
			 			 35,     3,   43,    11,    51,   19,    59,   27,
			 			 34,     2,   42,    10,    50,   18,    58,   26,
			 			 33,     1,   41,     9,    49,   17,    57,   25};
	 
	 private int EBit[] = {32,    1,     2,     3,     4,    5,
			 			   4,     5,     6,     7,     8,    9,
			 			   8,     9,    10,    11,    12,   13,
			 			   12,    13,   14,    15,    16,   17,
			 			   16,    17,   18,    19,    20,   21,
			 			   20,    21,   22,    23,    24,   25,
			 			   24,    25,   26,    27,    28,   29,
			 			   28,    29,   30,    31,    32,    1,};
	
	 
	 private int subkeys [][] // keys after permutation by PC2 (16 keys with 48 bit)
			 , data[] //64
			 ,  L[][], R[][];
	 
	 
	 private int numOfShifting[] = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
	 
	 
	 private int sTable [][][] ={
	            {
	                {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
	                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
	                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
	                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
	            },
	            {
	                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
	                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
	                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
	                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
	            },
	            {
	                {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
	                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
	                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
	                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
	            },
	            {
	                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
	                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
	                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
	                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
	            },
	            {
	                {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
	                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
	                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
	                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
	            },
	            {
	                {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
	                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
	                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
	                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
	            },
	            {
	                {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
	                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
	                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
	                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
	            },
	            {
	                {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
	                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
	                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
	                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
	            }
	        };
	
	 
	 private int P[] = {16,   7,  20,  21,
			 			29,  12,  28,  17,
			 			 1,  15,  23,  26,
			 			 5,  18,  31,  10,
			 			 2,   8,  24,  14,
			 			32,  27,   3,   9,
			 			19,  13,  30,   6,
			 			22,  11,   4,  25};
	 
	 
	 
	 public EncryptionDES() {
		superKey = "";
		cipher = "";
		hexCipher = "";
		}

	 
	public String pBox (int [] box, String origin){
		 String result = "";
		 for(int i = 0 ; i < box.length ; i++){
			 result+= origin.charAt(box[i] - 1);
		 }
		 return result;
	 }
	
	public int[] XOR (int [] a , int [] b){
		int xor[] = new int [a.length];
		for(int i = 0 ; i < a.length ; i++)
			xor[i] = a[i] ^ b[i];
		return xor;
	}
	
	public int[] sBox(int[] r){
		 
		 String firstAndLastBit = "";
		 String midBits = "";
		 int result [] = new int [32];
		 String res = "";
		 
		 for(int i = 0 , z = 0 ; i < r.length ; i++){
			 firstAndLastBit = "";
			 midBits = "";
			 
			 firstAndLastBit += String.valueOf(r[i]);
			 firstAndLastBit += String.valueOf(r[i + 5]);
			 i++;
			 
			 for(int j = 0 ; j < 4 ; j++)
				 midBits += String.valueOf(r[i++]);
			
			int firstAndLast = convertToDecimal(firstAndLastBit);
			int mid = convertToDecimal(midBits);
			
			int bin = sTable[z++][firstAndLast][mid];
			bin = convertToBinary(bin);
			
			String h = bin + "";
			switch (h.length()) {
            case 1:
                res += "000";
                res += h;
                break;
            case 2:
                res += "00";
                res += h;
                break;
            case 3:
                res += "0";
                res += h;
                break;
            case 4:
                res += h;
                break;
			}
		 }
		 
		 for(int i = 0 ; i < 32 ; i++)
			 result[i] = Integer.parseInt(res.charAt(i) + "");
		 
		 return result;
	 }
	
	public void generateSubKeys(String key){
		 
		 superKey = key;
		 permuteKey = ""; 
		 c = new String [17];
		 d = new String [17];
		 subkeys = new int [16][48];
		 
		// Step 1 : Permutation the superKey by PC1 
		permuteKey = pBox(PC1, superKey);
		
		// Step 2 : Split permuteKey to C0 and D0 
		
			C0 = permuteKey.substring(0, 28);
			D0 = permuteKey.substring(28, 56);
		 
			c[0] = C0;
			d[0] = D0;
	        
		// Step 3 : Shift C0 and D0 
		for(int i = 1 ; i < c.length ; i++){
			c[i] = "";
			d[i] = "";
			for(int j = 0 ; j < 28 ; j++){
				c[i] += c[i - 1].charAt((j + numOfShifting[i-1]) % 28);
                d[i] += d[i - 1].charAt((j + numOfShifting[i-1]) % 28);
			}
		}
		
		
		// Step 4 : combine c and d 
		// then Permutation by PC2 (48 bits)
		int CnDn [] = new int [56];
		
		for(int i = 1 ; i < 17 ; i++){
			int count = 0;
			for(int j = 0 ; j < 28 ; j++)
				CnDn [count++] = Integer.parseInt(c[i].charAt(j) + "");
			for(int j = 0 ; j < 28 ; j++)
				CnDn [count++] = Integer.parseInt(d[i].charAt(j) + "");
			for(int j = 0 ; j < 48 ; j++)
				subkeys[i - 1][j] = CnDn [PC2[j] - 1];
		}
		
	 }
	
	public int[] function(int [] r , int [] key){
		 int d[] = new int [48];
		 // Permutation and Expansion
		 for(int i = 0 ; i < 48 ; i++)
			 d[i] = r[EBit[i] - 1];
		 
		 
		 d = XOR(d, key);
		 d = sBox(d);
		 
		 int result [] = new int [32];
		 for(int i = 0 ; i < 32 ; i++)
			 result[i] = d[P[i] - 1];
		 
		 return result ;
	 }
	 
	 public void encrypt(String plainText , String key){
		 
		 if((key.matches("[0-9A-Fa-f]+")) && key.length() == 16)
			 generateSubKeys(hixToBinary(key));
		 else if(key.matches("[0,1]+") && key.length() == 64)
			 generateSubKeys(key);
		 else {
			 System.out.println("Wrong Key");
			 return ;
		 }
		 
		 
		plainText = utfToBin(plainText);
		 
		 int remainder = plainText.length() % 64;
			 
		 if(remainder != 0)
			 for(int i = 0 ; i < (64 - remainder) ; i++)
				 plainText += "0";
		// to encrypt null massage by add zeros
		if(plainText.length() == 0){
			System.out.println("No content");
			return;
		}
		 
		 int numOfBlocks = plainText.length() / 64;
		 String textBlocks[] = new String [numOfBlocks];
		 
		 for(int i = 0 ; i < numOfBlocks ; i++)
			 textBlocks[i] = plainText.substring(i * 64, (i * 64) + 64);
		 
			 
		 
		 for(int i = 0 ; i < numOfBlocks ; i++)
			 encryptBlock(textBlocks[i]);
			 
		 explain();
				
	 }
	 
	 public void encryptBlock(String Text){
		 
		cipherBlock = ""; 
		 
		 data = new int [64];
		 L = new int [17][32];
		 R = new int [17][32];
		 
		 
		 // Step 1 : Initial Permutation plain text by IP
		 for(int i = 0 ; i < 64 ; i++)
			 data[i] = Integer.parseInt(Text.charAt(IP[i] - 1) + "");
			 
	 
		 // Step 2 : Split the permuted massage to Left and Right
		 for(int i = 0 , j = 32 ; i < 32 ; i++){
			 L[0][i] = data[i];
			 R[0][i] = data[j++];
		 }
		 
		 
		 // Step 3 : 16 Rounds
		 for(int i = 1 ; i < 17 ; i++){
			 L[i] = R[i-1];
			 
			 int valueOfFunction [] = new int [32];
			 valueOfFunction = function(R[i - 1], subkeys[i - 1]);
			 R[i] = XOR(L[i - 1], valueOfFunction);
			 
				 
		 }
		 
		 // Step 4 : combine and reverse the order of L and R 
		 //then Final Permutation
		 String combine [] = new String [64];
		 for(int i = 0 , j = 32 ; i < 32 ; i++ , j++){
			 combine[i] = R[16][i] + "";
			 combine[j] = L[16][i] + "";
		 }
		 for(int i = 0 ; i < 64 ; i++)
			 cipherBlock += combine[FP[i] - 1] + "";
		 cipher += cipherBlock +" ";
		 hexCipher += binaryToHix(cipherBlock) + " ";
		 
	 }
	 
	 public static String utfToBin(String utf) {
		 
		 byte[] bytes = null;
			try {
				bytes = utf.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			String bin = "";
			for (int i = 0; i < bytes.length; i++) {
			     int value = bytes[i];
			     for (int j = 0; j < 8; j++)
			     {
			        bin += ((value & 128) == 0 ? 0 : 1);
			        value <<= 1;
			     }
			}
			return bin;
		}
	    
	 public int convertToDecimal (String str){
		 int x = Integer.parseInt(str);
		 double decimal = 0 ;
		 int i = 0;
		 while (x != 0){
			 decimal += (x % 10) * Math.pow(2,i );
			 x /= 10;
			 i++;
		 }
		 return (int) decimal;
	 }
	
	 public String binaryToHix(String str){
		 String hex = "";
	        for (int i = 0; i < str.length();) {
	            String bi = "";
	            for (int j = 0; j < 4; j++) {

	                bi += str.toCharArray()[i++];
	            }
	            int h = convertToDecimal(bi);
	            if (h >= 10) {
	                char x = (char) (h - 10 + 65);
	                hex += x;
	            } else {
	                hex += h;
	            }
	        }
	        return hex;
	 }
	 
	 public String hixToBinary(String hix){
		 String binary = "";
		 for(int i = 0 ; i < hix.length() ; i++){
			 String x = "";
			 x += hix.charAt(i);
			 String h = "";
	            if (x.toCharArray()[0] >= 65) {
	                h = convertToBinary(x.toCharArray()[0] - 65 + 10) + "";

	                binary += h;
	            } else {
	                h = convertToBinary(Integer.parseInt(x)) + "";
	                String res = "";
	                switch (h.length()) {
	                    case 1:
	                        res += "000";
	                        res += h;
	                        break;
	                    case 2:
	                        res += "00";
	                        res += h;
	                        break;
	                    case 3:
	                        res += "0";
	                        res += h;
	                        break;
	                    case 4:
	                        res += h;
	                        break;
	                }
	                binary += res;
	            }

	        }
	        return binary;
	    }
		 
	 public int convertToBinary (int n){
	        int binaryNumber = 0;
	        int digit = 1 ;
	        
	        while(n != 0){
	        	
	        	binaryNumber += (n % 2) * digit;
	        	n /= 2;
	        	digit *= 10;
	        }
	        return binaryNumber;
	 }
	 	  
	 public void explain(){
		System.out.println("Introduction:");
		System.out.println("DES (Data Encryption Standard) is a symmetric block cipher algorithm that was widely used for many years. \nIt encrypts data in 64-bits blocks using a 64-bits key. \nDES work on bits, or binary.");
		System.out.println("");
		
		System.out.println("How DES Works:");
		System.out.println("First: Generate SubKeys, each key is 48 bits long.");
		System.out.println("We have original key = "+ superKey +" have 64 bits");
		System.out.println("to get Subkeys we follow 4 step: \n");
		
		System.out.println("   Step 1 : We get permute Key = " + permuteKey + "\n   by permutation and Compression by PC-1.\n ");
		
		
		System.out.println("   Step2 : Spilt permute Key into left and right halves (C0 and D0):");
		System.out.println("   C0 = " + C0 + "\n   D0 = "+ D0 + "\n");
		
		
		System.out.println("   Step 3 : We create 16 blocks Cn and Dn. Each pair of blocks is formed from the previous pair by Circular Shifting to the left.");
		System.out.print("   using this pattern {" +numOfShifting[0] );
		for(int i = 1 ; i < numOfShifting.length ; i++)
			System.out.print("," +numOfShifting[i]);
		System.out.println("}");
		System.out.println("   we get: ");
		
		for(int i = 1; i < 17; i++) {
            	System.out.print("    C" + i + " = ");
                System.out.print(c[i] + "");
		
            System.out.print("    ");
            
            
            		System.out.print("    D" + i + " = ");
                System.out.print(d[i] + "");
            
            System.out.println();
        }
		System.out.println();
		
		System.out.println("   Step 4 : Combine pairs of Cn and Dn (56 bits for each pair), Then Permutation and Compression CnDn whit PC2 ");
		System.out.println("   Then we get 16 keys, each key has 48 bits :");
		for (int i = 0; i < 16; i++) {
            System.out.print("    k" + (i + 1) + " = ");
            for (int j = 0; j < 48; j++) {
                System.out.print(subkeys[i][j] + "");
            }
            System.out.println("");
        }
		
		System.out.println("------------------------------------------\n");
		System.out.println("After Generate SubKeys, we spilt the plaintext to blocks each block has 64 bits in Binary Formal (16 hexadecimal digits).");
		System.out.println("If there is a missing block, we add zeros at the end");
		System.out.println("for example 6C696E650D0A, we need add 4 zeros : 6C696E650D0A0000");
		System.out.println();
		System.out.println("Now, we encrypt each block alone \n to encypt block we follow 4 steps (We take last block for example):");
		System.out.println();
		System.out.println("Step 1 : Initial Permutation plaintext by IP table");
		System.out.print("To get : ");
		for(int i = 0 ; i < data.length ; i++)
			System.out.print(data[i]);
		System.out.println("\n");
		
		System.out.println("Step 2 : Split the permuted massage to Left and Right (L0 and R0) have 32 bits");
		System.out.print("L0 = ");
		for(int i = 0 ; i < L[0].length ; i++){
			System.out.print(L[0][i]);
		}
		System.out.println();
		System.out.print("R0 = ");
		for(int i = 0 ; i < R[0].length ; i++){
			System.out.print(R[0][i]);
		}
		System.out.println("\n");
		
		
		System.out.println("Step 3 : 16 Rounds, each round: \n We take the right side comes out unmodified and a copy of it enters the function \nThe output of the function enters xor with the left side");
		System.out.println("Then we make a swap so that the result of the Xor becomes the right side and the previous right side becomes the left side");
		System.out.println("To get the equations : \n 1-L(n) = R(n-1)            2-R(n) = L(n-1) + f(R(n-1),K(n)) \n     such that K(n) is key and 1 <= n <= 16 ");
		System.out.println("Steps Of The Function: ");
		System.out.println("  1- We Permutation and Expansion copy of right side (32 bits) by E-Bit Table (to get 48 bits)");
		System.out.println("  2- operation XOR with K(n)");
		System.out.println("  3- S-Box (substitution box) and Compression (to get 32 bits)");
		System.out.println("  4- Permutation by P Table");
		System.out.println("to get:");
		for (int i = 0; i < 17; i++) {

            System.out.print(" L" + i + " = ");
            for (int j = 0; j < L[i].length; j++) {

                System.out.print(L[i][j] + "");
            }
            
            System.out.print("            R" + i + " = ");
            for (int j = 0; j < R[i].length; j++) {

                System.out.print(R[i][j] + "");
            }

            System.out.println("");

        }
		System.out.println();
		
		
		System.out.println("Step 4 : After 16 Round, combine and reverse the order of L and R then Final Permutation by FP Table");
		System.out.println("We get for block : "+cipherBlock +" in binary \nand "+binaryToHix(cipherBlock)+" in hexadecimal");
		
		System.out.println("We repeat all the steps foe each block \n");
		System.out.println("Final result Ciphertext = " + hexCipher);
	 }
}