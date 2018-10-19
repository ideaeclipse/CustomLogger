
import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.customLogger.CustomLogger;
import ideaeclipse.customLogger.LoggerManager;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        LoggerManager manager = new LoggerManager(System.getProperty("user.dir") + "/logs/");
        CustomLogger logger = new CustomLogger(Main.class,manager);
        logger.error("Test error");
        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            CustomLogger logger2 = new CustomLogger(Main.class,manager);
            logger2.info("TEST");
            return Optional.empty();
        }).add(o -> {
            CustomLogger logger3 = new CustomLogger(Main.class,manager);
            logger3.info("MEMES");
            return Optional.empty();
        });
        list.execute();
        logger.info("End");
        manager.dump();
    }
}
