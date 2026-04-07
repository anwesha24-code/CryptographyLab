package Railfence;

import java.io.*;
import java.net.*;

public class Server {

    static String encrypt(String text, int key) {
        StringBuilder[] rail = new StringBuilder[key];
        for (int i = 0; i < key; i++) rail[i] = new StringBuilder();

        int dir = 1, row = 0;

        for (char c : text.toCharArray()) {
            rail[row].append(c);
            if (row == 0) dir = 1;
            else if (row == key - 1) dir = -1;
            row += dir;
        }

        String res = "";
        for (int i = 0; i < key; i++) res += rail[i].toString();

        return res;
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5005);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key (rails): ");
        int key = Integer.parseInt(br.readLine());

        String cipher = encrypt(msg, key);

        System.out.println("\nCipher Text: " + cipher);

        out.println(cipher + " " + key);

        s.close();
        ss.close();
    }
}