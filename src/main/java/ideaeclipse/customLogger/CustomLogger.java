package ideaeclipse.customLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLogger {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    private final Thread currentThread;
    private Level loggerLevel;

    public CustomLogger(Thread thread) {
        this.currentThread = thread;
        this.loggerLevel = Level.INFO;
    }

    public boolean setLevel(final Level level) {
        this.loggerLevel = level;
        return true;
    }

    public void info(final String message) {
        printMessage(ANSI_BLACK,Level.INFO, message);
    }

    public void debug(final String message) {
        printMessage(ANSI_BLACK,Level.DEBUG, message);
    }

    public void warn(final String message) {
        printMessage(ANSI_BLACK,Level.WARN, message);
    }

    public void error(final String message) {
        printMessage(ANSI_RED,Level.ERROR, message);
    }

    public void error(final String message, final Exception e) {
        printMessage(ANSI_RED,Level.ERROR, message);
        try {
            throw new Exception(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void sendMessage(final Level level, final String message) {
        if (level != Level.ALL)
            printMessage(ANSI_BLACK,level, message);
        else
            debug(message);

    }
    private String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }
    private String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private void printMessage(final String colour,final Level level, final String message) {
        if (this.loggerLevel.ordinal() <= level.ordinal())
            System.out.println(colour + "[" + getCurrentDateAndTime() + "][" + this.currentThread.getName().toLowerCase() + "]" + "[" + level.name() + "]" + " : " + message);
    }
}
