package RSA;
// Server.java
import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.print("Message: ");
        String msg = br.readLine();

        BigInteger m = new BigInteger(msg.getBytes());

        SecureRandom rand = new SecureRandom();

        // Generate 1024-bit primes
        BigInteger p = BigInteger.probablePrime(1024, rand);
        BigInteger q = BigInteger.probablePrime(1024, rand);

        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(65537);

        BigInteger d = e.modInverse(phi);

        // Encryption
        BigInteger c = m.modPow(e, n);

        // Output
        System.out.println("p value: " + p);
        System.out.println("q value: " + q);
        System.out.println("n = p*q: " + n);
        System.out.println("phi(n): " + phi);
        System.out.println("e: " + e);
        System.out.println("d: " + d);
        System.out.println("Encrypted text: " + c);

        // Send to client
        out.println(c + " " + d + " " + n);

        s.close();
        ss.close();
    }
}