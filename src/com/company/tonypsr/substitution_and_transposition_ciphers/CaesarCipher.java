package com.company.tonypsr.substitution_and_transposition_ciphers;

import java.util.Arrays;
import java.util.Scanner;

public class CaesarCipher {

    public static char[] encrypt(char[] plainText, int n){
        char[] cipherText = new char[plainText.length];
        for(int i=0; i<plainText.length; i++){
            int offset = Character.isUpperCase(plainText[i])?65:97;
            if(plainText[i] != ' ') {
                cipherText[i] = (char) ((plainText[i] + n - offset) % 26 + offset);
            } else {
                cipherText[i] = ' ';
            }
        }

        return cipherText;
    }

    public static char[] decrypt(char[] cipherText, int n){
        char[] plainText = new char[cipherText.length];
        for(int i=0; i<cipherText.length; i++){
            int offset = Character.isUpperCase(cipherText[i])?65:97;
            if(cipherText[i] != ' ') {
                plainText[i] = (char) ((cipherText[i] + 26 - n - offset) % 26 + offset);
            } else {
                plainText[i] = ' ';
            }
        }

        return plainText;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char[] message;
        int n;

        //creating char array from string input
        System.out.println("Enter the message: ");
        message = sc.nextLine().toCharArray();
        System.out.println("Enter the value of n: ");
        n = sc.nextInt();

        //encryption - input plain text
        String cipherText = String.valueOf(encrypt(message, n));
        System.out.println("Encrypted Text: " + cipherText);

        //decryption - input encrypted cipher text
        String plainText = String.valueOf(decrypt(cipherText.toCharArray(), n));
        System.out.println("Decrypted Text: " + plainText);

    }
}
