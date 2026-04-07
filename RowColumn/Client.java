package RowColumn;

import java.io.*;
import java.net.*;

public class Client {

    static String decrypt(String cipher, int cols) {
        int rows = (int) Math.ceil((double) cipher.length() / cols);

        char[][] mat = new char[rows][cols];

        int k = 0;
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                mat[i][j] = cipher.charAt(k++);

        String res = "";
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                res += mat[i][j];

        return res;
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5006);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ");

        String cipher = data[0];
        int key = Integer.parseInt(data[1]);

        String plain = decrypt(cipher, key);

        System.out.println("Decrypted Text: " + plain);

        s.close();
    }
}