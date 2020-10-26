package com.company.tonypsr.cryptographic_algorithms;

public class RSAAlgorithm {

    // Returns gcd of a and b
    public static int gcd(int a, int h)
    {
        int temp;
        while (true)
        {
            temp = a%h;
            if (temp == 0)
                return h;
            a = h;
            h = temp;
        }
    }

    public static void main(String[] args) {
        // Two random prime numbers
        double p = 13;
        double q = 11;

        // First part of public key:
        double n = p*q;

        // Finding other part of public key.
        // e stands for encrypt
        double e = 2;
        double phi = (p-1)*(q-1);
        while (e < phi)
        {
            // e must be co-prime to phi and
            // smaller than phi.
            if (gcd((int)e, (int)phi)==1)
                break;
            else
                e++;
        }

        // Private key (d stands for decrypt)
        // choosing d such that it satisfies
        // d*e = 1 + k * totient
        int k = 1;  // A constant value
        double d = (1 + (k*phi))/e;
        System.out.println("D: " + d);

        // Message to be encrypted
        double msg = 9;

        System.out.println("Message data = " + msg);

        // Encryption c = (msg ^ e) % n
        double c = Math.pow(msg, e) % n;


        System.out.println("Encrypted data = " + c);

        // Decryption m = (c ^ d) % n
        double m = Math.pow(c, d) %n;
        System.out.println("Original Message Sent = " + m);

    }
}
