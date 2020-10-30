package com.company.tonypsr.cryptographic_algorithms;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class SHA_Algorithm {
    public static String generateSHA1HashCode(String input) {
        StringBuilder hashCode = new StringBuilder();;
        BigInteger temp;
        MessageDigest messageDigest;
        byte[] messageBytes;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageBytes = messageDigest.digest(input.getBytes());

            temp = new BigInteger(1, messageBytes);

            messageDigest.update(messageBytes);

            hashCode = new StringBuilder(temp.toString(16));

            while (hashCode.length() < 32) {
                hashCode.append("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashCode.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nSHA-1 Algorithm\n");

        System.out.println("Enter the message: ");
        String message = sc.nextLine();

        System.out.println("\n->SHA HashCode: " + generateSHA1HashCode(message));
    }
}
