
import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.customLogger.CustomLogger;
import ideaeclipse.customLogger.Level;
import ideaeclipse.customLogger.LoggerManager;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        LoggerManager manager = new LoggerManager(System.getProperty("user.dir") + "/logs/", Level.INFO);
        CustomLogger logger = new CustomLogger(Main.class, manager);
        logger.error("Logger 1 Message 1");
        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            CustomLogger logger2 = new CustomLogger(Main.class, manager);
            logger2.info("Logger 2 Message 1");
            return Optional.empty();
        }).add(o -> {
            CustomLogger logger3 = new CustomLogger(Main.class, manager);
            logger3.info("Logger 3 Message 1");
            return Optional.empty();
        });
        list.execute();
        logger.info("Logger 1 Message 2");
        logger.setLevel(Level.INFO);
        logger.debug("Send Message");
        manager.dump();
    }
}
