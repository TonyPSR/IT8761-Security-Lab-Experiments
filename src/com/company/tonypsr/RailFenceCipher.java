package com.company.tonypsr;

import java.util.Arrays;
import java.util.Scanner;

public class RailFenceCipher {
    public static String encrypt(char[] plainText, int n){
        StringBuilder cipherText = new StringBuilder();

        char[][] rails = new char[n][plainText.length];

        for(char[] rail: rails){
            Arrays.fill(rail, '#');
        }

        int row = 0;
        int col = 0;
        int rowOffset = -1;


        //Creating rails table
        for (char letter : plainText) {
            if (row == n - 1 || row == 0) {
                rowOffset = -rowOffset;
            }
            rails[row][col] = letter;
            row += rowOffset;
            col += 1;
        }

        System.out.println("Encryption Rails:");
        for(int i=0; i<rails.length; i++){
            for(int j=0; j<rails[0].length; j++){
                System.out.print(rails[i][j] + " ");
                if(rails[i][j]!='#')
                    cipherText.append(rails[i][j]);
            }
            System.out.println();
        }


        return cipherText.toString();
    }

    public static String decrypt(char[] cipherText, int n){
        StringBuilder plainText = new StringBuilder();

        char[][] rails = new char[n][cipherText.length];

        for(char[] rail: rails){
            Arrays.fill(rail, '#');
        }

        int row = 0;
        int col = 0;
        int rowOffset = -1;

        //Creating rails table
        for (int i=0; i<cipherText.length; i++) {
            if (row == n - 1 || row == 0) {
                rowOffset = -rowOffset;
            }
            rails[row][col] = '_';
            row += rowOffset;
            col += 1;
        }

        int x = 0;
        for(int i=0; i<rails.length; i++){
            for(int j=0; j<rails[0].length; j++) {
                if (rails[i][j] == '_') {
                    rails[i][j] = cipherText[x];
                    x++;
                }
            }
        }

        System.out.println("Decryption Rails:");
        for(int i=0; i<rails.length; i++){
            for(int j=0; j<rails[0].length; j++){
                System.out.print(rails[i][j] + " ");
            }
            System.out.println();
        }

        row = 0;
        col = 0;
        rowOffset = -1;

        //Creating rails table
        for (int i=0; i<cipherText.length; i++) {
            if (row == n - 1 || row == 0) {
                rowOffset = -rowOffset;
            }
            plainText.append(rails[row][col]);
            row += rowOffset;
            col += 1;
        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the message: ");
        char[] message = sc.nextLine().toCharArray();
        System.out.println("Enter the no. of rails: ");
        int n = sc.nextInt();

        String cipherText = encrypt(message, n);
        System.out.println("Cipher Text: " + cipherText);

        System.out.println();

        String plainText = decrypt(cipherText.toCharArray(), n);
        System.out.println("Plain Text: " + plainText);


    }
}
