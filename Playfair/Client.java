package Playfair;

import java.io.*;
import java.net.*;

public class Client {

    static char[][] matrix = new char[5][5];
    static void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replace("J", "I");
        int k = 0;
        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                matrix[k / 5][k % 5] = c;
                used[c - 'A'] = true;
                k++;
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                matrix[k / 5][k % 5] = c;
                used[c - 'A'] = true;
                k++;
            }
        }
    }

    static String decrypt(String text) {
        String res = "";

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
            for (int r = 0; r < 5; r++)
                for (int c = 0; c < 5; c++) {
                    if (matrix[r][c] == a) { r1 = r; c1 = c; }
                    if (matrix[r][c] == b) { r2 = r; c2 = c; }
                }

            if (r1 == r2) {
                res += matrix[r1][(c1 + 4) % 5];
                res += matrix[r2][(c2 + 4) % 5];
            } else if (c1 == c2) {
                res += matrix[(r1 + 4) % 5][c1];
                res += matrix[(r2 + 4) % 5][c2];
            } else {
                res += matrix[r1][c2];
                res += matrix[r2][c1];
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5003);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ", 2);

        String cipher = data[0];
        String key = data[1];

        generateMatrix(key);
        String plain = decrypt(cipher);

        System.out.println("Decrypted Text: " + plain);

        s.close();
    }
}