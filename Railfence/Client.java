package Railfence;


import java.io.*;
import java.net.*;

public class Client {

    static String decrypt(String cipher, int key) {
        char[][] rail = new char[key][cipher.length()];

        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                rail[i][j] = '\n';

        int dir = 1, row = 0;

        for (int i = 0; i < cipher.length(); i++) {
            rail[row][i] = '*';
            if (row == 0) dir = 1;
            else if (row == key - 1) dir = -1;
            row += dir;
        }

        int index = 0;
        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*' && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);

        String res = "";
        dir = 1; row = 0;

        for (int i = 0; i < cipher.length(); i++) {
            res += rail[row][i];
            if (row == 0) dir = 1;
            else if (row == key - 1) dir = -1;
            row += dir;
        }

        return res;
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5005);

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