package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SeatGradeTest {

    @Test
    @DisplayName("각 좌석 등급(S,A,B) 는 올바른 가격을 반환해야 한다.")
    void getPrice(){
        int sPrice = SeatGrade.S.getPrice();
        int aPrice = SeatGrade.A.getPrice();
        int bPrice = SeatGrade.B.getPrice();

        assertThat(sPrice).isEqualTo(18000);
        assertThat(aPrice).isEqualTo(15000);
        assertThat(bPrice).isEqualTo(12000);
    }
}