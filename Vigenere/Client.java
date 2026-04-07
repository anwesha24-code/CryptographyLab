package Vigenere;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5001);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ", 2);

        String cipher = data[0];
        String key = data[1];

        String result = "";
        for (int i = 0, j = 0; i < cipher.length(); i++) {
            char c = cipher.charAt(i);

            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key.charAt(j % key.length()) - 'a';
                result += (char) ((c - base - shift + 26) % 26 + base);
                j++;
            } else result += c;
        }

        System.out.println("Decrypted Text: " + result);

        s.close();
    }
}