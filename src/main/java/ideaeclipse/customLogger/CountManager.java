package ideaeclipse.customLogger;

/**
 * This class stores the current message count, this is important for syncing messages from multiple different loggers in {@link LoggerManager#dump()}
 *
 * @author ideaeclipse
 * @see LoggerManager
 */
class CountManager {
    private Integer currentMessageCount;

    CountManager() {
        this.currentMessageCount = 0;
    }

    void reset() {
        this.currentMessageCount = 0;
    }

    Integer getCount() {
        return currentMessageCount++;
    }
}
