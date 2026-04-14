package MD;
import java.util.*;

public class Server {

    // Left rotation
    static int leftRotate(int x, int c) {
        return (x << c) | (x >>> (32 - c));
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter input: ");
        String input = sc.nextLine();

        System.out.println("\nNo of characters in the input: " + input.length());

        byte[] msg = input.getBytes();

        int originalLen = msg.length * 8;

        // Padding
        int newLen = ((msg.length + 8) / 64 + 1) * 64;
        byte[] padded = new byte[newLen];

        System.arraycopy(msg, 0, padded, 0, msg.length);
        padded[msg.length] = (byte) 0x80;

        // append length
        for (int i = 0; i < 8; i++) {
            padded[newLen - 8 + i] = (byte) (originalLen >>> (8 * i));
        }

        // Initialize MD5 buffers
        int A = 0x67452301;
        int B = 0xefcdab89;
        int C = 0x98badcfe;
        int D = 0x10325476;

        int[] s = {
                7,12,17,22, 7,12,17,22, 7,12,17,22, 7,12,17,22,
                5,9,14,20, 5,9,14,20, 5,9,14,20, 5,9,14,20,
                4,11,16,23, 4,11,16,23, 4,11,16,23, 4,11,16,23,
                6,10,15,21, 6,10,15,21, 6,10,15,21, 6,10,15,21
        };

        int[] K = new int[64];
        for (int i = 0; i < 64; i++) {
            K[i] = (int)(long)(Math.abs(Math.sin(i + 1)) * (1L << 32));
        }

        int blocks = newLen / 64;

        for (int b = 0; b < blocks; b++) {

            System.out.println("\nBlock " + (b + 1));

            int[] M = new int[16];

            for (int i = 0; i < 16; i++) {
                int index = b * 64 + i * 4;
                M[i] = ((padded[index] & 0xff)) |
                       ((padded[index+1] & 0xff) << 8) |
                       ((padded[index+2] & 0xff) << 16) |
                       ((padded[index+3] & 0xff) << 24);
            }

            int a = A, b1 = B, c = C, d = D;

            for (int i = 0; i < 64; i++) {

                int F = 0, g = 0;

                if (i < 16) {
                    F = (b1 & c) | (~b1 & d);
                    g = i;
                    if (i == 0) System.out.println("Round 1");
                } else if (i < 32) {
                    F = (d & b1) | (~d & c);
                    g = (5 * i + 1) % 16;
                    if (i == 16) System.out.println("Round 2");
                } else if (i < 48) {
                    F = b1 ^ c ^ d;
                    g = (3 * i + 5) % 16;
                    if (i == 32) System.out.println("Round 3");
                } else {
                    F = c ^ (b1 | ~d);
                    g = (7 * i) % 16;
                    if (i == 48) System.out.println("Round 4");
                }

                F = F + a + K[i] + M[g];
                a = d;
                d = c;
                c = b1;
                b1 = b1 + leftRotate(F, s[i]);
            }

            A += a;
            B += b1;
            C += c;
            D += d;
        }

        System.out.println("\nFinal Hash Value:");

        System.out.printf("%08x%08x%08x%08x\n", A, B, C, D);
    }
}