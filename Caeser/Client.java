package Caeser;
package Caesar;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ");

        String cipher = data[0];
        int key = Integer.parseInt(data[1]);

        String result = "";
        for (char c : cipher.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result += (char) ((c - base - key + 26) % 26 + base);
            } else result += c;
        }

        System.out.println("Decrypted Text: " + result);

        s.close();
    }
}