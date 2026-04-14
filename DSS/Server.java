package DSS;
import java.io.*;
import java.net.*;
import java.math.*;

public class Server {

    static BigInteger p, q, g, y;

    static boolean verify(String msg, BigInteger r, BigInteger s){
        BigInteger hash = new BigInteger(msg.getBytes()).mod(q);

        BigInteger w = s.modInverse(q);
        BigInteger u1 = hash.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

        return v.equals(r);
    }

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        Socket socket = ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String msg = in.readLine();

        String[] sig = in.readLine().split("\\s+");
        BigInteger r = new BigInteger(sig[0]);
        BigInteger s = new BigInteger(sig[1]);

        // p q g y in one line
        String[] params = in.readLine().split("\\s+");
        p = new BigInteger(params[0]);
        q = new BigInteger(params[1]);
        g = new BigInteger(params[2]);
        y = new BigInteger(params[3]);

        System.out.println("\nReceived Message: " + msg);
        System.out.println("Signature: " + r + ", " + s);

        boolean valid = verify(msg, r, s);

        if(valid)
            System.out.println("Signature Valid");
        else
            System.out.println("Signature Invalid");

        socket.close();
        ss.close();
    }
}