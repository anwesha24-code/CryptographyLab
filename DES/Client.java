package DES;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String[] d = in.readLine().split(" ", 2);

        byte[] keyBytes = d[1].getBytes();   

        Cipher cipher = Cipher.getInstance("DES");
        SecretKey key = new SecretKeySpec(keyBytes, "DES");  
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        byte[] decrypted = cipher.doFinal(hexToBytes(d[0]));

        System.out.println("Decrypted Text: " +
                new String(decrypted));

        s.close();
    }

    static byte[] hexToBytes(String s) {
        byte[] d = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2)
            d[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        return d;
    }
}