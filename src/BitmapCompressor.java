/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Sabrina Vohra
 */
public class BitmapCompressor {
    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        // TODO: complete compress()
        int bit = BinaryStdIn.readInt(1);
        int currentRun = 0;
        int currentVal = 2;
        if(bit == currentVal) {
            currentRun++;
            if(currentRun == 99) {
                if(currentVal == 0) {
                    BinaryStdOut.write(990, 3);
                }
                else {
                    BinaryStdOut.write(991, 3);
                }
            }
        }
        else {
            BinaryStdOut.write(currentRun + "" + currentVal);
            currentRun = 0;
            if(currentVal == 0) {
                currentVal = 1;
            }
            else {
                currentVal = 0;
            }
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        // TODO: complete expand()
        int a = BinaryStdIn.readInt(3);
        if((a % 10) == 0) {
            for(int i = 0; i < a/10; i++) {
                BinaryStdOut.write(0);
            }
        }
        else {
            for(int i = 0; i < a/10; i++) {
                BinaryStdOut.write(1);
            }
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}