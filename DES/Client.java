package DES;

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

        SecretKey key = new SecretKeySpec(data[1].getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = cipher.doFinal(hexToBytes(cipherText));

        System.out.println("Decrypted Text: " + new String(decrypted));

        s.close();
    }

    static byte[] hexToBytes(String s) {
        byte[] data = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2)
            data[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        return data;
    }
}