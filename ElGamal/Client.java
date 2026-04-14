package ElGamal;
// Client.java
import java.io.*;
import java.net.*;

public class Client {

    static int power(int a, int b, int mod) {
        int res = 1;
        for (int i = 0; i < b; i++)
            res = (res * a) % mod;
        return res;
    }

    static int modInverse(int a, int p) {
        for (int i = 1; i < p; i++) {
            if ((a * i) % p == 1)
                return i;
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String[] data = in.readLine().split(" ");

        int p = Integer.parseInt(data[0]);
        // int g = Integer.parseInt(data[1]);
        int x = Integer.parseInt(data[2]);
        int c1 = Integer.parseInt(data[3]);
        int c2 = Integer.parseInt(data[4]);

        System.out.println("Receiver (Client)");

        int sKey = power(c1, x, p);
        int inv = modInverse(sKey, p);
        int m = (c2 * inv) % p;

        System.out.println("Decrypted Message: " + m);

        s.close();
    }
}
