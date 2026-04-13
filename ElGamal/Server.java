package ElGamal;

// Server.java
import java.io.*;
import java.net.*;

public class Server {

    static int power(int a, int b, int mod) {
        int res = 1;
        for (int i = 0; i < b; i++)
            res = (res * a) % mod;
        return res;
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        System.out.println("Sender (Server)");

        System.out.print("Enter prime: ");
        int p = Integer.parseInt(br.readLine());
        System.out.print("Enter primitive root: ");
        int g = Integer.parseInt(br.readLine());
        System.out.print("Enter private key (x): ");
        int x = Integer.parseInt(br.readLine());

        int y = power(g, x, p); // public key

        System.out.println("Public key (y): " + y);

        System.out.print("Enter message: ");
        int m = Integer.parseInt(br.readLine());
        System.out.print("Enter random k: ");
        int k = Integer.parseInt(br.readLine());

        int c1 = power(g, k, p);
        int c2 = (m * power(y, k, p)) % p;

        System.out.println("Cipher Text: (" + c1 + ", " + c2 + ")");

        out.println(p + " " + g + " " + x + " " + c1 + " " + c2);

        s.close();
        ss.close();
    }
}