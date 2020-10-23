package com.company;

import java.util.Scanner;

public class CaesarCipher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char[] message;
        int n;

        //creating char array from string input
        System.out.println("Enter the message: ");
        message = sc.nextLine().toCharArray();
        System.out.println("Enter the value of n: ");
        n = sc.nextInt();

        //encryption
        for(int i=0; i<message.length; i++){
            int offset = Character.isUpperCase(message[i])?65:97;
            if(message[i] != ' ') {
                message[i] = (char) ((message[i] + n - offset) % 26 + offset);
            }
        }

        System.out.println("Encrypted Text: " + String.valueOf(message));

        //decryption
        for(int i=0; i<message.length; i++){
            int offset = Character.isUpperCase(message[i])?65:97;
            if(message[i] != ' ') {
                message[i] = (char) ((message[i] - n - offset) % 26 + offset);
            }
        }

        System.out.println("Decrypted Text: " + new String(message));

    }
}
