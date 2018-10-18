import ideaeclipse.AsyncUtility.AsyncList;
import ideaeclipse.AsyncUtility.ForEachList;
import ideaeclipse.customLogger.CustomLogger;
import ideaeclipse.customLogger.Level;

import java.util.Optional;

public class Main {
    public static void main(String[] args){
        new CustomLogger(Thread.currentThread()).error("Error");
        AsyncList<Integer> list = new ForEachList<>();
        list.add(o -> {
            new CustomLogger(Thread.currentThread()).info("Hey");
            return Optional.empty();
        }).add(o -> {
            new CustomLogger(Thread.currentThread()).info("Hey2");
            return Optional.empty();
        });
        list.execute();

    }
}
