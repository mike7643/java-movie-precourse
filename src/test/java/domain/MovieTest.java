package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class MovieTest {

    @Test
    @DisplayName("Movie 객체 생성 시 제목과 상영 길이를 올바르게 반환한다.")
    void createMovie(){
        String title = "something";
        long runningTime = 100;

        Movie movie = new Movie(title, runningTime);

        String realTitle = movie.getTitle();
        long realRunningTime = movie.getRunningTime();

        assertThat(realTitle).isEqualTo(title);
        assertThat(realRunningTime).isEqualTo(runningTime);
    }
}