package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testUser");
    }

    @Test
    @DisplayName("ID와 Nickname이 올바르게 설정되고, 기본 포인트 1000을 가져야 한다")
    void createUser() {
        long id = user.getId();
        String nickname = user.getNickname();
        long points = user.getPoint();

        assertThat(id).isEqualTo(1L);
        assertThat(nickname).isEqualTo("testUser");
        assertThat(points).isEqualTo(1000L);
    }

    @Test
    @DisplayName("포인트를 성공적으로 사용하면 보유 포인트가 차감된다")
    void usePoint_Success() {
        user.usePoint(300L);

        assertThat(user.getPoint()).isEqualTo(700L);
    }

    @Test
    @DisplayName("usePoint (예외): 보유 포인트보다 많이 사용하려 하면 예외가 발생한다")
    void usePoint_Fail() {
        assertThatThrownBy(() -> user.usePoint(1001L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("보유한 포인트(1000)보다 많이 사용할 수 없다.");

        // 예외가 발생했으므로 포인트는 차감되지 않아야 함
        assertThat(user.getPoint()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("포인트를 성공적으로 적립(100 이상)하면 보유 포인트가 증가한다")
    void addPoints_Success() {
        user.addPoint(500L);

        assertThat(user.getPoint()).isEqualTo(1500L);
    }

    @Test
    @DisplayName("100 포인트보다 적게 적립하려 하면 예외가 발생한다")
    void addPoints_Fail_BelowMinimum() {
        assertThatThrownBy(() -> user.addPoint(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 적립 포인트 100 이상 이어야 한다.");

        // 예외가 발생했으므로 포인트는 차감되지 않아야 함
        assertThat(user.getPoint()).isEqualTo(1000L);
    }
}