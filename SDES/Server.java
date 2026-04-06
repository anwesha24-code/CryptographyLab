package SDES;
// Server.java
import java.io.*;
import java.net.*;

public class Server {

    static int[] P10 = {3,5,2,7,4,10,1,9,8,6};
    static int[] P8  = {6,3,7,4,8,5,10,9};
    static int[] IP  = {2,6,3,1,4,8,5,7};
    static int[] IP1 = {4,1,3,5,7,2,8,6};
    static int[] EP  = {4,1,2,3,2,3,4,1};
    static int[] P4  = {2,4,3,1};

    static int[][] S0 = {{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};
    static int[][] S1 = {{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};

    static String permute(String input, int[] seq){
        StringBuilder sb = new StringBuilder();
        for(int i : seq) sb.append(input.charAt(i-1));
        return sb.toString();
    }

    static String shift(String s, int n){
        return s.substring(n) + s.substring(0,n);
    }

    static String xor(String a, String b){
        String res="";
        for(int i=0;i<a.length();i++)
            res += (a.charAt(i)==b.charAt(i))?'0':'1';
        return res;
    }

    static String sbox(String input){
        String left = input.substring(0,4);
        String right = input.substring(4);

        int row = Integer.parseInt(""+left.charAt(0)+left.charAt(3),2);
        int col = Integer.parseInt(""+left.charAt(1)+left.charAt(2),2);
        String s0 = String.format("%2s", Integer.toBinaryString(S0[row][col])).replace(' ','0');

        row = Integer.parseInt(""+right.charAt(0)+right.charAt(3),2);
        col = Integer.parseInt(""+right.charAt(1)+right.charAt(2),2);
        String s1 = String.format("%2s", Integer.toBinaryString(S1[row][col])).replace(' ','0');

        return s0 + s1;
    }

    static String fk(String left, String right, String key){
        String temp = permute(right, EP);
        temp = xor(temp, key);
        temp = sbox(temp);
        temp = permute(temp, P4);
        return xor(left, temp);
    }

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.println("Sender (Encryption)");

        System.out.print("Message/Plaintext (8-bit binary): ");
        String msg = br.readLine();

        System.out.print("Key (10-bit binary): ");
        String key = br.readLine();

        // Key generation
        String p10 = permute(key, P10);
        String left = p10.substring(0,5);
        String right = p10.substring(5);

        left = shift(left,1);
        right = shift(right,1);
        String k1 = permute(left+right, P8);

        left = shift(left,2);
        right = shift(right,2);
        String k2 = permute(left+right, P8);

        System.out.println("Subkeys:");
        System.out.println("K1: " + k1);
        System.out.println("K2: " + k2);

        // Initial Permutation
        String ip = permute(msg, IP);
        System.out.println("IP: " + ip);

        String L = ip.substring(0,4);
        String R = ip.substring(4);

        // Round 1
        String left1 = fk(L,R,k1);
        String right1 = R;
        System.out.println("Round 1: " + left1 + right1);

        // Swap
        String temp = left1;
        left1 = right1;
        right1 = temp;

        // Round 2
        String left2 = fk(left1,right1,k2);
        String right2 = right1;
        System.out.println("Round 2: " + left2 + right2);

        // Final Permutation
        String cipher = permute(left2+right2, IP1);

        System.out.println("Cipher Text: " + cipher);

        out.println(cipher + " " + k1 + " " + k2);

        s.close();
        ss.close();
    }
}