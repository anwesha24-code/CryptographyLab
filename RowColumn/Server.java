package RowColumn;

import java.io.*;
import java.net.*;

public class Server {

    static String encrypt(String text, int cols) {
        text = text.replaceAll(" ", "");
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] mat = new char[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                mat[i][j] = (k < text.length()) ? text.charAt(k++) : 'X';

        String res = "";
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                res += mat[i][j];

        return res;
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5006);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Number of columns (key): ");
        int key = Integer.parseInt(br.readLine());

        String cipher = encrypt(msg, key);

        System.out.println("\nCipher Text: " + cipher);

        out.println(cipher + " " + key);

        s.close();
        ss.close();
    }
}