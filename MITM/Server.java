package MITM;
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
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        // Alice Input
        System.out.println("Alice");
        System.out.print("Enter prime: ");
        int p = Integer.parseInt(br.readLine());
        System.out.print("Enter primitive root: ");
        int g = Integer.parseInt(br.readLine());
        System.out.print("Enter private key: ");
        int Xa = Integer.parseInt(br.readLine());


        int Ya = power(g, Xa, p);
        System.out.println("Alice Public Key: " + Ya);

        // Attacker Input
        System.out.println("\nAttacker");
        System.out.print("Enter Xd1: ");
        int Xd1 = Integer.parseInt(br.readLine());
        System.out.print("Enter Xd2: ");
        int Xd2 = Integer.parseInt(br.readLine());


        int Yd1 = power(g, Xd1, p);
        int Yd2 = power(g, Xd2, p);

        System.out.println("Attacker Public Keys: " + Yd1 + ", " + Yd2);

        // Send fake key to Bob (instead of Alice's Ya)
        out.println(p + " " + g + " " + Yd2);
        // Receive Bob key (intercepted)(instead of Bob's Yb)
        int Yb = Integer.parseInt(in.readLine());

        // Alice thinks this is Bob’s key but it's attacker’s public key
        int Ka = power(Yd1, Xa, p);

        // Attacker keys
        int Kd1 = power(Ya, Xd1, p);
        int Kd2 = power(Yb, Xd2, p);

        System.out.println("\nAlice");
        System.out.println("Public key from attacker: " + Yd1);
        System.out.println("Common key (Alice-Attacker): " + Ka);

        System.out.println("\nAttacker");
        System.out.println("Common key with Alice: " + Kd1);
        System.out.println("Common key with Bob: " + Kd2);

        s.close();
        ss.close();
    }
}