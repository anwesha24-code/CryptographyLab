package SDES;

import java.io.*;
import java.net.*;

public class Client {

    static int[] IP  = {2,6,3,1,4,8,5,7};
    static int[] IP1 = {4,1,3,5,7,2,8,6};
    static int[] EP  = {4,1,2,3,2,3,4,1};
    static int[] P4  = {2,4,3,1};

    static int[][] S0 = {{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};
    static int[][] S1 = {{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};

    static String permute(String in, int[] seq){
        StringBuilder sb = new StringBuilder();
        for(int i : seq) sb.append(in.charAt(i-1));
        return sb.toString();
    }

    static String xor(String a, String b){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<a.length();i++)
            sb.append(a.charAt(i)==b.charAt(i)?'0':'1');
        return sb.toString();
    }

    static String sbox(String in){
        String l = in.substring(0,4), r = in.substring(4);

        int row = Integer.parseInt(""+l.charAt(0)+l.charAt(3),2);
        int col = Integer.parseInt(""+l.charAt(1)+l.charAt(2),2);
        String s0 = String.format("%2s", Integer.toBinaryString(S0[row][col])).replace(' ','0');

        row = Integer.parseInt(""+r.charAt(0)+r.charAt(3),2);
        col = Integer.parseInt(""+r.charAt(1)+r.charAt(2),2);
        String s1 = String.format("%2s", Integer.toBinaryString(S1[row][col])).replace(' ','0');

        return s0 + s1;
    }

    static String fk(String L, String R, String k){
        String t = permute(R, EP);
        t = xor(t, k);
        t = permute(sbox(t), P4);
        return xor(L, t);
    }

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String[] d = in.readLine().split(" ");

        String ip = permute(d[0], IP);
        String L = ip.substring(0,4), R = ip.substring(4);

        String t = fk(L,R,d[2]);
        L = R;
        R = t;

        String plain = permute(fk(L,R,d[1]) + R, IP1);

        System.out.println("Decrypted Text: " + plain);

        s.close();
    }
}