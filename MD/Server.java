package MD;

import java.util.*;
import java.security.*;

class Server{
    public static void main(String [] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the message:");
        String word=sc.nextLine();

        int len=word.length();
        if(len<56)System.out.println("Test Case 1");
        else if(len==56)System.out.println("Test Case 2");
        else System.out.println("Test Case 3");

        int blocks=((len+8)/64)+1;
        System.out.println("Number of blocks: "+blocks);

        for(int i=0;i<blocks;i++){
            System.out.println("Block " + (i + 1));
            System.out.println("Round 1");
            System.out.println("Round 2");
            System.out.println("Round 3");
            System.out.println("Round 4");
        }
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] hash=md.digest(word.getBytes());
        String result="";
        for(byte b:hash){
            result+=String.format("%02x",b);
        }
        System.out.println("Hash: "+result);


    }
}
