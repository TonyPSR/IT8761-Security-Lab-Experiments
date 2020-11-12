package com.company.tonypsr.cryptographic_algorithms;

import java.util.Random;
import java.util.Scanner;

public class RSA_Algorithm {

    public static boolean isPrime(int num) {
        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int generateNthPrime(int n) {
        int count = 0;

        int result = -1;
        for (int i = 2; count <= n; i++) {
            if (isPrime(i)) {
                result = i;
                count++;
            }
        }

        return result;
    }

    //findExponentialModulo() - fast exponentiation recursive algorithm
    public static long findExponentialModulo(long a, long N, long M) {
        if (N == 0) {
            return 1;
        } else {
            final long R = findExponentialModulo(a, N / 2, M);
            if (N % 2 == 0) {
                return (R * R) % M;
            } else {
                return (R * R * a) % M;
            }
        }
    }


    public static int gcd(int num1, int num2) {
        int temp;
        while (true) {
            temp = num1 % num2;
            if (temp == 0)
                return num2;
            num1 = num2;
            num2 = temp;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);

        System.out.println("\nRSA Algorithm\n");

        //STEP 1
        // Two unique random prime numbers
        // lower bound 98
        int p = generateNthPrime(rand.nextInt(100) + 99);
        int q = generateNthPrime(rand.nextInt(100) + 98);
        // make sure p and q are not the same
        while(p == q){
            q = generateNthPrime(rand.nextInt(100) + 98);
        }

        //STEP 2
        long n = p * q;

        //STEP 3
        long phi = (p - 1) * (q - 1);

        //STEP 4
        long e = 2;
        while (e < phi) {
            if (gcd((int) e, (int) phi) == 1)
                break;
            else
                e++;
        }

        //STEP 5
        long d = 0;
        while ((d * e) % phi != 1) {
            d++;
        }

        // get the message from the user
        System.out.println("Enter the message to send: (0-99999)");
        long plainText = sc.nextLong();

        System.out.println();

        System.out.println("Computed Values:");
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = p*q = " + n);
        System.out.println("phi = (p-1)*(q-1) = " + phi);
        System.out.println();
        System.out.println("e = " + e);
        System.out.println("d = " + d);

        System.out.println();

        // encryption {e,n}
        long cipherText = findExponentialModulo(plainText, e, n);
        System.out.println("->Cipher Text {e,n}: " + cipherText);

        // decryption {d,n}
        long m = findExponentialModulo(cipherText, d, n);
        System.out.println("->Plain Text {d,n}: " + m);
    }
}