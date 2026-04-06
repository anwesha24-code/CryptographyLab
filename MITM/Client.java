package MITM;
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

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        // Receive p, g and fake key from attacker
        String[] data = in.readLine().split(" ");
        int p = Integer.parseInt(data[0]);
        int g = Integer.parseInt(data[1]);
        int fakeYa = Integer.parseInt(data[2]);

        System.out.println("Bob");

        System.out.print("Enter private key: ");
        int Xb = Integer.parseInt(br.readLine());

        int Yb = power(g, Xb, p);
        System.out.println("Bob Public Key: " + Yb);

        // Send to attacker
        out.println(Yb);

        int Kb = power(fakeYa, Xb, p);

        System.out.println("Public key from attacker: " + fakeYa);
        System.out.println("Common key (Bob-Attacker): " + Kb);

        s.close();
    }
}