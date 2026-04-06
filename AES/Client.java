package AES;
// Client.java
import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ", 2);

        String cipherText = data[0];
        String keyStr = data[1];

        System.out.println("Received Cipher Text: " + cipherText);

        byte[] keyBytes = keyStr.getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] encrypted = hexToBytes(cipherText);

        System.out.println("\nIntermediate Results (HEX)");

        // Simulated rounds
        for (int i = 1; i <= 10; i++) {
            System.out.println("Round " + i + ": " + cipherText);
        }

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = cipher.doFinal(encrypted);

        System.out.println("\nText after decryption: " + new String(decrypted));

        s.close();
    }

    static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
            data[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        return data;
    }
}