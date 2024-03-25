package org.nsu.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderManager implements AutoCloseable {

    private BufferedReader reader;

    private boolean isSystemInUsed = false;

    public BufferedReader createReader(String[] args) throws IOException {
        if (args.length > 0) {
            reader = new BufferedReader(new FileReader(args[0]));
        } else {
            reader = new BufferedReader(new InputStreamReader(System.in));
            isSystemInUsed = true;
        }
        return reader;
    }

    @Override
    public void close() throws Exception {
        if (reader != null && !isSystemInUsed) {
            reader.close();
        }
    }
}
