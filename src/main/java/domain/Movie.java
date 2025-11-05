package domain;

public class Movie {
    private String title;
    private long runningTime;

    public Movie(String title, long runningTime) {
        this.title = title;
        this.runningTime = runningTime;
    }

    public String getTitle() {
        return title;
    }
    public long getRunningTime() {
        return runningTime;
    }
}
