package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SeatTest {

    @Test
    @DisplayName("Seat 생성 시 좌석 위치와 등급에 맞는 가격을 반환한다")
    void createSeat(){
        Seat seat = new Seat(SeatGrade.S, "A", 1);

        int price = seat.getGrade().getPrice();
        String row = seat.getRow();
        int column = seat.getColumn();

        assertThat(row).isEqualTo("A");
        assertThat(column).isEqualTo(1);
        assertThat(price).isEqualTo(18000);
    }

    @Test
    @DisplayName("set 의 중복 방지를 통해 같은 위치의 좌석에 다른 등급 방지")
    void equalsHashCode(){
        Seat seatA1S = new Seat(SeatGrade.S, "A", 1);
        Seat seatA1A = new Seat(SeatGrade.A, "A", 1);
        Seat seatA2B = new Seat(SeatGrade.B, "A", 2);

        assertThat(seatA1S.equals(seatA1A)).isTrue();
        assertThat(seatA1S.equals(seatA2B)).isFalse();
    }

    @Test
    @DisplayName("Set에서 중복을 제거한다")
    void removeDuplicate(){
        Set<Seat> seats = new HashSet<>();

        Seat seatA1S = new Seat(SeatGrade.S, "A", 1);
        Seat seatA1A = new Seat(SeatGrade.A, "A", 1);
        Seat seatA2B = new Seat(SeatGrade.B, "A", 2);

        seats.add(seatA1S);
        seats.add(seatA1A);
        seats.add(seatA2B);

        assertThat(seats.size()).isEqualTo(2);
    }
}