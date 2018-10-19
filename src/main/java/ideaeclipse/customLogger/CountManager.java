package ideaeclipse.customLogger;

class CountManager {
    private Integer currentMessageCount;

    CountManager() {
        this.currentMessageCount = 0;
    }

    Integer getCount() {
        return currentMessageCount++;
    }
}
