package domain;

public class User {
    private final String id;
    private long point =1000; //신규 회원 포인트 1000으로 설정함.

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public Long getPoint() {
        return point;
    }

    public void addPoint(long addPoint){
        if (addPoint < 100) { // 최소 100포인트 이상 적립하도록 설정함.
            throw new IllegalArgumentException("최소 적립 포인트 100 이상 이어야 한다");
        }
        this.point += addPoint;
    }

    public void usePoint(long usePoint) {
        if (usePoint < 0) {
            throw new IllegalArgumentException("최소 0 포인트 이상 사용하여야 한다.");
        }
        if(usePoint > this.point){
            throw new IllegalArgumentException("보유한 포인트(" + this.point + ")보다 많이 사용할 수 없다.");
        }
        this.point -= usePoint;
    }
}
