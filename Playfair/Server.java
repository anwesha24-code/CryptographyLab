package Playfair;

import java.io.*;
import java.net.*;

public class Server {

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

    static String encrypt(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll(" ", "");
        String res = "";

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';

            int r1 = 0, c1 = 0, r2 = 0, c2 = 0;

            for (int r = 0; r < 5; r++)
                for (int c = 0; c < 5; c++) {
                    if (matrix[r][c] == a) { r1 = r; c1 = c; }
                    if (matrix[r][c] == b) { r2 = r; c2 = c; }
                }

            if (r1 == r2) {
                res += matrix[r1][(c1 + 1) % 5];
                res += matrix[r2][(c2 + 1) % 5];
            } else if (c1 == c2) {
                res += matrix[(r1 + 1) % 5][c1];
                res += matrix[(r2 + 1) % 5][c2];
            } else {
                res += matrix[r1][c2];
                res += matrix[r2][c1];
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5003);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key: ");
        String key = br.readLine();

        generateMatrix(key);
        String cipher = encrypt(msg);

        System.out.println("\nCipher Text: " + cipher);

        out.println(cipher + " " + key);

        s.close();
        ss.close();
    }
}