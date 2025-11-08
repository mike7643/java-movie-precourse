package validate;

import domain.Screening;

import java.time.LocalDateTime;
import java.util.List;

public class ScreeningOverlapValidator {

    public void validate(List<Screening> screenings) {
        if (screenings == null || screenings.size() < 2) {
            return;
        }

        for (int i = 0; i < screenings.size(); i++) {
            Screening screeningA = screenings.get(i);

            for (int j = i + 1; j < screenings.size(); j++) {
                Screening screeningB = screenings.get(j);

                if (isOverlapping(screeningA, screeningB)) {
                    throw new IllegalArgumentException(
                            "예매하려는 상영 목록 간에 시간이 겹칩니다. ("
                                    + screeningA.getMovie().getTitle() + " / "
                                    + screeningB.getMovie().getTitle() + ")"
                    );
                }
            }
        }
    }

    private boolean isOverlapping(Screening screeningA, Screening screeningB) {
        long runningTimeA = screeningA.getMovie().getRunningTime();
        long runningTimeB = screeningB.getMovie().getRunningTime();

        LocalDateTime startA = screeningA.getStartDayTime();
        LocalDateTime endA = startA.plusMinutes(runningTimeA);

        LocalDateTime startB = screeningB.getStartDayTime();
        LocalDateTime endB = startB.plusMinutes(runningTimeB);

        return startA.isBefore(endB) && startB.isBefore(endA);
    }
}