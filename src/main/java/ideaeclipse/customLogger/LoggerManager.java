package ideaeclipse.customLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class manages all loggers and allows you to dump your console log into a .log file in the directory of your choice
 * When dumped there will be a subfolder created for each date you have log files
 * Log files are names using the follow convention LogDumpFile-HH-MM-SS.log
 *
 * @author ideaeclipse
 * @see CustomLogger
 * @see CountManager
 */
public class LoggerManager {
    private final List<CustomLogger> loggerList;
    private final String logsDirectory;
    private final CountManager countManager;
    private final Level globalLevel;

    /**
     * @param directory directory you wish to store your log files
     */
    public LoggerManager(final String directory, final Level globalLevel) {
        this.loggerList = new LinkedList<>();
        this.logsDirectory = directory;
        this.globalLevel = globalLevel;
        this.countManager = new CountManager();
        File temp = new File(directory);
        if (!temp.isDirectory())
            temp.mkdir();

    }

    /**
     * @param logger adds a logger to the list of loggers
     */
    public void addLogger(final CustomLogger logger) {
        loggerList.add(logger);
    }

    /**
     * @return current messageCount, allows for syncing of messages from multiple loggers
     * @see #dump()
     */
    public Integer getMessageCount() {
        return this.countManager.getCount();
    }

    /**
     * Allows all loggers attached to this manager have a shared level state. Good for debug on/off
     *
     * @return global level
     */
    public Level getGlobalLevel() {
        return globalLevel;
    }

    /**
     * This method takes all {@link CustomLogger#getDump()} which is a map of integer,string tuples and merges all of them into a single map
     * By merging all them they get ordered based on their key which allows for a conversion to a list of values
     * After converting to a list of values all lines are dumped into the designated log file and has a message with how many lines were printed
     *
     * @return status of whether logs where able to be dumped or not
     */
    public boolean dump() {
        File file = new File((logsDirectory.endsWith("/") ? logsDirectory : logsDirectory + "/") + getDate().replace("/", "-"));
        if (!file.exists())
            file.mkdir();
        file = new File(file.getAbsolutePath() + "/LogDumpFile-" + getTime().replace(" ", "-").replace(":", "-") + ".log");
        try {
            if (file.createNewFile()) {
                if (file.canWrite()) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    writer.write("TimeStamp Of File Dump: " + getDate() + "-" + getTime());
                    writer.newLine();
                    Map<Integer, String> map = new HashMap<>();
                    for (CustomLogger c : this.loggerList) {
                        map.putAll(c.getDump());
                    }
                    List<String> list = new LinkedList<>(map.values());
                    for (String s : list) {
                        writer.write(s);
                        writer.newLine();
                    }
                    writer.write("Ended Log with " + getMessageCount() + " messages");
                    this.countManager.reset();
                    writer.newLine();
                    writer.close();
                } else
                    System.out.println("cannot write");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @return current date
     */
    private String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * @return current Time
     */
    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
