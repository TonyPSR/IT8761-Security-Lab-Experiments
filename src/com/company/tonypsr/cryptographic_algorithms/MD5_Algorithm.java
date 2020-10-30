package com.company.tonypsr.cryptographic_algorithms;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5_Algorithm {
    public static String generateMD5HashCode(String input) {
        StringBuilder hashCode = new StringBuilder();;
        BigInteger temp;
        MessageDigest messageDigest;
        byte[] messageBytes;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageBytes = messageDigest.digest(input.getBytes());

            temp = new BigInteger(1, messageBytes);

            messageDigest.update(messageBytes);

            hashCode = new StringBuilder(temp.toString(16));

            while (hashCode.length() < 32) {
                hashCode.insert(0, "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashCode.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nMD5 Algorithm\n");

        System.out.println("Enter the message: ");
        String message = sc.nextLine();

        System.out.println("\n->MD5 HashCode: " + generateMD5HashCode(message));
    }
}