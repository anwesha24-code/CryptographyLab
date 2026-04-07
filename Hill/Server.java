package Hill;

import java.io.*;
import java.net.*;

public class Server {

    static int[][] key = new int[2][2];

    static String encrypt(String text) {
        text = text.toUpperCase().replaceAll(" ", "");
        if (text.length() % 2 != 0) text += "X";

        String res = "";

        for (int i = 0; i < text.length(); i += 2) {
            int a = text.charAt(i) - 'A';
            int b = text.charAt(i + 1) - 'A';

            int c1 = (key[0][0]*a + key[0][1]*b) % 26;
            int c2 = (key[1][0]*a + key[1][1]*b) % 26;

            res += (char)(c1 + 'A');
            res += (char)(c2 + 'A');
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5004);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.println("Enter 2x2 key matrix:");
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                key[i][j] = Integer.parseInt(br.readLine());

        String cipher = encrypt(msg);

        System.out.println("\nCipher Text: " + cipher);

        out.println(cipher + " " +
                key[0][0]+" "+key[0][1]+" "+key[1][0]+" "+key[1][1]);

        s.close();
        ss.close();
    }
}