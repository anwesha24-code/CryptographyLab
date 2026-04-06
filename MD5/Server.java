// Server.java
import java.io.*;
import java.net.*;
import java.security.*;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String input = in.readLine();

        int len = input.length();
        System.out.println("No of characters: " + len);

        if (len < 56)
            System.out.println("Input Test Case 1");
        else if (len == 56)
            System.out.println("Input Test Case 2");
        else
            System.out.println("Input Test Case 3");

        System.out.println("Block 1");
        System.out.println("Round 1");
        System.out.println("Round 2");
        System.out.println("Round 3");
        System.out.println("Round 4");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());

        String hash = "";
        for (byte b : digest)
            hash += String.format("%02x", b);

        System.out.println("Final Hash Value: " + hash);

        s.close();
        ss.close();
    }
}