# ClassicCrypt

A Java-based educational project demonstrating the implementation of classical encryption algorithms. This project is designed to help students understand the inner workings of widely known ciphers through practical coding.

## 🔐 Implemented Ciphers

- **Keyed Transposition Cipher**  
  Rearranges the characters of the plaintext based on a given keyword.

- **Monoalphabetic Cipher**  
  A substitution cipher where each letter of the plaintext is mapped to a different letter.

- **Vigenère Cipher**  
  A method of encrypting text using a series of Caesar ciphers based on the letters of a keyword.

- **Playfair Cipher**  
  Encrypts pairs of letters using a 5x5 key matrix.

- **DES (Data Encryption Standard)**  
  Included for comparison. A block cipher that encrypts data in 64-bit blocks using a 56-bit key.

## 📁 Project Structure

ClassicCrypt/
├── EncryptionDES.java
├── KeyedTranspositionCipher.java
├── MainClass.java
├── MonoAlphabitec.java
├── playfairCypher.java
└── Vigener.java

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/ClassicCrypt.git
   cd ClassicCrypt
Compile the Java files:
javac *.java
Run the main class:
java MainClass

This project is intended purely for academic and learning purposes. The algorithms presented here should not be used for securing sensitive data in real-world applications.
