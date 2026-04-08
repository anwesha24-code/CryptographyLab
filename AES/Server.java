package AES;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        System.out.print("Message: ");
        String msg = br.readLine();
        System.out.print("Key: ");
        String keyStr = br.readLine();

        // Make 16-byte key
        byte[] keyBytes = new byte[16];
        byte[] inputKey = keyStr.getBytes();
        for (int i = 0; i < 16; i++)
            keyBytes[i] = (i < inputKey.length) ? inputKey[i] : 0;

        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(msg.getBytes());
        String cipherText = bytesToHex(encrypted);

        System.out.println("Cipher Text: " + cipherText);
        out.println(cipherText + " " + new String(keyBytes));

        s.close();
        ss.close();
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02X", b));
        return sb.toString();
    }
}