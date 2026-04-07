package Vernam;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5002);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key (same length): ");
        String key = br.readLine();

        String result = "";
        for (int i = 0; i < msg.length(); i++) {
            result += (char) (msg.charAt(i) ^ key.charAt(i));
        }

        System.out.println("\nCipher Text: " + result);

        out.println(result + " " + key);

        s.close();
        ss.close();
    }
}