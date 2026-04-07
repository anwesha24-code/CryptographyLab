package Vernam;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5002);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ", 2);

        String cipher = data[0];
        String key = data[1];

        String result = "";
        for (int i = 0; i < cipher.length(); i++) {
            result += (char) (cipher.charAt(i) ^ key.charAt(i));
        }

        System.out.println("Decrypted Text: " + result);

        s.close();
    }
}