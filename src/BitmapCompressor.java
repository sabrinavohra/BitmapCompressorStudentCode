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
        // Initializes count and the last bit that was read in
        int count = 0;
        boolean last = false;
        while(!BinaryStdIn.isEmpty()) {
            boolean readIn = BinaryStdIn.readBoolean();
            // If the read in number doesn't match the last number, prints out number of repeats and resets count
            if(readIn != last) {
                BinaryStdOut.write(count, BYTE);
                count = 0;
            }
            // If the read in number is the same as the last and the count is greater than the 8-bit limit
            if((readIn == last) && (count == MAX)) {
                // Prints max and 0 and resets the count
                BinaryStdOut.write(MAX, BYTE);
                BinaryStdOut.write(0, BYTE);
                count = 0;
            }
            // Sets last to the current number
            last = readIn;
            count++;
        }
        // Writes out any remaining
        BinaryStdOut.write(count, BYTE);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        // Initializes boolean to keep track of current number being written
        boolean one = false;
        while(!BinaryStdIn.isEmpty()) {
            int num = BinaryStdIn.readInt(BYTE);
            // Writes out number the specified number of times
            for(int i = 0; i < num; i++) {
                if(one) {
                    // Specifies 1 bit to write out
                    BinaryStdOut.write(1, 1);
                }
                else {
                    BinaryStdOut.write(0, 1);
                }
            }
            // Sets value to the other number before repeating and printing
            one = !one;
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