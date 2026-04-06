package RSA;
// Client.java
import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String[] data = in.readLine().split(" ");

        BigInteger c = new BigInteger(data[0]);
        BigInteger d = new BigInteger(data[1]);
        BigInteger n = new BigInteger(data[2]);

        // Decryption
        BigInteger m = c.modPow(d, n);

        String decrypted = new String(m.toByteArray());

        System.out.println("Decrypted text: " + decrypted);

        s.close();
    }
}