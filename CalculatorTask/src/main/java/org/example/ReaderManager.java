package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ReaderManager {
    public BufferedReader createReader(String[] args) throws IOException {
        if (args.length > 0) {
            return new BufferedReader(new FileReader(args[0]));
        } else {
            return new BufferedReader(new InputStreamReader(System.in));
        }
    }

    public void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
