package com.company.tonypsr.substitution_and_transposition_ciphers;

import java.util.ArrayList;

public class HillCipher {

    public static ArrayList<String> splitMessage(String message){
        ArrayList<String> splittedMessage = new ArrayList<>();

        for(int i=0; i<message.length()-1; i+=2){
            splittedMessage.add(message.charAt(i) + "" + message.charAt(i+1));
        }

        return splittedMessage;
    }


    public static int[][] multiply(int[][] a, int[][] b){
        int[][] c = new int[a.length][b[0].length];

        for(int i=0; i<a.length; i++){
            for(int j=0; j<b[0].length; j++){
                for(int k=0; k<b.length; k++){
                    c[i][j] += (a[i][k]*b[k][j]);
                }
            }
        }

        return c;
    }

    public static int[][] stringToColumnVector(String text){
        int[][] colVector = new int[text.length()][1];

        for(int i=0; i<text.length(); i++){
            colVector[i][0] = text.charAt(i) - 'a';
        }

        return colVector;
    }

    public static String encrypt(String plainText, int[][] key){
        StringBuilder cipherText = new StringBuilder();

        ArrayList<String> messageSplit = splitMessage(plainText);

        for(String letterPair: messageSplit){
            int[][] c = multiply(key, stringToColumnVector(letterPair));

            for(int i=0; i<c.length; i++){
                cipherText.append((char)(c[i][0]%26+'a'));
            }
        }



        return cipherText.toString();
    }

    public static void main(String[] args) {
        int keyLenght = 2;

        int[][] key = {
                {3,3},
                {2,5}
        };

        String message = "helloo";

        System.out.println(encrypt(message, key));


    }
}
