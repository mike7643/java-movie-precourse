package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceTest {

    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<Seat> seats;
    private Place place;

    @BeforeEach
    void setUp() {
        name="1";
        openTime=LocalTime.of(10,0);
        closeTime=LocalTime.of(18,0);
        seats=new ArrayList<>();

        seats.add(new Seat(SeatGrade.S, "A", 1));
        seats.add(new Seat(SeatGrade.A, "A", 2));
        seats.add(new Seat(SeatGrade.B, "A", 3));

        place=new Place(name, openTime, closeTime, seats);
    }

    @Test
    @DisplayName("Place 객체 정상 생성 및 반환 확인")
    void createPlace(){
        String realName=place.getName();
        LocalTime realOpenTime=place.getOpenTime();
        LocalTime realCloseTime=place.getCloseTime();
        List<Seat> realSeats=place.getSeats();

        assertThat(realName).isEqualTo(name);
        assertThat(realOpenTime).isEqualTo(openTime);
        assertThat(realCloseTime).isEqualTo(closeTime);
        assertThat(realSeats).isEqualTo(seats);

        assertThat(realSeats.size()).isEqualTo(3);
        assertThat(realSeats).containsExactly(new Seat(SeatGrade.S, "A", 1),
                                                new Seat(SeatGrade.A, "A", 2),
                                                    new Seat(SeatGrade.B, "A", 3));
    }
}