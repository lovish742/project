package it.spaceschool.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerUtil {

    private static final Logger LOG = Logger.getLogger("SpaceSchool");

    private LoggerUtil() {}

    public static void info(String msg) {
        LOG.log(Level.INFO, msg);
    }

    public static void error(String msg, Throwable t) {
        LOG.log(Level.SEVERE, msg, t);
    }
}
