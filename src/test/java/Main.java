
import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.customLogger.CustomLogger;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        new CustomLogger(Main.class).error("Error");
        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            new CustomLogger(Main.class).info("Hey");
            return Optional.empty();
        }).add(o -> {
            new CustomLogger(Main.class).info("Hey2");
            return Optional.empty();
        });
        list.execute();

    }
}
