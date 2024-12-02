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
    private static final int MAX = 255;
    private static final int BYTE = 8;
    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        // TODO: complete compress()
        int toPrint = 0;
        boolean one = false; // Starts as 0
        while(!BinaryStdIn.isEmpty()) {
            boolean readIn = BinaryStdIn.readBoolean();
            if (readIn != one || toPrint == MAX) {
                if (toPrint == MAX) {
                    BinaryStdOut.write(MAX, BYTE);
                    BinaryStdOut.write(0, BYTE);
                    toPrint -= 255;
                }
                BinaryStdOut.write(toPrint, BYTE);
                toPrint = 0;
                one = readIn;
            }
            toPrint++;
        }
        if (toPrint > 0) {
            while (toPrint > MAX) {
                BinaryStdOut.write(MAX, BYTE);
                BinaryStdOut.write(0, BYTE);
                toPrint -= 255;
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
        boolean zeroOrOne = true;
        while(!BinaryStdIn.isEmpty()) {
            int num = BinaryStdIn.readInt(8);
            for(int i = 0; i < num; i++) {
                if(zeroOrOne) {
                    BinaryStdOut.write(0);
                }
                else {
                    BinaryStdOut.write(1);
                }
            }
            zeroOrOne = !zeroOrOne;
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