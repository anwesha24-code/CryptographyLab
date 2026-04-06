package SDES;
// Client.java
import java.io.*;
import java.net.*;

public class Client {

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

        Socket s = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Receiver (Decryption)");

        String[] data = in.readLine().split(" ");

        String cipher = data[0];
        String k1 = data[1];
        String k2 = data[2];

        System.out.println("Received Cipher Text: " + cipher);
        System.out.println("Subkeys:");
        System.out.println("K1: " + k1);
        System.out.println("K2: " + k2);

        String ip = permute(cipher, IP);
        System.out.println("IP: " + ip);

        String L = ip.substring(0,4);
        String R = ip.substring(4);

        // Round 1 (reverse keys)
        String left1 = fk(L,R,k2);
        String right1 = R;
        System.out.println("Round 1: " + left1 + right1);

        // Swap
        String temp = left1;
        left1 = right1;
        right1 = temp;

        // Round 2
        String left2 = fk(left1,right1,k1);
        String right2 = right1;
        System.out.println("Round 2: " + left2 + right2);

        String plain = permute(left2+right2, IP1);

        System.out.println("Text after decryption: " + plain);

        s.close();
    }
}