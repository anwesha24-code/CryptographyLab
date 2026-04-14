package DSS;
import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

public class Client {
    static BigInteger p, q, g, x, y;
    static Random rand = new Random();

    static BigInteger getPrime(int bits){
        return BigInteger.probablePrime(bits, rand);
    }

    static void generateKeys(){
        q = getPrime(5);
        p = q.multiply(getPrime(5)).add(BigInteger.ONE);
        g = new BigInteger("2");

        x = new BigInteger(q.bitLength()-1, rand); 
        y = g.modPow(x, p); 
    }

    static BigInteger[] sign(String msg){
        BigInteger hash = new BigInteger(msg.getBytes()).mod(q);

        BigInteger k = new BigInteger(q.bitLength()-1, rand);
        BigInteger r = g.modPow(k, p).mod(q);

        BigInteger kinv = k.modInverse(q);
        BigInteger s = kinv.multiply(hash.add(x.multiply(r))).mod(q);

        return new BigInteger[]{r, s};
    }

    public static void main(String[] args) throws Exception {

        Socket sock = new Socket("localhost", 5000);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

        generateKeys();

        System.out.print("Enter message: ");
        String msg = br.readLine();

        BigInteger[] sig = sign(msg);
        BigInteger r = sig[0];
        BigInteger s = sig[1];

        System.out.println("\nGenerated Signature:");
        System.out.println("r = " + r);
        System.out.println("s = " + s);

        out.println(msg);
        out.println(r + " " + s);
        out.println(p + " " + q + " " + g + " " + y);

        sock.close();
    }
}