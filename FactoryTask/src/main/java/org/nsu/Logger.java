package org.nsu;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static Logger logger;
    private static java.util.logging.Logger log;
    private static FileHandler fh;

    public Logger(String loggerName, String fileName) {
        if (logger != null) {
            return;
        }
        try {
            fh = new FileHandler(fileName);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }

        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
        log = java.util.logging.Logger.getLogger(loggerName);
        log.setUseParentHandlers(false);
        log.addHandler(fh);

        logger = this;
    }

    public java.util.logging.Logger getLogger() {
        return Logger.log;
    }
}