package MD;
import java.util.Scanner;

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

        // Step 1: Create padded array
        int newLen = ((msg.length + 8) / 64 + 1) * 64;
        byte[] padded = new byte[newLen];

        // Step 2: Copy message (NO system function)
        for (int i = 0; i < msg.length; i++) {
            padded[i] = msg[i];
        }

        // Step 3: Append 1 bit (0x80)
        padded[msg.length] = (byte) 0x80;

        // Step 4: Append length in bits (little endian)
        for (int i = 0; i < 8; i++) {
            padded[newLen - 8 + i] = (byte) (originalLen >>> (8 * i));
        }

        // Step 5: MD5 initial values
        int A = 0x67452301;
        int B = 0xefcdab89;
        int C = 0x98badcfe;
        int D = 0x10325476;

        // Step 6: Constants (fixed values)
        int[] K = {
            0xd76aa478,0xe8c7b756,0x242070db,0xc1bdceee,
            0xf57c0faf,0x4787c62a,0xa8304613,0xfd469501,
            0x698098d8,0x8b44f7af,0xffff5bb1,0x895cd7be,
            0x6b901122,0xfd987193,0xa679438e,0x49b40821,
            0xf61e2562,0xc040b340,0x265e5a51,0xe9b6c7aa,
            0xd62f105d,0x02441453,0xd8a1e681,0xe7d3fbc8,
            0x21e1cde6,0xc33707d6,0xf4d50d87,0x455a14ed,
            0xa9e3e905,0xfcefa3f8,0x676f02d9,0x8d2a4c8a,
            0xfffa3942,0x8771f681,0x6d9d6122,0xfde5380c,
            0xa4beea44,0x4bdecfa9,0xf6bb4b60,0xbebfbc70,
            0x289b7ec6,0xeaa127fa,0xd4ef3085,0x04881d05,
            0xd9d4d039,0xe6db99e5,0x1fa27cf8,0xc4ac5665,
            0xf4292244,0x432aff97,0xab9423a7,0xfc93a039,
            0x655b59c3,0x8f0ccc92,0xffeff47d,0x85845dd1,
            0x6fa87e4f,0xfe2ce6e0,0xa3014314,0x4e0811a1,
            0xf7537e82,0xbd3af235,0x2ad7d2bb,0xeb86d391
        };

        int[] s = {
            7,12,17,22, 7,12,17,22, 7,12,17,22, 7,12,17,22,
            5,9,14,20, 5,9,14,20, 5,9,14,20, 5,9,14,20,
            4,11,16,23, 4,11,16,23, 4,11,16,23, 4,11,16,23,
            6,10,15,21, 6,10,15,21, 6,10,15,21, 6,10,15,21
        };

        int blocks = newLen / 64;

        // Step 7: Process each block
        for (int b = 0; b < blocks; b++) {

            System.out.println("\nBlock " + (b + 1));

            int[] M = new int[16];

            // Step 8: Create 16 words
            for (int i = 0; i < 16; i++) {
                int index = b * 64 + i * 4;

                M[i] = (padded[index] & 0xff) | ((padded[index + 1] & 0xff) << 8) | ((padded[index + 2] & 0xff) << 16)  | ((padded[index + 3] & 0xff) << 24);
            }

            int a = A, b1 = B, c = C, d = D;

            // Step 9: 64 operations (4 rounds)
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

            // Step 10: Update hash
            A += a;
            B += b1;
            C += c;
            D += d;
        }

        // Step 11: Final output
        System.out.println("\nFinal Hash Value:");
        System.out.printf("%08x%08x%08x%08x\n", A, B, C, D);
        sc.close();
    }
}