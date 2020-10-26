package com.company.tonypsr.cryptographic_algorithms;

import java.util.HashSet;
import java.util.Scanner;

public class DiffieHellmanAlgorithm {

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

    // custom solution
    public static int findPrimitiveRoot(long num){
        HashSet<Long> set = new HashSet<>();

        int primitiveRoot = -1;

        for(int i=1; i<num; i++){
            for(int j=1; j<num; j++){
                long val = findExponentialModulo(i, j, num);

                if(set.contains(val)){
                    break;
                } else {
                    set.add(val);
                }

                if(set.size() == num-1){
                    primitiveRoot = i;
                    break;
                }
            }
            set = new HashSet<>();
            if(primitiveRoot != -1){
                break;
            }
        }

        return primitiveRoot;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long q, alpha, Ya, Xa, Yb, Xb, Ka, Kb;

        /*
            q = prime
            alpha = primitive of prime q
            Ya = public key of A
            Xa = private key of A
            Yb = public key of B
            Xb = private key of B
            Ka = secret key of A
            kb = secret key of B
         */

        System.out.println("Enter value of q (PRIME): ");
        q = sc.nextInt();

        alpha = findPrimitiveRoot(q);
        System.out.println("Computed value of alpha : " + alpha);

        System.out.println("Enter private key a for A: ");
        Xa = sc.nextInt();
        Ya = findExponentialModulo(alpha, Xa, q);

        System.out.println("Enter private key a for B: ");
        Xb = sc.nextInt();
        Yb = findExponentialModulo(alpha, Xb, q);

        System.out.println();
        System.out.println("Public key for A is: " + Ya);
        System.out.println("Public Key for B is: " + Yb);

        Ka = findExponentialModulo(Yb, Xa, q);
        Kb = findExponentialModulo(Ya, Xb, q);

        System.out.println();
        System.out.println("->Secret key for A is: " + Ka);
        System.out.println("->Secret Key for B is: " + Kb);
    }
}
