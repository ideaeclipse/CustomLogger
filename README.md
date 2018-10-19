# CustomLogger
This utility is used to log data in the console with information like
* Time stamp (dd/mm/yyyy, hh:mm:ss)
* Class Name where the logger was created
* Thread Name
* Message Tag (Debug,Info,Warn,Error)

## Levels
* There are 5 levels All, debug, info, warn, error
* Each level has an integer value
    * All = 0
    * Debug = 1
    * Info = 2
    * Warn = 3
    * Error = 4
* Each logger has the ability to set whether or not each type of message is displayed
* When you set the level any new message level but be greater than or equal to the loggers level
* The loggers default level is set to info (2)
```java
logger.setLevel(Level.INFO);
logger.debug("Send Message");
```
* This message will not get printed out because the level is not low enough to allow debug messages
```java
logger.setLevel(Level.DEBUG);
logger.debug("Send Message");
```
* This message will print out because the level is adequate
## Logger Manager
* Every custom logger instance needs a manager to be passed to it in order to get the current message count
* When you want to dump the current console messages use the manager.dump() method
```java
 manager.dump();
```
* This will dump all the messages sent from all registered loggers into a log file in the specified location

## Example
* This is a demo of how the logger works
* This examples registers 3 different loggers and prints 4 messages to the console
* This example also features multiple threads to show thread identification
* See the [async utility repo for more](https://github.com/ideaeclipse/AsyncUtility)
```java
public class Main {
    public static void main(String[] args) {
        LoggerManager manager = new LoggerManager(System.getProperty("user.dir") + "/logs/");
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
```
* The console output would be 
```text
[18/10/2018 19:23:27][Main                ][main                ][ERROR] : Logger 1 Message 1
[18/10/2018 19:23:27][Main                ][async-1             ][INFO ] : Logger 2 Message 1
[18/10/2018 19:23:27][Main                ][async-2             ][INFO ] : Logger 3 Message 1
[18/10/2018 19:23:27][Main                ][main                ][INFO ] : Logger 1 Message 2
```
* This would get but into a file tilted LogDumpFile-07-23-27.log in the sub directory 18-10-2018
* The log file looks like
```text
TimeStamp Of File Dump: 18/10/2018-07:23:27
[18/10/2018 19:23:27][Main                ][main                ][ERROR] : Logger 1 Message 1
[18/10/2018 19:23:27][Main                ][async-1             ][INFO ] : Logger 2 Message 1
[18/10/2018 19:23:27][Main                ][async-2             ][INFO ] : Logger 3 Message 1
[18/10/2018 19:23:27][Main                ][main                ][INFO ] : Logger 1 Message 2
Ended Log with 4 messages
```
* This shows that even though the messages are stored on different loggers and executed on different threads they still get synced into the same order when added to the log file
