package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovieTheaterTest {

    private MovieTheater movieTheater;
    private Place place1;
    private Place place2;
    private Place place3;


    @BeforeEach
    void setUp(){
        place1 = new Place("송강호관", LocalTime.of(9, 0), LocalTime.of(23, 0), new ArrayList<>());
        place2= new Place("IMAX관", LocalTime.of(9, 0), LocalTime.of(23, 0), new ArrayList<>());
        place3= new Place("VIP관", LocalTime.of(9, 0), LocalTime.of(23, 0), new ArrayList<>());

        List<Place> places = List.of(place1, place2, place3);
        movieTheater = new MovieTheater(places);
    }

    @Test
    @DisplayName("영화관이 소유한 모든 스크린 목록을 반환한다")
    void getAllPlaces() {
        List<Place> allPlaces = movieTheater.getPlaces();

        assertThat(allPlaces).isNotNull();
        assertThat(allPlaces).hasSize(3);

        assertThat(allPlaces).containsExactly(place1, place2,place3);
    }

}