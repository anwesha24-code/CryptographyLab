package MD5;
// Client.java
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 5000);

        BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        System.out.print("Enter input: ");
        String msg = user.readLine();

        out.println(msg);

        s.close();
    }
}