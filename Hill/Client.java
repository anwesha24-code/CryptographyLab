package Hill;

import java.io.*;
import java.net.*;

public class Client {

    static int[][] key = new int[2][2];

    static int modInverse(int det) {
        det = (det % 26 + 26) % 26;
        for (int i = 1; i < 26; i++)
            if ((det * i) % 26 == 1) return i;
        return 1;
    }

    static String decrypt(String text) {
        int det = key[0][0]*key[1][1] - key[0][1]*key[1][0];
        int invDet = modInverse(det);

        int[][] inv = new int[2][2];
        inv[0][0] = key[1][1];
        inv[1][1] = key[0][0];
        inv[0][1] = -key[0][1];
        inv[1][0] = -key[1][0];

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                inv[i][j] = (inv[i][j] * invDet) % 26;
                if (inv[i][j] < 0) inv[i][j] += 26;
            }

        String res = "";

        for (int i = 0; i < text.length(); i += 2) {
            int a = text.charAt(i) - 'A';
            int b = text.charAt(i + 1) - 'A';

            int p1 = (inv[0][0]*a + inv[0][1]*b) % 26;
            int p2 = (inv[1][0]*a + inv[1][1]*b) % 26;

            res += (char)(p1 + 'A');
            res += (char)(p2 + 'A');
        }
        return res;
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5004);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ");

        String cipher = data[0];

        key[0][0] = Integer.parseInt(data[1]);
        key[0][1] = Integer.parseInt(data[2]);
        key[1][0] = Integer.parseInt(data[3]);
        key[1][1] = Integer.parseInt(data[4]);

        String plain = decrypt(cipher);

        System.out.println("Decrypted Text: " + plain);

        s.close();
    }
}