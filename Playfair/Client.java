package Playfair;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String[] data = in.readLine().split(" ");
        String word = data[0];
        String key = data[1];

        key = key.toUpperCase().replaceAll("J", "I");

        // Create matrix
        char[][] mat = new char[5][5];
        boolean[] used = new boolean[26];
        int j = 0;

        for (char k : key.toCharArray()) {
            if (k == 'J') k = 'I';
            if (!used[k - 'A']) {
                mat[j / 5][j % 5] = k;
                used[k - 'A'] = true;
                j++;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                mat[j / 5][j % 5] = c;
                used[c - 'A'] = true;
                j++;
            }
        }

        word = word.toUpperCase().replaceAll("J", "I").replaceAll(" ", "");

        // Decryption
        String res = "";

        for (int i = 0; i < word.length(); i += 2) {
            char a = word.charAt(i);
            char b = word.charAt(i + 1);

            int r1 = 0, c1 = 0, r2 = 0, c2 = 0;

            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++) {
                    if (mat[r][c] == a) {
                        r1 = r; c1 = c;
                    }
                    if (mat[r][c] == b) {
                        r2 = r; c2 = c;
                    }
                }
            }

            if (r1 == r2) {
                res += mat[r1][(c1 + 4) % 5];
                res += mat[r2][(c2 + 4) % 5];
            } else if (c1 == c2) {
                res += mat[(r1 + 4) % 5][c1];
                res += mat[(r2 + 4) % 5][c2];
            } else {
                res += mat[r1][c2];
                res += mat[r2][c1];
            }
        }

        System.out.println("Decrypted: " + res);

        s.close();
    }
}