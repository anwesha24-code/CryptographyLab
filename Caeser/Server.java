package Caeser;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key: ");
        int key = Integer.parseInt(br.readLine());

        String result = "";
        for (char c : msg.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result += (char) ((c - base + key) % 26 + base);
            } else result += c;
        }

        System.out.println("\nCipher Text: " + result);

        out.println(result + " " + key);

        s.close();
        ss.close();
    }
}