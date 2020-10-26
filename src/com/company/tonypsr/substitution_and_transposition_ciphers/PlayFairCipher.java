package com.company.tonypsr.substitution_and_transposition_ciphers;

import java.util.*;

public class PlayFairCipher {

    public static char[][] generateAndGetKeySquare(String key){
        char[][] keyTable = new char[5][5];

        // for quicker access
        HashSet<Character> keySet = new HashSet<>();
        for(char letter: key.toCharArray()) {
            keySet.add(letter);
        }


        // for avoiding repetition
        HashSet<Character> letterSet = new HashSet<>();

        int row = 0;
        int col = 0;

        for(int i=0; i<key.length(); i++){
            // to avoid duplication
            if(letterSet.contains(key.charAt(i))) continue;

            if(col == 5){
                row++;
                col = 0;
            }

            if(key.charAt(i) == 'j'){
                continue;
            }

            keyTable[row][col] = key.charAt(i);
            letterSet.add(key.charAt(i));
            col++;
        }

        for(int i=0; i<26; i++) {
            // to avoid duplication
            if(letterSet.contains('a'+i)) continue;

            if(col == 5){
                row++;
                col = 0;
            }

            if('a'+i == 'j'){
                continue;
            }

            if(!keySet.contains(((char)('a'+i)))){
                keyTable[row][col] = (char) ('a'+i);
                letterSet.add((char) ('a'+i));
                col++;
            }
        }

        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                System.out.print(keyTable[i][j] + " ");
            }
            System.out.println();
        }

        return keyTable;
    }

    public static ArrayList<String> splitMessage(String message){
        ArrayList<String> splittedMessage = new ArrayList<>();

        int duplicates=0;
        for(int i=0; i<message.length()-1; i+=2){
            if((message.charAt(i) != message.charAt(i+1))){
                splittedMessage.add(message.charAt(i) + "" + message.charAt(i+1));
            } else if(!(message.charAt(i) == 'x' && message.charAt(i) == message.charAt(i))) {
                splittedMessage.add(message.charAt(i) + "" + 'x');
                i-=1;
                duplicates+=1;
            }
        }
        // if length of key is odd
        if((message.length() + duplicates )%2 != 0) {
            splittedMessage.add("" + message.charAt(message.length() - 1) + "x");
        }

        return splittedMessage;
    }

    public static String encrypt(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        char[][] keyTable = generateAndGetKeySquare(key);

        ArrayList<String> splittedPlainText = splitMessage(plainText);

        System.out.println(splittedPlainText);

        for(String letterPair: splittedPlainText){
            char firstLetter = letterPair.charAt(0)=='j'?'i':letterPair.charAt(0);
            char secondLetter = letterPair.charAt(1)=='j'?'i':letterPair.charAt(1);
            int[] firstLetterIndex = new int [2];
            int[] secondLetterIndex = new int[2];


            for(int i=0; i<5; i++){
                for(int j=0; j<5; j++){
                    if(keyTable[i][j] == firstLetter){
                        firstLetterIndex[0] = i;
                        firstLetterIndex[1] = j;
                    } else if(keyTable[i][j] == secondLetter){
                        secondLetterIndex[0] = i;
                        secondLetterIndex[1] = j;
                    }
                }
            }

            //Cases
            // same row, same column, diff row and column
            if(firstLetter == secondLetter){
                cipherText.append(keyTable[firstLetterIndex[0]][(firstLetterIndex[1] + 1) % 5])
                        .append(keyTable[firstLetterIndex[0]][(firstLetterIndex[1] + 1) % 5]);
            }else if(firstLetterIndex[0] == secondLetterIndex[0]){
                cipherText.append(keyTable[firstLetterIndex[0]][(firstLetterIndex[1] + 1) % 5])
                        .append(keyTable[secondLetterIndex[0]][(secondLetterIndex[1] + 1) % 5]);
            } else if (firstLetterIndex[1] == secondLetterIndex[1]){
                cipherText.append(keyTable[(firstLetterIndex[0]+1)%5][(firstLetterIndex[1])])
                        .append(keyTable[(secondLetterIndex[0]+1)%5][(secondLetterIndex[1])]);
            } else {
                cipherText.append(keyTable[firstLetterIndex[0]][(secondLetterIndex[1])])
                        .append(keyTable[secondLetterIndex[0]][(firstLetterIndex[1])]);
            }

        }

        return cipherText.toString();
    }

    public static String decrypt(String cipherText, String key){
        StringBuilder plainText = new StringBuilder();
        char[][] keyTable = generateAndGetKeySquare(key);

        ArrayList<String> splittedPlainText = splitMessage(cipherText);

        System.out.println(splittedPlainText);

        for(String letterPair: splittedPlainText){
            char firstLetter = letterPair.charAt(0)=='j'?'i':letterPair.charAt(0);
            char secondLetter = letterPair.charAt(1)=='j'?'i':letterPair.charAt(1);
            int[] firstLetterIndex = new int [2];
            int[] secondLetterIndex = new int[2];

            for(int i=0; i<5; i++){
                for(int j=0; j<5; j++){
                    if(keyTable[i][j] == firstLetter){
                        firstLetterIndex[0] = i;
                        firstLetterIndex[1] = j;
                    } else if(keyTable[i][j] == secondLetter){
                        secondLetterIndex[0] = i;
                        secondLetterIndex[1] = j;
                    }
                }
            }

            //Cases
            // same row, same column, diff row and column
            if(firstLetterIndex[0] == secondLetterIndex[0]){
                plainText.append(keyTable[firstLetterIndex[0]][(5+firstLetterIndex[1] - 1) % 5])
                        .append(keyTable[secondLetterIndex[0]][(5+secondLetterIndex[1] - 1) % 5]);
            } else if (firstLetterIndex[1] == secondLetterIndex[1]){
                plainText.append(keyTable[(5+firstLetterIndex[0]-1)%5][(firstLetterIndex[1])])
                        .append(keyTable[(5+secondLetterIndex[0]-1)%5][(secondLetterIndex[1])]);
            } else {
                plainText.append(keyTable[firstLetterIndex[0]][(secondLetterIndex[1])])
                        .append(keyTable[secondLetterIndex[0]][(firstLetterIndex[1])]);
            }

        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the message (lowercase only):");
        String message = sc.next().toLowerCase().replaceAll("j","i");
        System.out.println("Enter the key:");
        // replace all j with i
        String key = sc.next().toLowerCase().replaceAll("j","i");

        String cipherText = encrypt(message, key);
        System.out.println("Cipher Text: " + cipherText);
        String plainText = decrypt(cipherText, key);
        System.out.println("Plain Text: " + plainText);
    }
}
