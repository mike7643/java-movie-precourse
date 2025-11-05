package domain;

import java.util.List;

public class MovieTheater {
    private final List<Place> places;

    public MovieTheater(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }
}
