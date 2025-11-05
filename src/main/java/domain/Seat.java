package domain;

import java.util.Objects;

public class Seat {

    private final SeatGrade grade;
    private final String row;
    private final int column;


    public Seat(SeatGrade grade, String row, int column) {
        this.grade = grade;
        this.row = row;
        this.column = column;
    }

    public SeatGrade getGrade() {
        return grade;
    }

    public String getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return column == seat.column && Objects.equals(row, seat.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
