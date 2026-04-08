package AES;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String[] data = in.readLine().split(" ", 2);

        String cipherText = data[0];
        String keyStr = data[1];

        byte[] keyBytes = keyStr.getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] encrypted = hexToBytes(cipherText);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = cipher.doFinal(encrypted);

        System.out.println("Decrypted Text: " + new String(decrypted));

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