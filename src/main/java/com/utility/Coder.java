package com.utility;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
    Bad case occurs when there are many single symbols
    Hence we create StringBuilder that contains them
    Finally it adds them with negative length at the beginning
    That way we have constant 2 symbols added instead of twice size
 */

public class Coder {

    private final static int DEFINE_TYPE = 128;

    // method used to encode and write sequence of bytes according to content of "bytes" list
    public static void printByteArray(int[] bytes, int size, OutputStream os) throws IOException {
        if (size == -1) return;
        os.write(size + 1);
        for (int i = 0; i <= size; i++) {
            os.write(bytes[i]);
        }
    }

    public static void encode(String inputFile, String outputFile) throws IOException {
        try (InputStream br = new FileInputStream(inputFile);
             OutputStream bw = new FileOutputStream(outputFile)) {
            int[] bytes = new int[DEFINE_TYPE - 1];
            int size = -1;
            while (true) {
                int now = br.read();
                if (now == -1) {
                    printByteArray(bytes, size, bw);
                    break;
                }
                size++;
                bytes[size] = now;
                if (size > 1) {
                    if (bytes[0] == bytes[1] && bytes[1] != bytes[2]) {
                        printByteArray(bytes, size, bw);
                        size = -1;
                    }
                    if (bytes[0] != bytes[1] && bytes[1] == bytes[2]) {
                        printByteArray(bytes, size, bw);
                        size = -1;
                    }
                }
                if (size == DEFINE_TYPE - 2) {
                    printByteArray(bytes, size, bw);
                    size = -1;
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }

    public static void decode(String inputFile, String outputFile) {
        try (InputStream br = new FileInputStream(inputFile);
             OutputStream bw = new FileOutputStream(outputFile)) {
            while (true) {
                int now = br.read();
                if (now == -1) break;
                for (int i = 0; i < now; i++) bw.write((char)br.read());
            }
        } catch (IOException e) {
            System.out.println("Can't find file");
        }
    }
}

