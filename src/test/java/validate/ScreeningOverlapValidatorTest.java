package validate;

import domain.Movie;
import domain.Place;
import domain.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScreeningOverlapValidatorTest {

    private ScreeningOverlapValidator validator;
    private Movie movieA;
    private Movie movieB;
    private Place place = null; // 이 테스트에서 Place 정보가 필요 없으므로 null을 사용했읍니다.

    @BeforeEach
    void setUp() {
        validator = new ScreeningOverlapValidator();
        movieA = new Movie("설쿡열차", 120);
        movieB = new Movie("쿵부팬더", 90);
    }

    @Test
    @DisplayName("두 상영 시간이 명확히 겹칠 경우 IllegalArgumentException 발생")
    void validate_OverlappingScreenings() {

        Screening screeningA = new Screening(movieA, place, LocalDateTime.of(2025, 1, 1, 10, 0));
        Screening screeningB = new Screening(movieB, place, LocalDateTime.of(2025, 1, 1, 11, 30));

        List<Screening> screenings = Arrays.asList(screeningA, screeningB);

        assertThatThrownBy(() -> validator.validate(screenings))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시간이 겹칩니다");
    }

    @Test
    @DisplayName("한 상영이 다른 상영을 완전히 포함할 경우 예외 발생")
    void validate_ContainScreening() {

        // 10:00 ~ 12:00
        Screening screeningA = new Screening(movieA, place, LocalDateTime.of(2025, 1, 1, 10, 0));
        // 10:30 ~ 11:30 이라서 완전히 포함
        Screening screeningB = new Screening(movieB, place, LocalDateTime.of(2025, 1, 1, 10, 30));

        List<Screening> screenings = Arrays.asList(screeningA, screeningB);

        assertThatThrownBy(() -> validator.validate(screenings))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상영 시간이 겹치지 않으면(간격 있음) 예외가 발생하지 않음")
    void validate_NoOverlappingScreenings() {

        Screening screeningA = new Screening(movieA, place, LocalDateTime.of(2025, 1, 1, 10, 0));
        Screening screeningB = new Screening(movieB, place, LocalDateTime.of(2025, 1, 1, 13, 0));

        List<Screening> screenings = Arrays.asList(screeningA, screeningB);

        assertThatCode(() -> validator.validate(screenings))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상영 시간이 정확히 이어붙을 때 예외가 발생하지 않음")
    void validate_ContinueScreenings() {

        // 10:00 ~ 12:00 (12:00 정각에 끝남)
        Screening screeningA = new Screening(movieA, place, LocalDateTime.of(2025, 1, 1, 10, 0));
        // 12:00 ~ 13:30 (12:00 정각에 시작)
        Screening screeningB = new Screening(movieB, place, LocalDateTime.of(2025, 1, 1, 12, 0));

        List<Screening> screenings = Arrays.asList(screeningA, screeningB);

        assertThatCode(() -> validator.validate(screenings))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상영 목록이 1개이거나 없으면 예외가 발생하지 않음")
    void validate_singleOrNull() {
        Screening screeningA = new Screening(movieA, place, LocalDateTime.of(2025, 1, 1, 10, 0));
        List<Screening> singleList = Collections.singletonList(screeningA);
        List<Screening> emptyList = Collections.emptyList();

        assertThatCode(() -> validator.validate(singleList))
                .doesNotThrowAnyException();
        assertThatCode(() -> validator.validate(emptyList))
                .doesNotThrowAnyException();
        assertThatCode(() -> validator.validate(null))
                .doesNotThrowAnyException();
    }
}