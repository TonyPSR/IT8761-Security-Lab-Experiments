package com.company.tonypsr.substitution_and_transposition_ciphers;

import java.util.ArrayList;
import java.util.Scanner;

public class HillCipher {

    public static ArrayList<String> splitMessage(String message, int splitBy) {
        ArrayList<String> splittedMessage = new ArrayList<>();

        for (int i = 0; i < message.length() - splitBy + 1; i += splitBy) {
            splittedMessage.add(message.substring(i, i + splitBy));
        }

        return splittedMessage;
    }


    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    c[i][j] += (a[i][k] * b[k][j]);
                }
            }
        }

        return c;
    }

    public static int[][] stringToColumnVector(String text) {
        int[][] colVector = new int[text.length()][1];

        for (int i = 0; i < text.length(); i++) {
            colVector[i][0] = text.charAt(i) - 'a';
        }

        return colVector;
    }

    public static String encrypt(String plainText, int[][] key) {
        StringBuilder cipherText = new StringBuilder();

        ArrayList<String> messageSplit = splitMessage(plainText, key.length);


        for (String textBlock : messageSplit) {
            int[][] c = multiply(key, stringToColumnVector(textBlock));

            for (int i = 0; i < c.length; i++) {
                cipherText.append((char) (c[i][0] % 26 + 'a'));
            }
        }

        return cipherText.toString();
    }

    public static boolean isValidKey(int[][] key){
        int determinant = determinant(key, key.length);

        if (determinant == 0) {
            System.out.println("Key can't be inverted, determinant 0. Try another key");
            return false;
        }

        //prelims check
        if (Math.abs(gcd(determinant, 26)) != 1) {
            System.out.println("Key doesn't have a modular inverse, try another key");
            return false;
        }

        return true;
    }

    public static String decrypt(String cipherText, int[][] key) {
        StringBuilder plainText = new StringBuilder();

        ArrayList<String> messageSplit = splitMessage(cipherText, key.length);

        for (String textBlock : messageSplit) {
            int[][] adjMatrix = adj(key);
            int determinant = determinant(key, key.length);

            if(!isValidKey(key)){
                System.exit(0);
            }


            int[][] c = multiply(adjMatrix, stringToColumnVector(textBlock));

            //finding suitable k such that
            // (det*k)mod26 === 1
            int k = 1;
            while ((determinant * k) % 26 != 1) {
                k++;
            }

            for (int i = 0; i < c.length; i++) {
                c[i][0] *= k;
                plainText.append((char) (Math.abs(c[i][0]) % 26 + 'a'));
            }

        }

        return plainText.toString();
    }


    static int gcd(int n1, int n2) {
        if (n2 != 0)
            return gcd(n2, n1 % n2);
        else
            return n1;
    }

    static void getCofactor(int[][] A, int[][] temp, int p, int q, int n) {

        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];

                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    static int determinant(int[][] A, int n) {
        int result = 0;

        if (n == 1)
            return A[0][0];

        int[][] temp = new int[A.length][A.length];

        int sign = 1;

        for (int f = 0; f < n; f++) {
            getCofactor(A, temp, 0, f, n);
            result += sign * A[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }

        return result;
    }


    static int[][] adj(int A[][]) {
        int[][] adj = new int[A.length][A.length];
        if (A.length == 1) {
            adj[0][0] = 1;
            return adj;
        }

        int sign = 1;
        int[][] temp = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                getCofactor(A, temp, i, j, A.length);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(temp, A.length - 1));
            }
        }

        return adj;
    }

    public static int[][] stringToIntArray(String message) {
        int n = (int) Math.sqrt(message.length());
        int[][] key = new int[n][n];

        int stringPtr = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                key[i][j] = message.charAt(stringPtr) - 'a';
                stringPtr += 1;
            }
        }

        return key;
    }


    public static void main(String[] args) {
        /* May fail for edge cases */
        /* Additional testing required */

        Scanner sc = new Scanner(System.in);

        System.out.println("\nHill Cipher\n");

        System.out.println("Enter the message: (lowercase without spaces)");
        String message = sc.next().toLowerCase();

        int[][] key = null;

        System.out.println("1. Enter key as String\n2. Enter key as Matrix (2D Array)");
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Enter the key: (length: 4, 9, etc...) (a-z)");
            String keyString = sc.next().toLowerCase();

            // if length of the key is not a square of an integer.
            if (Math.sqrt(keyString.length()) != Math.round(Math.sqrt(keyString.length()))) {
                System.out.println("Invalid key length");
                System.exit(0);
            }

            key = stringToIntArray(keyString);

        } else if (choice == 2) {
            System.out.println("Enter Matrix Order: ");
            int order = sc.nextInt();
            key = new int[order][order];

            System.out.println("\nEnter numbers:");

            for (int i = 0; i < order; i++) {
                for (int j = 0; j < order; j++) {
                    System.out.println("Enter Matrix[" + (i+1) + "][" + (j+1) + "]: ");
                    key[i][j] = sc.nextInt();
                }
            }
        } else {
            System.out.println("Invalid choice");
        }

        String cipherText = encrypt(message, key);
        System.out.println("Cipher Text: " + cipherText);

        String plainText = decrypt(cipherText, key);
        System.out.println("Plain Text: " + plainText);

    }
}
