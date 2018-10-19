package ideaeclipse.customLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is the logger class and is what the user interacts with most
 * This classes allows you to output information to the console
 * All messages get stored in a Map where the key is an integer obtained from {@link LoggerManager#getMessageCount()}
 *
 * @author ideaeclipse
 * @see LoggerManager
 * @see CountManager
 */
public class CustomLogger {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private final LoggerManager manager;
    private final Class<?> c;
    private Map<Integer, String> map;
    private Level loggerLevel;

    /**
     * @param c       the class in which you're instantiated the terminal in.
     * @param manager the logger manager that will control all your loggers
     */
    public CustomLogger(final Class<?> c, final LoggerManager manager) {
        this.manager = manager;
        this.manager.addLogger(this);
        this.map = new HashMap<>();
        this.c = c;
        this.loggerLevel = Level.INFO;
    }

    /**
     * If your level is debug all types to the right of debug will be printed, Including debug
     *
     * @param level the level you wish to set your logger to. (ALL, DEBUG, INFO, WARN, ERROR)
     */
    public void setLevel(final Level level) {
        this.loggerLevel = level;
    }

    /**
     * @param message prints a message with the info tag
     */
    public void info(final String message) {
        printMessage(ANSI_BLACK, Level.INFO, message);
    }

    /**
     * @param message prints a message with the debug tag
     */
    public void debug(final String message) {
        printMessage(ANSI_BLACK, Level.DEBUG, message);
    }

    /**
     * @param message prints a message with the warn tag, the text is red
     */
    public void warn(final String message) {
        printMessage(ANSI_RED, Level.WARN, message);
    }

    /**
     * @param message prints a message with the error tag, the text is red
     */
    public void error(final String message) {
        printMessage(ANSI_RED, Level.ERROR, message);
    }

    /**
     * @param message prints a message with the error tag, the text is red
     * @param e       throws an inputted exception
     */
    public void error(final String message, final Exception e) {
        printMessage(ANSI_RED, Level.ERROR, message);
        try {
            throw new Exception(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Allows you send message and change the tag dynamically
     *
     * @param level   level tag {@link Level}
     * @param message message you wish to print
     */
    public void sendMessage(final Level level, final String message) {
        if (level != Level.ALL)
            printMessage(ANSI_BLACK, level, message);
        else
            debug(message);

    }

    /**
     * @return current date and time
     */
    private String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * This method adds the inputted message into a map with the current messageCounter as the key
     *
     * @param colour  text colour
     * @param level   level tag
     * @param message message type
     */
    private void printMessage(final String colour, final Level level, final String message) {
        if (this.loggerLevel.ordinal() <= level.ordinal()) {
            String string = "[" + getCurrentDateAndTime() + "][" + format(c.getSimpleName(), 20) + "][" + format(Thread.currentThread().getName().toLowerCase(), 20) + "]" + "[" + format(level.equals(Level.ALL) ? Level.DEBUG.name() : level.name(), 5) + "]" + " : " + message;
            this.map.put(this.manager.getMessageCount(), string);
            System.out.println(colour + string);
        }
    }

    /**
     * Formats print outs so that everything is always in the same spot, its easy on the eyes
     *
     * @param string string that is being formatted
     * @param length length to be formatted to
     * @return returns a formatted string
     */
    private String format(String string, final int length) {
        if (string.length() > length) {
            string = string.substring(0, length);
        } else {
            string += new String(new char[length - string.length()]).replace("\0", " ");
        }
        return string;
    }

    /**
     * @return log of all messages in a map
     */
    Map<Integer, String> getDump() {
        Map<Integer, String> temp = this.map;
        this.map = new HashMap<>();
        return temp;
    }
}
