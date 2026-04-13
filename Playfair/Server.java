package Playfair;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Enter word:");
        String word = br.readLine();

        System.out.println("Enter key:");
        String key = br.readLine();

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

        // Preprocess message
        word = word.toUpperCase().replaceAll("J", "I").replaceAll(" ", "");

        String processed = "";
        for (int i = 0; i < word.length(); i++) {
            char a = word.charAt(i);
            char b;

            if (i + 1 < word.length() && word.charAt(i) != word.charAt(i + 1)) {
                b = word.charAt(i + 1);
                i++;
            } else {
                b = 'X';
            }

            processed += "" + a + b;
        }

        // Encryption
        String res = "";

        for (int i = 0; i < processed.length(); i += 2) {
            char a = processed.charAt(i);
            char b = processed.charAt(i + 1);

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
                res += mat[r1][(c1 + 1) % 5];
                res += mat[r2][(c2 + 1) % 5];
            } else if (c1 == c2) {
                res += mat[(r1 + 1) % 5][c1];
                res += mat[(r2 + 1) % 5][c2];
            } else {
                res += mat[r1][c2];
                res += mat[r2][c1];
            }
        }

        System.out.println("Encrypted: " + res);

        // Send encrypted + key
        out.println(res + " " + key);

        s.close();
        ss.close();
    }
}