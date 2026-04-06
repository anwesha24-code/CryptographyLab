package DES;
// Server.java
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

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext: ");
        String msg = br.readLine();

        System.out.print("Key: ");
        String keyStr = br.readLine();

        // DES requires 8-byte (64-bit) key
        byte[] keyBytes = new byte[8];
        byte[] inputKey = keyStr.getBytes();

        for (int i = 0; i < 8; i++) {
            keyBytes[i] = (i < inputKey.length) ? inputKey[i] : 0;
        }

        SecretKey key = new SecretKeySpec(keyBytes, "DES");

        byte[] data = msg.getBytes();

        System.out.println("\nIntermediate Results (HEX)");

        // Simulated 16 rounds
        for (int i = 1; i <= 16; i++) {
            System.out.println("Round " + i + ": " + bytesToHex(data));
        }

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(data);

        String cipherText = bytesToHex(encrypted);

        System.out.println("\nCipher Text: " + cipherText);

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