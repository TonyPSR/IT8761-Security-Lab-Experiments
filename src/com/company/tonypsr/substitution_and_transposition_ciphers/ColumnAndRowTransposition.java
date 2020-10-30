package com.company.tonypsr.substitution_and_transposition_ciphers;

import java.util.Scanner;

public class ColumnAndRowTransposition {
    public static int[] getAlphabetsIndexOrderMapFromKey(String keyString){
        int[] order = new int[keyString.length()];
        int[] indexOrder = new int[keyString.length()];

        int counter = 0;
        for(char c='0'; c<='9'; c++){
            for(int j=0; j<order.length; j++){
                if(keyString.charAt(j) == c) {
                    order[j] = counter;
                    counter++;
                }
            }
        }

        for(char c='a'; c<='z'; c++){
            for(int j=0; j<order.length; j++){
                if(keyString.charAt(j) == c) {
                    order[j] = counter;
                    counter++;
                }
            }
        }


        for(int i=0; i<order.length; i++){
            indexOrder[order[i]] = i;
        }

        return indexOrder;
    }

    public static char[][] getMatrixFromPlainText(String plainText, int n){
        char[][] matrix = new char[plainText.length()%n==0?plainText.length()/n:(plainText.length()/n+1)][n];

        int stringPtr = 0;
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(stringPtr < plainText.length()) {
                    matrix[i][j] = plainText.charAt(stringPtr);
                    stringPtr++;
                }else {
                    // dummy characters
                    matrix[i][j] = '-';
                }
            }
        }
        return matrix;
    }

    public static char[][] getMatrixFromCipherText(String cipherText, int n, int[] order){
        char[][] matrix = new char[cipherText.length()%n==0?cipherText.length()/n:(cipherText.length()/n+1)][n];

        int stringPtr = 0;
        for(int position: order){
            for(int i=0; i<matrix.length; i++){
                if(stringPtr < cipherText.length()) {
                    matrix[i][position] = cipherText.charAt(stringPtr);
                    stringPtr++;
                }else {
                    // dummy characters
                    matrix[i][position] = '-';
                }
            }
        }
        return matrix;
    }

    public static String encrypt(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();

        int[] orderIndex = getAlphabetsIndexOrderMapFromKey(key);

        char[][] matrix = getMatrixFromPlainText(plainText, key.length());

        for(int position: orderIndex){
            for(int i=0; i<matrix.length; i++){
                cipherText.append(matrix[i][position]);
            }
        }

        return cipherText.toString();
    }


    public static String decrypt(String cipherText, String key){
        StringBuilder plainText = new StringBuilder();

        int[] order = getAlphabetsIndexOrderMapFromKey(key);

        char[][] matrix = getMatrixFromCipherText(cipherText, key.length(), order);


        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                if(matrix[i][j] != '-') {
                    plainText.append(matrix[i][j]);
                }
            }
        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nColumn And Row Transposition Cipher\n");

        System.out.println("Enter the message: (A-Z, a-z, 0-9)");
        String message = sc.nextLine();

        System.out.println("Enter the key: (a-z,A-Z,0-9) (no spacing)");
        String key = sc.next().toLowerCase();

        if(key.length() >= message.length()){
            System.out.println("Key length must be smaller than message length");
            System.exit(0);
        }

        System.out.println();

        String cipherText = encrypt(message, key);
        System.out.println("Cipher Text: " + cipherText);
        String plainText = decrypt(cipherText, key);
        System.out.println("Plain Text: " + plainText);
    }
}

