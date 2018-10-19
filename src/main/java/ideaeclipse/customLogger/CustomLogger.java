package ideaeclipse.customLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomLogger {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private final LoggerManager manager;
    private final Class<?> c;
    private Map<Integer, String> map;
    private Level loggerLevel;

    public CustomLogger(final Class<?> c, final LoggerManager manager) {
        this.manager = manager;
        this.manager.addLogger(this);
        this.map = new HashMap<>();
        this.c = c;
        this.loggerLevel = Level.INFO;
    }

    public boolean setLevel(final Level level) {
        this.loggerLevel = level;
        return true;
    }

    public void info(final String message) {
        printMessage(ANSI_BLACK, Level.INFO, message);
    }

    public void debug(final String message) {
        printMessage(ANSI_BLACK, Level.DEBUG, message);
    }

    public void warn(final String message) {
        printMessage(ANSI_BLACK, Level.WARN, message);
    }

    public void error(final String message) {
        printMessage(ANSI_RED, Level.ERROR, message);
    }

    public void error(final String message, final Exception e) {
        printMessage(ANSI_RED, Level.ERROR, message);
        try {
            throw new Exception(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void sendMessage(final Level level, final String message) {
        if (level != Level.ALL)
            printMessage(ANSI_BLACK, level, message);
        else
            debug(message);

    }

    private String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private void printMessage(final String colour, final Level level, final String message) {
        if (this.loggerLevel.ordinal() <= level.ordinal()) {
            String string = "[" + getCurrentDateAndTime() + "][" + format(c.getSimpleName(), 20) + "][" + format(Thread.currentThread().getName().toLowerCase(), 20) + "]" + "[" + format(level.name(), 5) + "]" + " : " + message;
            this.map.put(this.manager.getMessageCount(), string);
            System.out.println(colour + string);
        }
    }

    private String format(String string, int length) {
        if (string.length() > length) {
            string = string.substring(0, length);
        } else {
            string += new String(new char[length - string.length()]).replace("\0", " ");
        }
        return string;
    }

    Map<Integer, String> getDump() {
        Map<Integer, String> temp = this.map;
        this.map = new HashMap<>();
        return temp;
    }
}
