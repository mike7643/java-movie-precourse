package domain;

import java.time.LocalDateTime;

public class Screening {

    private final Movie movie;
    private final Place place;
    private final LocalDateTime startDayTime;

    public Screening(Movie movie, Place place, LocalDateTime startDayTime) {
        this.movie = movie;
        this.place = place;
        this.startDayTime = startDayTime;
    }

    public Movie getMovie() {
        return movie;
    }
    public Place getPlace() {
        return place;
    }
    public LocalDateTime getStartDayTime() {
        return startDayTime;
    }

    public boolean isMovieDay(){
        int dayOfMonth = startDayTime.getDayOfMonth();
        return dayOfMonth == 10 || dayOfMonth == 20 || dayOfMonth == 30;
    }

    public boolean isDiscountTime(){
        return startDayTime.getHour() < 11 || startDayTime.getHour() >= 20;
    }
}
