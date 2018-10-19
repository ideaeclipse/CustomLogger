package ideaeclipse.customLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoggerManager {
    private final List<CustomLogger> loggerList;
    private final String logsDirectory;
    private final CountManager countManager;

    public LoggerManager(final String directory) {
        this.loggerList = new LinkedList<>();
        this.logsDirectory = directory;
        this.countManager = new CountManager();
        File temp = new File(directory);
        if (!temp.isDirectory())
            temp.mkdir();

    }

    public void addLogger(final CustomLogger logger) {
        loggerList.add(logger);
    }

    public Integer getMessageCount() {
        return this.countManager.getCount();
    }

    public boolean dump() {
        File file = new File((logsDirectory.endsWith("/") ? logsDirectory : logsDirectory + "/") + "LogDumpFile-" + getDateTime().replace("/", "-").replace(" ", "-").replace(":", "-") + ".log");
        System.out.println(file.getName());
        try {
            if (file.createNewFile()) {
                if (file.canWrite()) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    writer.write("TimeStamp: " + getDateTime());
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
                    writer.write("End of log file");
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

    private String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
