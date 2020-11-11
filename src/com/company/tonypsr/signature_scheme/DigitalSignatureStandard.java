package com.company.tonypsr.signature_scheme;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Scanner;

public class DigitalSignatureStandard {
    public static byte[] generateSignature(byte[] messageBytes, PrivateKey Key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(Key);
        signature.update(messageBytes);
        return signature.sign();
    }

    public static KeyPair generateRSAKeyPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }


    public static boolean isVerifiedSignature(byte[] messageBytes, byte[] signatureGenerated, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(messageBytes);
            return signature.verify(signatureGenerated);
        }catch (Exception e){
            e.printStackTrace();
        }
         return false;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the message");
        String message = sc.next();

        try {
            KeyPair keyPair = generateRSAKeyPair();
            byte[] signature = generateSignature(message.getBytes(), keyPair.getPrivate());

            // bytes to hex
            System.out.println("Signature");
            for (byte b : signature) {
                String hex = String.format("%02x", b);
                System.out.print(hex);
            }

            System.out.println("\n");

            if (isVerifiedSignature(message.getBytes(), signature, keyPair.getPublic())) {
                System.out.println("Signature is verified");
            } else {
                System.out.println("Signature is not verified");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
