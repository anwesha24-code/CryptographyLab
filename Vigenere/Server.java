package Vigenere;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5001);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key: ");
        String key = br.readLine().toLowerCase();

        String result = "";
        for (int i = 0, j = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);

            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key.charAt(j % key.length()) - 'a';
                result += (char) ((c - base + shift) % 26 + base);
                j++;
            } else result += c;
        }

        System.out.println("\nCipher Text: " + result);

        out.println(result + " " + key);

        s.close();
        ss.close();
    }
}